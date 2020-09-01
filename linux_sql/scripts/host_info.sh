#!/bin/bash

# Notes
# Script usage: ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password

# Setup arguments from arguments passed in
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# Set up Crontab environmental password
export PGPASSWORD=$psql_password

# Check to see if user entered the correct amount of arguments
if [ $# -ne 5 ];
then
  echo "There was an invalid number of arguments.
  Refer to script usage: ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password"
  exit 1
fi

lscpu_out=$("lscpu")

# Get the hostname
get_hostname() {
  hostname=$(hostname -f)
}

# Function to call when searching for specific value
get_lscpu_value() {
  pattern=$1
  value=$(echo "$lscpu_out" | egrep "$pattern" | awk -F':' '{print $2}' | xargs)
  echo "value=$value"
}

# Get function for getting number of CPUs
get_cpu_number() {
  get_lscpu_value "^CPU\(s\)"
  cpu_number=$value
}

# Get function for getting CPU architecture
get_cpu_architecture() {
  get_lscpu_value "Architecture"
  cpu_architecture=$value
}

# Get function for getting CPU model
get_cpu_model() {
  get_lscpu_value "Model name"
  cpu_model=$value
}

# Get function for getting CPU MHz
get_cpu_mhz() {
  get_lscpu_value "CPU MHz"
  cpu_mhz=$value
}

# Get function for getting L2 Cache
get_L2_cache() {
  value=$(echo "$lscpu_out" | egrep "L2 cache" | awk -F':' '{print $2}' | tr -d 'K' | xargs)
  l2_cache=$value
}

# Get function for getting Total memory
get_total_mem() {
  total_mem=$(echo $(grep MemTotal /proc/meminfo) | awk '{print $2}' | xargs)
}


# Get function for getting timestamp
get_timestamp() {
  get_lscpu_value ""
  timestamp=$(date)
}

# Create an insert statement, then insert into the database
insert_info() {
  insert_query="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, timestamp)
  VALUES ('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', '$cpu_mhz', '$l2_cache', '$total_mem', '$timestamp');"

  psql -h "$psql_host" -p "$psql_port" -d "$db_name" -U "$psql_user" -c "$insert_query"
}

# Execute the functions
get_hostname
get_cpu_number
get_cpu_architecture
get_cpu_model
get_cpu_mhz
get_L2_cache
get_total_mem
get_timestamp
insert_info
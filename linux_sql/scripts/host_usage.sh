#!/bin/bash


# Notes
# bash scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password

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
  Refer to script usage: .scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password"
  exit 1
fi

# Get the hostname
hostname=$(hostname -f)
timestamp=$(date)

vmstat=`vmstat`
vmstatd=`vmstat -d`   # Disk mode description

# Search for values and save in variables
memory_free=$(echo "$vmstat" | awk 'FNR == 3 {print $4}' | xargs )
cpu_idle=$(echo "$vmstat" | awk 'FNR == 3 {print $15}' | xargs)
cpu_kernel=$(echo "$vmstat" | awk 'FNR == 3 {print $14}' | xargs)
disk_io=$(echo "$vmstatd" | awk 'FNR == 3 {print $10}' | xargs)
disk_available=$(df -BM / | awk 'FNR == 2 {print $4}' | tr -d "M" | xargs)

# Create an insert statement, then insert into the database
insert_info() {
  insert_query="INSERT INTO host_usage (timestamp, host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
  VALUES ('$timestamp', (SELECT id FROM host_info WHERE host_info.hostname = '${hostname}'), '$memory_free', '$cpu_idle', '$cpu_kernel', '$disk_io', '$disk_available');"

  psql -h "$psql_host" -p "$psql_port" -d "$db_name" -U "$psql_user" -c "$insert_query"
}

insert_info


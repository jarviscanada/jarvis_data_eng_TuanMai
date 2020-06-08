#!/bin/bash

# Setup Arguments
CONTAINER_NAME="jrvs-psql"
command=$1
db_username=$2
db_password=$3

# Check to make sure user enters at least one command: create, start, stop
if [ "$#" -lt 1 ]
then
  echo "This script requires a command to execute. Script usage: ./psql_docker.sh start|stop|create [db_username][db_password]"
  exit 1
fi

# Check if docker container is running; if not, start docker container
sudo systemctl status docker || sudo systemctl start docker

# If user wants to create, create a docker container with the username and password passed in
# Check if user used create command and has the correct number of arguments
if [ "$command" == "create" ] && [ "$#" -eq 3 ];
then
  # Check to see if there is already a container named jrvs-psql
  if [ $(docker container ls -a -f name=$CONTAINER_NAME | wc -l) -eq 2 ];
  then
    echo "The container name jrvs-psql already exists. Please use the command start to begin, or stop to end the container."
    exit 1
  fi

  # Pull for the latest postgres image to ensure compatibility across platforms
  docker pull postgres

  # Create the docker volume pgdata
  docker volume create pgdata
  docker run --name jrvs-psql -e POSTGRES_PASSWORD="${db_password}" -e POSTGRES_USER="${db_username}" -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
  exit $?

elif [ "$command" == "create" ] && [ "$#" -ne 3 ]
then
  # If the user used the command create but input an invalid number of arguments
    echo "Container not created. There is an invalid number of parameters.
    If using create command, please use: create [db_username] [db_password]"
    exit 1
fi

# Check to see if there is already a container named jrvs-psql
if [ $(docker container ls -a -f name=$CONTAINER_NAME | wc -l) -ne 2 ];
then
  echo "The container does not exists. Please use the command create to create a container."
  exit 1
fi

# If the user decides to start the container
# Check that the command is start and without other arguments
if [ "$command" == "start" ] && [ "$#" -eq 1 ];
then
  docker container start $CONTAINER_NAME
  exit $?
elif [ "$command" == "start" ] && [ "$#" -ne 1 ]
then
  echo "Invalid number of arguments with start. Please only run start without arguments."
  exit 1
fi

# If the user decides to stop the container
# Check that the command is stop and without other arguments
if [ "$command" == "stop" ] && [ "$#" -eq 1 ];
then
  docker container stop $CONTAINER_NAME
  exit $?

elif [ "$command" == "stop" ] && [ "$#" -ne 1 ]
then
  echo "Invalid number of arguments with stop. Please only run stop without arguments."
  exit 1
fi

#If an invalid command was entered, explain the usage.
echo "No valid command was used. Script usage: ./psql_docker.sh start|stop|create [db_username][db_password]"
exit 1




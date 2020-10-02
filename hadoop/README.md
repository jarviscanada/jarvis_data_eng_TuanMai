# Hadoop Project

Table of contents
* [Introduction](#Introduction)
* [Hadoop Cluster](#Hadoop-Cluster)
* [Hive Project](#Hive-Porject)
* [Improvements](#Improvements)

# Introduction
The Hadoop project works with the data analysis team to process big data. The data analytics team uses SAP and R to 
process data, but want to switch to big data platforms. The project will help the data analytics team in processing 
data by using Apache Hadoop. In this project, a Hadoop Cluster provisioned with GCP and Core Hadoop components were 
evaluated including MapReduce, HDFS, and YARN. All business problems were solved and written in a Zeppelin Notebook 
to display the solution and results. 


# Hadoop Cluster
![Hadoop Cluster Architecture](./assets/Hadoop_Cluster_Architecture.png)

The Hadoop Cluster works based of a single `Master Node` along with two `worker nodes`. All nodes run off a 2 CPU with 
13 GB Memory and a disk space of 100 GB. Queries are written in Zeppelin which connects to the Hive Server to establish 
a connection through JDBC. 

These queries are submitted to YARN managers as it deals with the distributing of jobs and allocation. 
The cluster uses HDFS (Hadoop Distributed FileSystem) to manage storage and data within the cluster, while storing to 
a Metastore using an RDBMS. 

# Hive Project
- discuss the purposes of the project and what you have done.
The purpose of this project was to evaluate and solve business problems with Apache Hive along with other big data tools 
and components. In this project, I was able to test the efficiency and limitations of Hive, Bash, as well as Spark. 
The results and data have been written in a Zeppelin Notebook.

![Zeppelin Notebook](./assets/zeppelin_notebook.png)


# Improvements
Some improvements that could have been done to the project are: 
1. Trying out a cluster with more worker nodes and testing to see where the tangent between cost and efficiency meet. 
2. Create more partitions with additional specifics that narrow the search down to increase runtime. 
3. Implement more Spark integration such as Spark SQL
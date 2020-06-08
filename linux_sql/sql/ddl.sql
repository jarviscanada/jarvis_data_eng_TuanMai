-- Automation script to create two tables automatically if they do not exist: "host_info" and "host_usage"

-- Connect to the database
\c host_agent;

-- Create table "host_info" if it does not exist
CREATE TABLE IF NOT EXISTS PUBLIC.host_info
(
    id                  SERIAL PRIMARY KEY,
    hostname            VARCHAR NOT NULL UNIQUE,
    cpu_number          INTEGER NOT NULL,
    cpu_architecture    VARCHAR NOT NULL,
    cpu_model           VARCHAR NOT NULL,
    cpu_mhz             FLOAT NOT NULL,
    L2_cache            INTEGER NOT NULL,
    total_mem           INTEGER NOT NULL,
    timestamp           TIMESTAMP NOT NULL
);

-- Create table "host_usage" if it does not exist
CREATE TABLE IF NOT EXISTS PUBLIC.host_usage
(
    timestamp           TIMESTAMP NOT NULL,
    host_id             SERIAL REFERENCES host_info(id),
    memory_free         INTEGER NOT NULL,
    cpu_idle            INTEGER NOT NULL,
    cpu_kernel          INTEGER NOT NULL,
    disk_io             INTEGER NOT NULL,
    disk_available      INTEGER NOT NULL
);
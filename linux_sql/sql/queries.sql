-- Group hosts by hardware info. Group hosts by CPU number and sort by their memory size in descending order within each cpu_number group

SELECT
    cpu_number,
    id,
    total_mem
FROM
    host_info
GROUP BY
    id
ORDER BY
    cpu_number, total_mem DESC;

-- Average used memory in percentage over 5 mins interval for each host. used memory = total memory - free memory.
SELECT
    host_id,
    hostname,
    date_trunc('hour', host_usage.timestamp) + INTERVAL '5min' * ROUND((date_part('minute', host_usage.timestamp) / 5)::int) AS time_stamp,
    (AVG(host_info.total_mem - host_usage.memory_free) / host_info.total_mem * 100)::int AS avg_used_mem_percentage
FROM
    host_info
INNER JOIN
    host_usage
ON
    id = host_id
GROUP BY
    id, host_id, host_info.hostname, time_stamp
ORDER BY
    id, time_stamp ASC;



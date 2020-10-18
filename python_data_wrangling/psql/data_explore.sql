-- Show table schema 
\d+ retail;

-- Show first 10 rows
SELECT * FROM retail limit 10;

-- Check # of records
SELECT COUNT(invoice_no) FROM retail;

-- number of clients (e.g. unique client ID)
SELECT COUNT( DISTINCT customer_id ) FROM retail;

-- Invoice date range (e.g. min/max dates)
SELECT
    MIN (invoice_date), MAX (invoice_date)
FROM
    retail;

-- Number of SKU/merchants (e.g. unique stock code)
SELECT
    COUNT( DISTINCT stock_code )
FROM
    retail;

-- Calculate avg invoice amount excluding invoices with a negative amount (e.g. cancelled orders have negative amount)
--

-- Calculate total revenue (e.g. sum of unit_price * quantity)
SELECT
    SUM( unit_price * quantity)
FROM
    retail;

-- Calculate total revenue by YYYYMM
SELECT
    CAST (EXTRACT (YEAR FROM invoice_date) * 100 + EXTRACT (MONTH FROM invoice_date) AS INTEGER) AS yyyymm ,
    SUM ( unit_price * quantity) AS "sum"
FROM
    retail
GROUP BY
    yyyymm
ORDER BY
    yyyymm ASC;
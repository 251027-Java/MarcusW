-- Parking Lot*******
-- *                *
-- *                *
--- *****************

-- SETUP:
-- Create a database server (docker)
-- $ docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432-d postgres
-- Connect to the server (Azure Data Studio / Database extension)
-- Test your connection with a simple query (like a select)
-- Execute the Chinook database (from the Chinook_pg.sql file to create Chinook resources in your server)

USE [Chinook];
Go

/*
DQL - Data Query Language
Keywords:

SELECT - retrieve data, select the columns from the resulting set
FROM - the table(s) to retrieve data from
WHERE - a conditional filter of the data
GROUP BY - group the data based on one or more columns
HAVING - a conditional filter of the grouped data
ORDER BY - sort the data
*/

SELECT last_name FROM actor;

-- BASIC CHALLENGES
-- List all customers (full name, customer id, and country) who are not in the USA
SELECT first_name, last_name, customer_id, country
FROM customer
WHERE (country != 'USA');

-- List all customers from Brazil
SELECT * FROM customer WHERE (country = 'Brazil');

-- List all sales agents
SELECT * FROM employee WHERE (title = 'Sales Support Agent');

-- Retrieve a list of all countries in billing addresses on invoices
SELECT DISTINCT billing_country FROM invoice;

-- Retrieve how many invoices there were in 2009, and what was the sales total for that year?
SELECT COUNT(*) as num_invoices, SUM(total) as sales_total FROM invoice WHERE EXTRACT(YEAR FROM invoice_date) = 2009;

-- (challenge: find the invoice count sales total for every year using one query)
SELECT EXTRACT(YEAR FROM invoice_date), SUM(total) as sales_total FROM invoice GROUP BY EXTRACT(YEAR FROM invoice_date);

-- how many line items were there for invoice #37
SELECT COUNT(*) FROM invoice_line WHERE invoice_id = 37;

-- SELECT COUNT(*) AS InvoiceCount, SUM(Total) AS SalesTotal FROM Invoice WHERE YEAR(Invo)i

-- how many invoices per country? BillingCountry  # of invoices -
SELECT billing_country, COUNT(*) FROM invoice GROUP BY billing_country;

-- Retrieve the total sales per country, ordered by the highest total sales first.
SELECT billing_country, SUM(total) as total_sales FROM invoice GROUP BY billing_country ORDER BY total_sales DESC;

-- JOINS CHALLENGES
-- Every Album by Artist
SELECT album.title, artist.name
FROM album
INNER JOIN artist on album.artist_id = artist.artist_id

-- All songs of the rock genre
SELECT track.name as song FROM track
INNER JOIN genre ON track.genre_id = genre.genre_id
WHERE (genre.name = 'Rock');

-- Show all invoices of customers from brazil (mailing address not billing)
SELECT invoice.* FROM invoice INNER JOIN customer ON customer.customer_id = invoice.customer_id
WHERE (customer.country = 'Brazil');

-- Show all invoices together with the name of the sales agent for each one
SELECT CONCAT(employee.first_name, ' ', employee.last_name) as Sales_Agent, invoice.* FROM invoice 
INNER JOIN customer ON customer.customer_id = invoice.customer_id
INNER JOIN employee ON employee.employee_id = customer.support_rep_id;

-- Which sales agent made the most sales in 2009?
SELECT CONCAT(employee.first_name, ' ', employee.last_name) as Sales_Agent, SUM(invoice.total) as total FROM invoice 
INNER JOIN customer ON customer.customer_id = invoice.customer_id
INNER JOIN employee ON employee.employee_id = customer.support_rep_id
WHERE EXTRACT(YEAR FROM invoice.invoice_date) = 2009
GROUP BY Sales_Agent ORDER BY total DESC LIMIT 1;

-- How many customers are assigned to each sales agent?
SELECT CONCAT(employee.first_name, ' ', employee.last_name) as Sales_Agent, COUNT(customer.*) as Num_Customers FROM customer 
INNER JOIN employee ON employee.employee_id = customer.support_rep_id
GROUP BY Sales_Agent;

-- Which track was purchased the most in 2010?
SELECT track.name, COUNT(invoice_line.quantity) as purchases FROM invoice_line 
INNER JOIN track ON invoice_line.track_id = track.track_id
INNER JOIN invoice ON invoice_line.invoice_id = invoice.invoice_id
WHERE EXTRACT(YEAR FROM invoice.invoice_date) = 2010
GROUP BY track.name ORDER BY purchases DESC LIMIT 1;

-- Show the top three best selling artists.
SELECT artist.name, COUNT(quantity) as sales FROM artist
INNER JOIN album ON album.artist_id = artist.artist_id
INNER JOIN track ON track.album_id = album.album_id
INNER JOIN invoice_line ON invoice_line.track_id = track.track_id
GROUP BY artist.name ORDER BY sales DESC LIMIT 3;

-- Which customers have the same initials as at least one other customer?
SELECT CONCAT(c.first_name, ' ', c.last_name) as customer FROM customer as c
JOIN customer as s ON c.customer_id != s.customer_id
WHERE CONCAT(SUBSTRING(c.first_name, 0, 2), SUBSTRING(c.last_name, 0, 2))
    = CONCAT(SUBSTRING(s.first_name, 0, 2), SUBSTRING(s.last_name, 0, 2));

-- ADVANCED CHALLENGES
-- solve these with a mixture of joins, subqueries, CTE, and set operators.
-- solve at least one of them in two different ways, and see if the execution
-- plan for them is the same, or different.

-- 1. which artists did not make any albums at all?
SELECT artist.name FROM artist
LEFT JOIN album ON album.artist_id = artist.artist_id
WHERE album.album_id IS NULL;

-- 2. which artists did not record any tracks of the Latin genre?

SELECT DISTINCT artist.name FROM artist
LEFT JOIN album ON album.artist_id = artist.artist_id
INNER JOIN track ON track.album_id = album.album_id
INNER JOIN genre on genre.genre_id = track.genre_id
WHERE genre.name IS NULL or genre.name != 'Latin'
ORDER by artist.name ASC;

-- video is media_type_id = 3
-- 3. which video track has the longest length? (use media type table)
SELECT track.name, track.milliseconds from track
INNER JOIN media_type on media_type.media_type_id = track.media_type_id
WHERE media_type.media_type_id = 3
ORDER BY track.milliseconds DESC LIMIT 1;

-- 4.  boss employee (the one who reports to nobody)
SELECT CONCAT(e.first_name, ' ', e.last_name) FROM employee as e
WHERE e.reports_to IS NULL;

-- 5. how many audio tracks were bought by German customers, and what was
--    the total price paid for them?

SELECT COUNT(*) as count, SUM(invoice_line.unit_price) FROM invoice
INNER JOIN invoice_line on invoice_line.invoice_id = invoice.invoice_id
INNER JOIN track on track.track_id = invoice_line.track_id
INNER JOIN media_type on media_type.media_type_id = track.media_type_id
WHERE media_type.media_type_id != 3
AND invoice.billing_country = 'Germany';

-- 6. list the names and countries of the customers supported by an employee
--    who was hired younger than 35.
SELECT CONCAT(customer.first_name, ' ', customer.last_name) as name, customer.country FROM customer
INNER JOIN employee ON employee.employee_id = customer.support_rep_id
WHERE (EXTRACT(YEAR FROM employee.hire_date) - EXTRACT(YEAR FROM employee.birth_date)) < 35;

-- DML exercises

-- 1. insert two new records into the employee table.

-- 2. insert two new records into the tracks table.

-- 3. update customer Aaron Mitchell's name to Robert Walter

-- 4. delete one of the employees you inserted.

-- 5. delete customer Robert Walter.

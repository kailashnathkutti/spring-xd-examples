--!/usr/bin/pig-0.13.0/bin/pig -x mapred historicalOrderProcessor_V1.pig 
-- Generate aggregates from historical data
--kkutti@gopivotal.com


raw_orders = LOAD '/historicalOrders.csv' USING PigStorage('\n') AS (line:chararray);

flatten_raw_orders = FOREACH raw_orders GENERATE FLATTEN (REGEX_EXTRACT_ALL( line,'^([\\d.]+),([\\d.]+),([\\d.]+),([\\d.]+),([\\d.]+),([0-9]{2})/([0-9]{2})/([0-9]{4}) ([0-9]{2}):?([0-9]{2})')) AS (customer_id:chararray,order_id:long, order_amount:float,store_id:int,num_items:int,order_day:int,order_month:int,order_year:int,order_hour:int,order_minute:int);


not_null_flatten_raw_orders = filter flatten_raw_orders BY NOT $0 IS NULL;

orders_by_store_id = GROUP not_null_flatten_raw_orders by (store_id,order_day,order_month,order_year) ;

order_count = FOREACH orders_by_store_id GENERATE FLATTEN (group),FLATTEN(COUNT(not_null_flatten_raw_orders.store_id));

unique_store_id = DISTINCT (FOREACH orders_by_store_id GENERATE not_null_flatten_raw_orders.store_id);
unique_customer_id = DISTINCT (FOREACH orders_by_store_id GENERATE not_null_flatten_raw_orders.customer_id);

STORE order_count INTO '/data/historical' USING PigStorage(',');
STORE unique_store_id INTO '/data/historical/unique_store_ids' USING PigStorage(',');
STORE unique_customer_id INTO '/data/historical/unique_customer_ids' USING PigStorage(',');



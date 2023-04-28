EXPLAIN
SELECT t1.photos,t1.clothes_status,t1.out_clothes_num,t1.clothes_num,t6.tenant_id AS storeId,t1.clothes_name,t1.service_name,
t2.rewash_type,t4.after_sale_status
FROM xyt_order AS t6 
LEFT JOIN xyt_order_clothes t1 ON t6.order_number = t1.order_number
LEFT JOIN xyt_order_clothes_rewash t2
ON t2.id = (
SELECT t3.id
FROM xyt_order_clothes_rewash t3
WHERE t3.clothes_num = t1.clothes_num
ORDER BY t3.create_time DESC
LIMIT 1
)
LEFT JOIN xyt_after_sale AS t4 
ON t4.after_sale_id = (
SELECT t5.after_sale_id FROM xyt_after_sale AS t5
WHERE t5.clothes_num = t1.clothes_num
ORDER BY t5.created_time DESC LIMIT 1
)
LEFT JOIN xiyitong_bank_factory_dev.xyt_factory_timeout_order AS t7 
ON t7.time_out_id = (
SELECT t8.time_out_id FROM xiyitong_bank_factory_dev.xyt_factory_timeout_order AS t8
WHERE t8.clothes_num = t1.clothes_num
ORDER BY t8.created_time DESC LIMIT 1
)
LIMIT 0,10



 WHERE t1.clothes_num = '10142000000104'






SELECT clothes_num,count(clothes_num) FROM xyt_order_clothes_rewash  GROUP BY clothes_num HAVING count(clothes_num) > 1



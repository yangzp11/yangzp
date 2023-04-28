SELECT day_time,IFNULL(count_num,0) FROM (

SELECT DATE_FORMAT(order_time,'%Y-%m-%d') AS order_time,COUNT(1) as count_num
FROM xyt_order 

WHERE  order_time > '2023-02-01'

GROUP BY DATE_FORMAT(order_time,'%Y-%m-%d')
) AS t1 RIGHT JOIN (SELECT
	date_format(
		date_sub(
			curdate(),
			INTERVAL (
				cast(help_topic_id AS signed) - 0
			) DAY
		),
		'%Y-%m-%d'
	) day_time
FROM
	mysql.help_topic
WHERE
	-- 符号转移需替换 <![CDATA[<]]>; 此处n数字替换成需要近n天距离当前的日期，如：30
	help_topic_id < 21
) AS t2 ON t1.order_time = t2.day_time
ORDER BY day_time
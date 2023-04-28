SELECT * FROM (
                  SELECT clothes_num, after_sale_status,after_sale_id
                  FROM xyt_after_sale
                  WHERE
                      clothes_num IN ('123','223')
                  ORDER BY created_time DESC LIMIT 100000 -- 确保查询的数据不大于这个值
              ) AS t1 GROUP BY clothes_num
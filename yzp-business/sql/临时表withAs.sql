WITH my AS (select * from xyt_user WHERE phone = '15873067229'),
     he AS (select * from xyt_user WHERE phone = '13800000001')
SELECT * FROM my LEFT JOIN he ON my.user_type = he.user_type
UNION ALL
SELECT * FROM he;

select
    GROUP_CONCAT('t1.',column_name,'')
   
from information_schema.columns
where table_schema = 'xiyitong_bank_dev'
and table_name = 'xyt_after_sale';

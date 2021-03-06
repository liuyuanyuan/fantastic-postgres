==null值的运算==
       Oracle        PostgreSQL
sum    当作0          当作0
+      遇null得null   遇null得null
||     当作‘’         遇null得null（用coalesce(col,'')）

==浮点类型(不精确的类型)==
Oracle浮点类型：float，binary_float，binary_double
PG浮点类型： double precision (float, real) 

浮点类型的特点是不精确的，存储的数据不精确，并且存的数据与存入时不一定相同，在oracle和pg中均是如此。
因此不适合用作主键或者where条件。也不能直接用来比对。

--PG example--
create table float2(id real primary key, c1 int);
insert into float2 values(1.11111111, 1);
insert into float2 values(1.22222222, 2);

select * from float2;
   id    | c1
---------+----
1.11111116 |  1
1.22222221 |  2
(2 行记录)

delete from float2 where id = 1.11111;
DELETE 0
（没有删掉）
delete from float2 where id=1.11111111;
DELETE 0
（没有删掉）

delete from float2 where id= real'1.11111111';
DELETE 1
(real'原始值' 或者 原始值::real 可以删掉)
lyy2=# delete from float2 where id= 1.22222221::real;
DELETE 1
(real'全精度查询结果' 或者 全精度查询结果::real 可以删掉)

insert into float2 values(1.33, 3);
select * from float2;
   id    | c1
---------+----
1.33000004 |  3

select real'1.33000004', real'1.33'
-----------+------------
1.33000004 | 1.33000004

==oracle number - PG numeric==
任意精度类型，是精确的数字类型，可以通差值来比较。

==Oracle Date/Timestamp - PG Timestamp==
统一格式化后可以比较。

==Oracle-PG timestamp with time zone==
时区无法统一格式化，因此不能直接比较。



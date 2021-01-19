# flink-sql

## flink-sql 类型
--> https://help.aliyun.com/document_detail/98951.html?spm=a2c4g.11186623.6.751.1a1c4708TbYcHx
-   ddl
-   dml
-   query

```text
CREATE TABLE tableName
      (columnName dataType [, columnName dataType ]*)
      [ WITH (propertyName=propertyValue [, propertyName=propertyValue ]*) ];

INSERT INTO tableName
[ (columnName[ , columnName]*) ]
queryStatement;

EMIT 
  WITH DELAY '1'MINUTE BEFORE WATERMARK,
  WITHOUT DELAY AFTER WATERMARK

SELECT [ ALL | DISTINCT ]{ * | projectItem [, projectItem ]* }
  FROM tableExpression
  [ WHERE booleanExpression ]
  [ GROUP BY { groupItem [, groupItem ]* } ]
  [ HAVING booleanExpression ];
  
tableReference [, tableReference ]* | tableexpression
[ LEFT ] JOIN tableexpression [ joinCondition ];


select_statement
UNION ALL
select_statement;

SELECT *
FROM (
  SELECT *,
    ROW_NUMBER() OVER ([PARTITION BY col1[, col2..]]
    ORDER BY col1 [asc|desc][, col2 [asc|desc]...]) AS rownum
  FROM table_name)
WHERE rownum <= N [AND conditions]

SELECT *
FROM (
   SELECT *,
     ROW_NUMBER() OVER ([PARTITION BY col1[, col2..]
     ORDER BY timeAttributeCol [asc|desc]) AS rownum
   FROM table_name)
WHERE rownum = 1;
```
# MySQL 不同方式插入速度对比（仅供参考）

## 测试环境

> CPU: Intel Core i7-12700H
>
> 内存: DDR5 4800MHz
>
> 磁盘: SSD PCIe4.0
> 
> MySQL 8.1.0
> 
> Spring Boot 3.1.2

## 测试结果

| 方式 \ 插入数据量                                        |   5万   |  10万   |  100万   |
|:--------------------------------------------------|:------:|:------:|:-------:|
| Mybatis 循环插入                                      | 41.73s | 93.26s | 716.35s |
| Mybatis-Plus 循环插入                                 | 41.59s | 87.56s | 747.62s |
| Mybatis 批量插入                                      | 0.87s  | 1.69s  | 10.23s  |
| Mybatis-Plus 批量插入（rewriteBatchedStatements=false） | 4.03s  | 6.86s  | 57.07s  |
| Mybatis-Plus 批量插入（rewriteBatchedStatements=true）  | 0.83s  | 1.09s  |  7.47s  |
| Mybatis-Plus insertBatchSomeColumn                | 0.79s  | 1.57s  | 10.19s  |

## 结论

### 1.不指定或 rewriteBatchedStatements=false 时，速度排名：

Mybatis 批量插入 ≈ Mybatis-Plus insertBatchSomeColumn > Mybatis-Plus 批量插入 > Mybatis-Plus 循环插入 ≈ Mybatis 循环插入

### 2.rewriteBatchedStatements=true 时，速度排名：

Mybatis-Plus 批量插入 > Mybatis 批量插入 ≈ Mybatis-Plus insertBatchSomeColumn > Mybatis-Plus 循环插入 ≈ Mybatis 循环插入
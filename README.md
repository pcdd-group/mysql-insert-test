# MySQL 不同方式插入速度对比

## 测试环境

> CPU: Intel Core i7-12700H
>
> 内存: DDR5 4800MHz
>
> 磁盘:    SSD PCIe4.0

## 测试结果

| 方式 \ 插入数据量                                        |   5万   |  10万   |  100万   |
|:--------------------------------------------------|:------:|:------:|:-------:|
| Mybatis-Plus 循环插入                                 | 25.96s | 53.43s | 510.49s |
| Mybatis-Plus 批量插入（rewriteBatchedStatements=false） | 4.03s  | 6.86s  | 57.07s  |
| Mybatis-Plus 批量插入（rewriteBatchedStatements=true）  | 0.83s  | 1.38s  |  9.75s  |
| Mybatis 批量插入                                      | 0.87s  | 1.38s  |  9.83s  |
| Mybatis-Plus insertBatchSomeColumn                | 0.79s  | 1.35s  |  9.84s  |

## 结论

### 1.不指定或 rewriteBatchedStatements=false 时，速度排名：

Mybatis 批量插入 ≈ Mybatis-Plus insertBatchSomeColumn > Mybatis-Plus 批量插入 > Mybatis-Plus 循环插入

### 2.rewriteBatchedStatements=true 时，速度排名：

Mybatis 批量插入 ≈ Mybatis-Plus insertBatchSomeColumn ≈ Mybatis-Plus 批量插入 > Mybatis-Plus 循环插入
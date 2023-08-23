package com.pcdd.mysqlinserttest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pcdd
 */
@Data
@NoArgsConstructor
public class Person {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}

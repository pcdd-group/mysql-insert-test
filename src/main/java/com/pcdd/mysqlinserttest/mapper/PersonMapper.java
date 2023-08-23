package com.pcdd.mysqlinserttest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pcdd.mysqlinserttest.entity.Person;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author pcdd
 */
public interface PersonMapper extends BaseMapper<Person> {

    /**
     * 清空表
     */
    @Update("TRUNCATE TABLE mysql_insert_test.person")
    void truncate();

    @Insert("INSERT INTO mysql_insert_test.person(name, age) VALUES(#{name}, #{age})")
    int insert(Person person);

    int insertBatch(List<Person> personList);

    int insertBatchSomeColumn(@Param("list") List<Person> personList);

}

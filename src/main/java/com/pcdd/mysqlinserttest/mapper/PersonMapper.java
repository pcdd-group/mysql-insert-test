package com.pcdd.mysqlinserttest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pcdd.mysqlinserttest.entity.Person;
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

    int insertBatch(List<Person> personList);

    int insertBatchSomeColumn(@Param("list") List<Person> personList);

}

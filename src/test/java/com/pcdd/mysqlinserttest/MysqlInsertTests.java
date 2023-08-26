package com.pcdd.mysqlinserttest;

import com.pcdd.mysqlinserttest.entity.Person;
import com.pcdd.mysqlinserttest.mapper.PersonMapper;
import com.pcdd.mysqlinserttest.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author pcdd
 */
@Slf4j
@SpringBootTest
@DisplayName("MySQL 插入速度对比")
class MysqlInsertTests {

    @Autowired
    PersonService personService;
    @Autowired
    PersonMapper personMapper;

    static final int N = 10_0000;

    @BeforeEach
    void clear() {
        log.info("clear BeforeEach");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        long count = personService.count();
        personMapper.truncate();

        stopWatch.stop();
        log.info("截断 {} 条数据耗时：{} s", count, stopWatch.getTotalTimeSeconds());
    }

    /**
     * rewriteBatchedStatements 开不开启均无影响
     * 5w：41.73s 42.95s
     * 10w: 93.26s 59.75s
     * 100w: 716.35s 661.17s
     */
    @Test
    @DisplayName("Mybatis 循环插入")
    void test01() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Random random = new Random();
        for (int i = 1; i <= N; i++) {
            personMapper.insert(new Person("person-" + i, random.nextInt(18, 90)));
        }

        stopWatch.stop();
        log.info("插入 {} 条数据耗时：{} s", N, stopWatch.getTotalTimeSeconds());
    }

    /**
     * rewriteBatchedStatements 开不开启均无影响
     * 5w：41.59s 29.76
     * 10w: 87.56s 58.42s
     * 100w: 747.62s 720.36s
     */
    @Test
    @DisplayName("Mybatis-Plus 循环插入")
    void test02() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Random random = new Random();
        for (int i = 1; i <= N; i++) {
            personService.save(new Person("person-" + i, random.nextInt(18, 90)));
        }

        stopWatch.stop();
        log.info("插入 {} 条数据耗时：{} s", N, stopWatch.getTotalTimeSeconds());
    }

    /**
     * rewriteBatchedStatements 开不开启均无影响
     * 5w: 0.87s 0.75s
     * 10w: 1.69s 1.32s
     * 100w: 10.23s 15.02s
     */
    @Test
    @DisplayName("Mybatis 批量插入")
    void test03() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Random random = new Random();
        List<Person> list = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            list.add(new Person("person-" + i, random.nextInt(18, 90)));
            // 防止内存溢出
            if (i % 1000 == 0) {
                personMapper.insertBatch(list);
                list.clear();
            }
        }

        stopWatch.stop();
        log.info("插入 {} 条数据耗时：{} s", N, stopWatch.getTotalTimeSeconds());
    }

    /**
     * rewriteBatchedStatements=false 时
     * 5w: 4.03s
     * 10w: 6.86s
     * 100w: 57.07s
     * <p>
     * rewriteBatchedStatements=true 时
     * 5w: 0.83s 0.50s
     * 10w: 1.09s 0.91s
     * 100w: 7.47s 10.33s
     */
    @Test
    @DisplayName("Mybatis-Plus 批量插入")
    void test04() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Random random = new Random();
        List<Person> list = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            list.add(new Person("person-" + i, random.nextInt(18, 90)));
            // 防止内存溢出
            if (i % 1000 == 0) {
                personService.saveBatch(list);
                list.clear();
            }
        }

        stopWatch.stop();
        log.info("插入 {} 条数据耗时：{} s", N, stopWatch.getTotalTimeSeconds());
    }


    /**
     * rewriteBatchedStatements 开不开启均无影响
     * 5w: 0.79s 0.62s
     * 10w: 1.57s 0.96s
     * 100w: 10.19s 14.11s
     */
    @Test
    @DisplayName("Mybatis-Plus insertBatchSomeColumn")
    void test05() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Random random = new Random();
        List<Person> list = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            list.add(new Person("person-" + i, random.nextInt(18, 90)));
            // 防止内存溢出
            if (i % 1000 == 0) {
                personMapper.insertBatchSomeColumn(list);
                list.clear();
            }
        }

        stopWatch.stop();
        log.info("插入 {} 条数据耗时：{} s", N, stopWatch.getTotalTimeSeconds());
    }

}

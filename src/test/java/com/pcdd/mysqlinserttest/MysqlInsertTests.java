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
     * 5w：25.96s
     * 10w: 53.43s
     * 100w: 510.49s
     */
    @Test
    @DisplayName("Mybatis-Plus 循环插入")
    void test01() {
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
     * rewriteBatchedStatements=false 时
     * 5w: 4.03s
     * 10w: 6.86s
     * 100w: 57.07s
     * <p>
     * rewriteBatchedStatements=true 时
     * 5w: 0.83s
     * 10w: 1.38s
     * 100w: 9.75s
     */
    @Test
    @DisplayName("Mybatis-Plus 批量插入")
    void test02() {
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
     * 5w: 0.87s
     * 10w: 1.38s
     * 100w: 9.83s
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
     * rewriteBatchedStatements 开不开启均无影响
     * 5w: 0.79s
     * 10w: 1.35s
     * 100w: 9.84s
     */
    @Test
    @DisplayName("Mybatis-Plus insertBatchSomeColumn")
    void test04() {
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

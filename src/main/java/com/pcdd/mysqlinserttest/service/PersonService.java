package com.pcdd.mysqlinserttest.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcdd.mysqlinserttest.entity.Person;
import com.pcdd.mysqlinserttest.mapper.PersonMapper;
import org.springframework.stereotype.Service;

/**
 * @author pcdd
 */
@Service
public class PersonService extends ServiceImpl<PersonMapper, Person> implements IPersonService {

}

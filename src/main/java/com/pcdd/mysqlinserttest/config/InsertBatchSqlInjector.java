package com.pcdd.mysqlinserttest.config;


import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

/**
 * 自定义Sql注入器,只要类  ? extends AbstractMethod,就可以在BaserMapper的基础上,就可以拓展自定义的
 * 方法类,其中InsertBatchSomeColumn 是mybatis plus帮我们做好的,因为它底层是拼接sql,不同的数据库sql脚本有些许不一样,目前
 * 只在mysql测试过,所以官方没有将它暴露给我们直接使用,二是让我们自己进行扩展
 *
 * @author pcdd
 */
public class InsertBatchSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        // 添加自定义方法：批量插入，方法名为 insertBatchSomeColumn
        methodList.add(new InsertBatchSomeColumn());
        return methodList;
    }

}
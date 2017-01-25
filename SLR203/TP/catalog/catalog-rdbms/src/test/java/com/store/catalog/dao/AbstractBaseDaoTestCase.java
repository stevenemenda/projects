package com.store.catalog.dao;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration({ "classpath:/applicationContext-dao-jpa-test.xml" })
@Transactional  
@RunWith(SpringJUnit4ClassRunner.class)
public class AbstractBaseDaoTestCase {


    protected int getIterableSize(Iterable<?> it){
        if (it instanceof Collection){
            return ((Collection<?>)it).size();
        }

        int i = 0;
        for (Object obj : it){
            i++;
        }
        return i;
    }
}

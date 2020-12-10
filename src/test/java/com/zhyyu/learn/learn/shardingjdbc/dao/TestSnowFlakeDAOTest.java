package com.zhyyu.learn.learn.shardingjdbc.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author zhongyu.yzy
 * @date 2020/12/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSnowFlakeDAOTest {

    @Autowired
    private TestSnowFlakeDAO testSnowFlakeDAO;

    @Test
    public void testInsert() {
        TestSnowFlakeDAO.TestSnowFlakeRow testSnowFlakeRow = new TestSnowFlakeDAO.TestSnowFlakeRow();
        testSnowFlakeRow.setName("name0");
        Integer insertCount = testSnowFlakeDAO.insert(testSnowFlakeRow);
        assertEquals(insertCount, Integer.valueOf(1));

        TestSnowFlakeDAO.TestSnowFlakeRow testSnowFlakeRow2 = new TestSnowFlakeDAO.TestSnowFlakeRow();
        testSnowFlakeRow2.setName("aname0");
        testSnowFlakeDAO.insert(testSnowFlakeRow2);
    }

}
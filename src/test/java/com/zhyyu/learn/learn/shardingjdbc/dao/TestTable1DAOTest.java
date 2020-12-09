package com.zhyyu.learn.learn.shardingjdbc.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author zhongyu.yzy
 * @date 2020/12/8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTable1DAOTest {

    @Autowired
    private TestTable1DAO testTable1DAO;

    @Test
    public void testQueryAll() {
        List<TestTable1DAO.TestTable1Row> testTable1Rows = testTable1DAO.queryAll();
        System.out.println(testTable1Rows);
        testTable1Rows.stream().findFirst().ifPresent(row -> {
            assertEquals(row.getName(), "name1");
        });
    }

    @Test
    public void testQueryAllBySharding() {
        List<TestTable1DAO.TestTable1Row> testTable1Rows2 = testTable1DAO.queryAllBySharding();
        System.out.println(testTable1Rows2);
        assertEquals(testTable1Rows2.size(), 2);
    }

    @Test
    public void testUpdateById() {
        Integer updateCount = testTable1DAO.updateNameById("name1u", 1);
        assertEquals(updateCount, Integer.valueOf(1));
    }

    @Test
    public void testQueryById() {
        TestTable1DAO.TestTable1Row testTable1Row = testTable1DAO.queryById(1);
        assertEquals(testTable1Row.getId(), Integer.valueOf(1));
        System.out.println(testTable1Row);
    }

    @Test
    public void testCombineUpdateAndQuery() {
        TestTable1DAO.TestTable1Row testTable1Row = testTable1DAO.queryAfterUpdateNameById("name1u", 1);
        System.out.println(testTable1Row);
        assertEquals(testTable1Row.getName(), "name1u");
    }

}
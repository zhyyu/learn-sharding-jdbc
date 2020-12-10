package com.zhyyu.learn.learn.shardingjdbc.dao;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 需开启 com.zhyyu.learn.learn.shardingjdbc.config.ShardingJdbcDataSourceConfig#shardingJdbcDataSource() @Bean
 * @author zhongyu.yzy
 * @date 2020/12/8
 */
@Repository
public class TestTable1DAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 若sql 中直接使用物理表，依然可以直接定位到物理表（不使用逻辑表）
     * default-data-source:
     * https://shardingsphere.apache.org/document/legacy/4.x/document/cn/faq/
     * @return
     */
    public List<TestTable1Row> queryAll() {
        List<TestTable1Row> testTable1RowList = jdbcTemplate.query("select * from test_table1", getTestTableRowRowMapper());
        return testTable1RowList;
    }

    public List<TestTable1Row> queryAllBySharding() {
        List<TestTable1Row> testTable1RowList = jdbcTemplate.query("select * from test_table", getTestTableRowRowMapper());
        return testTable1RowList;
    }

    public Integer updateNameById(String name, Integer id) {
        int update = jdbcTemplate.update("update test_table set name = ? where id = ?", name, id);
        return update;
    }

    public TestTable1Row queryById(Integer id) {
        TestTable1Row testTable1Row = jdbcTemplate.queryForObject("select * from test_table where id = ?", getTestTableRowRowMapper(), id);
        return testTable1Row;
    }

    /**
     * 注意：使用事务情况下使用同一连接均使用主库，若不使用事务，则更新使用主库，查询使用从库（可能存在刚更新未查询到情况-主从库同步时延）
     * @param name
     * @param id
     * @return
     */
    @Transactional
    public TestTable1Row queryAfterUpdateNameById(String name, Integer id) {
        updateNameById(name, id);
        return queryById(id);
    }

    private RowMapper<TestTable1Row> getTestTableRowRowMapper() {
        return (rs, rowNum) -> {
            TestTable1Row testTable1Row = new TestTable1Row();
            testTable1Row.setId(rs.getInt("id"));
            testTable1Row.setName(rs.getString("name"));
            return testTable1Row;
        };
    }

    @Data
    public static class TestTable1Row {
        private Integer id;
        private String name;
    }

}

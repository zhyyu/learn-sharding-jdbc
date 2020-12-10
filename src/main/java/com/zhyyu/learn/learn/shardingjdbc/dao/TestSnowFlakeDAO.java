package com.zhyyu.learn.learn.shardingjdbc.dao;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 需开启 com.zhyyu.learn.learn.shardingjdbc.config.ShardingJdbcDataSourceConfig#shardingJdbcSnowFlakeDataSource() @Bean
 * @author zhongyu.yzy
 * @date 2020/12/10
 */
@Repository
public class TestSnowFlakeDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer insert(TestSnowFlakeRow testSnowFlakeRow) {
        int insertCount = jdbcTemplate.update("insert into test_snow_flake (name) values (?)", testSnowFlakeRow.getName());
        return insertCount;
    }

    @Data
    public static class TestSnowFlakeRow {
        private Long id;
        private String name;
    }

}

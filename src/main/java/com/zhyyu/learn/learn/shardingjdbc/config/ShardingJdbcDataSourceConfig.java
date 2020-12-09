package com.zhyyu.learn.learn.shardingjdbc.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.NoneShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 数据分片
 * <url>https://shardingsphere.apache.org/document/legacy/4.x/document/cn/manual/sharding-jdbc/configuration/config-java/</url>
 * 读写分离
 * <url>https://shardingsphere.apache.org/document/legacy/4.x/document/cn/manual/sharding-jdbc/usage/read-write-splitting/</url>
 * @author zhongyu.yzy
 * @date 2020/12/8
 */
@Configuration
public class ShardingJdbcDataSourceConfig {

    @Bean
    public DataSource shardingJdbcDataSource() throws SQLException {
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        // 配置第一个数据源
        HikariDataSource dataSource1 = new HikariDataSource();
        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1.setJdbcUrl("jdbc:mysql://localhost:3306/test_schema_1");
        dataSource1.setUsername("root");
        dataSource1.setPassword("root");
        dataSourceMap.put("test_schema_1", dataSource1);

        // 从库
        HikariDataSource dataSource1Salve = new HikariDataSource();
        dataSource1Salve.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1Salve.setJdbcUrl("jdbc:mysql://localhost:3306/test_schema_1_slave");
        dataSource1Salve.setUsername("root");
        dataSource1Salve.setPassword("root");
        dataSourceMap.put("test_schema_1_slave", dataSource1Salve);

        // 配置test_table表规则
        TableRuleConfiguration testTableTableRuleConfig = new TableRuleConfiguration("test_table","test_schema_1.test_table${0..1}");

        // 配置分库 + 分表策略
        testTableTableRuleConfig.setDatabaseShardingStrategyConfig(new NoneShardingStrategyConfiguration());
        testTableTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "test_table${id % 2}"));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(testTableTableRuleConfig);

        // 从库配置
        MasterSlaveRuleConfiguration masterSlaveRuleConfiguration = new MasterSlaveRuleConfiguration("test_schema_1", "test_schema_1",
                Arrays.asList("test_schema_1_slave"));
        shardingRuleConfig.getMasterSlaveRuleConfigs().add(masterSlaveRuleConfiguration);

        // 获取数据源对象
        Properties properties = new Properties();
        properties.setProperty("sql.show", "true");
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, properties);
        return dataSource;
    }

}

package com.zhyyu.learn.learn.shardingjdbc.spi;

import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;

/**
 * @author zhongyu.yzy
 * @date 2020/12/9
 */
public class SnowflakeShardingKeyGeneratorTest {

    public static void main(String[] args) {
        SnowflakeShardingKeyGenerator snowflakeShardingKeyGenerator = new SnowflakeShardingKeyGenerator();
        for (int i = 0; i < 10; i++) {
            Comparable<?> key = snowflakeShardingKeyGenerator.generateKey();
            System.out.println(key);
        }

        System.out.println(Integer.MAX_VALUE);
        System.out.println(Long.MAX_VALUE);
    }

}

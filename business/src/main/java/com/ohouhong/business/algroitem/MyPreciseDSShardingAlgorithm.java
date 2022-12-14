package com.ohouhong.business.algroitem;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Collection;

/**
 * @description: 实现精确分片-库
 **/
@Component
public class MyPreciseDSShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
    //select * from course where cid = ? or cid in (?,?)
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        String logicTableName = shardingValue.getLogicTableName();
        String cid = shardingValue.getColumnName();
        Long cidValue = shardingValue.getValue();


        //实现 course_$->{cid%2+1)
        BigInteger shardingValueB = BigInteger.valueOf(cidValue);
        BigInteger resB = (shardingValueB.mod(new BigInteger("2"))).add(new BigInteger("1"));
        String key = "m"+resB;
        if(availableTargetNames.contains(key)){
            return key;
        }
        // 返回 couse_1, course_2
        throw new UnsupportedOperationException("route "+ key +" is not supported ,please check your config");
    }
}

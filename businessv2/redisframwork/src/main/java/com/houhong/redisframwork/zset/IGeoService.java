package com.houhong.redisframwork.zset;

import com.houhong.redisframwork.domain.CityInfo;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metric;
import org.springframework.data.redis.connection.RedisGeoCommands;

import java.awt.*;
import java.util.Collection;

public interface IGeoService {


    /**
     * 把城市信息保存到 Redis 中
     *
     * @param cityInfos {@link CityInfo}
     * @return 成功保存的个数
     */
    Long saveCityInfoToRedis(Collection<CityInfo> cityInfos);

    /**
     * 获取给定城市的坐标
     *
     * @param cities 给定城市 key
     * @return {@link Point}s
     */
    List<Point> getCityPos(String[] cities);

    /**
     * 获取两个城市之间的距离
     *
     * @param city1  第一个城市
     * @param city2  第二个城市
     * @param metric {@link Metric} 单位信息, 可以是 null
     * @return {@link Distance}
     */
    Distance getTwoCityDistance(String city1, String city2, Metric metric);

    /**
     * 根据给定地理位置坐标获取指定范围内的地理位置集合
     *
     * @param within {@link Circle} 中心点和距离
     * @param args   {@link RedisGeoCommands.GeoRadiusCommandArgs} 限制返回的个数和排序方式, 可以是 null
     * @return {@link RedisGeoCommands.GeoLocation}
     */
    GeoResults<RedisGeoCommands.GeoLocation<String>> getPointRadius(
            Circle within, RedisGeoCommands.GeoRadiusCommandArgs args);

    /**
     * 根据给定地理位置获取指定范围内的地理位置集合
     */
    GeoResults<RedisGeoCommands.GeoLocation<String>> getMemberRadius(
            String member, Distance distance, RedisGeoCommands.GeoRadiusCommandArgs args);

    /**
     * 获取某个地理位置的 geohash 值
     *
     * @param cities 给定城市 key
     * @return city geohashs
     */
    java.util.List<String> getCityGeoHash(String[] cities);
}

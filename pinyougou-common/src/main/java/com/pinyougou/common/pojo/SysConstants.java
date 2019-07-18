package com.pinyougou.common.pojo;

/**
 * 描述 系统的常量对象
 *
 * @author 三国的包子
 * @version 1.0
 * @package com.pinyougou.common.util
 * @since 1.0
 */
public class SysConstants {
    //秒杀中的某商品的队列名前缀
    //一个商品就是一个队列  队列中的数据便是商品本身 队列的长度便是 商品的库存
    public static final String SEC_KILL_GOODS_PREFIX="SEC_KILL_GOODS_ID_";

    //用于表示用户抢购的下单的排队
    public static final String SEC_KILL_USER_ORDER_LIST="SEC_KILL_USER_ORDER_LIST";

    //用于标识某商品被抢购的人数队列的名字前缀  一个商品就是一个队列

    public static final String SEC_KILL_LIMIT_PREFIX="SEC_KILL_LIMIT_SEC_ID_";

    //用于标识用户已秒杀下单排队中的key
    public static final String SEC_USER_QUEUE_FLAG_KEY="SEC_USER_QUEUE_FLAG_KEY";

    /**
     * 某广告的列表redis的KEY
     */
    public static final String CONTENT_REDIS_KEY="CONTENT_REDIS_KEY";


}

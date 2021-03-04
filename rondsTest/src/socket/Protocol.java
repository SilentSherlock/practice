package com.bosssoft.hr.train.j2se.basic.example.socket;

/**
 * @description: 传输的数据协议定义这里 记得序列化否则无法放对象流传输虽然我们借助json传输不用到还是写下留一手吧
 * @author: lwh
 * @create: 2020/11/5 14:48
 * @version: v1.0
 **/
public class Protocol {
    /**
     * 这里是一个内部类 报文投 一般是包含
     */
    class Head{
        /**
         * 版本 业务流水号 等 公共字段 这里省略啦
         */

        /**
         * 定义body类型，方便服务端强制转化body
         */
        int bodyType;
    }
    Object body;
}

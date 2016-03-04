package com.bob.lottery.util;

/**
 * Created by Administrator on 2016/1/30.
 */
public interface ConstantValue {
    //编码方式
    String ENCONDING="UTF-8";
    //代理的ID
    String AGENTERID="889931";
    //信息来源
    String SOURCE="ivr";
    //加密算法
    String COMPRESS="DES";
    //自代理商密钥（.so）JNI
    String AGENTER_PASSWORD="9ab62a694d8bf6ced1fab6acd48d02f8";
    //des加密用密钥
    String DES_PASSWORD = "9b2648fcdfbad80f";
    // 10.0.2.2模拟器如果需要跟PC机通信127.0.0.1
    //String LOTTERY_URI = "http://10.0.3.2:8080/ZCWService/Entrance";
    // 10.0.2.2模拟器如果需要跟PC机通信127.0.0.1
    String LOTTERY_URI = "http://192.168.56.1:8080/ZCWService/Entrance";

    int VIEW_FISRT=1;
    int VIEW_SECOND=2;

    //购彩大厅
    int VIEW_HALL=10;
    //双色球选号界面
    int VIEW_SSQ=15;
    //购物车
    int VIEWSHOPPING=20;
    //追期和信投的设置界面
    int VIEW_PREBET=25;
    //双色球的标识
    int SSQ=118;
    //服务器返回成功
    String SUCCESS="0";


}

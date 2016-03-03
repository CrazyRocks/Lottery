package com.bob.lottery.factory;

import android.content.Context;

import com.bob.lottery.BaseApplication;
import com.bob.lottery.R;
import com.bob.lottery.engine.UserEngine;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 2016/3/2.
 */

//工厂类
public class BeanFactory {

    //依据配置文件加载实例
    private static Properties properties;
    public  static Context mContext;

    static {
        properties=new Properties();
        mContext=BaseApplication.mAppContext;
        System.out.println("上下文："+mContext);

        //eclipse中必须在src的根目录下
        try {
            properties.load(mContext.getAssets().open("path.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //加载需要的实现类
    public static <T> T getImpl(Class<T> clazz){

        String key = clazz.getSimpleName();
        //System.out.println(key);
        String className = properties.getProperty(key);
        try {
           return (T) Class.forName(className).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.bob.lottery.bean;

/**
 * Created by Administrator on 2016/1/30.
 */
//用户登录信息封装
public class User {
    //用户名
    private String username;
    //登录密码
    private String password;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

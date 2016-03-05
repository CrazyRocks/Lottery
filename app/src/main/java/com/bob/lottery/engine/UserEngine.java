package com.bob.lottery.engine;

import com.bob.lottery.bean.User;
import com.bob.lottery.protocol.Message;

/**
 * Created by Administrator on 2016/3/2.
 */

//用户相关业务操作接口
public interface UserEngine {
    //用户登录
     Message login(User user);

     //获取用户余额
     Message getBalance(User param);

     //追期与倍投
     Message bet(User param);
}

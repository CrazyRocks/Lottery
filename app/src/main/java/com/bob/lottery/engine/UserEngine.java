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
}

package com.bob.lottery;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.bob.lottery.bean.User;
import com.bob.lottery.engine.UserEngineImpl;
import com.bob.lottery.protocol.Message;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void UserLoginTest() throws Exception{
        UserEngineImpl engine=new UserEngineImpl();
        User user=new User();
        user.setUsername("12321");
        user.setPassword("123321");
        Message login = engine.login(user);
        System.out.println(login.getBody().getOelement().getErrorcode());
    }
}
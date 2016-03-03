package com.bob.lottery;

import android.test.AndroidTestCase;
import android.util.Log;

import com.bob.lottery.bean.User;
import com.bob.lottery.engine.UserEngineImpl;
import com.bob.lottery.protocol.Message;
import com.bob.lottery.element.CurrentIssueElement;

/**
 * Created by Administrator on 2016/1/31.
 */
public class TestCases extends AndroidTestCase {
    private static final String TAG="XmlTest";

    public void UserLoginTest() throws Exception{
        UserEngineImpl engine=new UserEngineImpl();
        User user=new User();
        user.setUsername("12321");
        user.setPassword("123321");
        Message login = engine.login(user);
        System.out.println(login.getBody().getOelement().getErrorcode());
    }

    public void creatXml() throws Exception{
        Message message=new Message();
        CurrentIssueElement element=new CurrentIssueElement();
        element.getLotteryid().setTagValue("118");
        String xml=message.getXml(element);
        Log.i(TAG, xml);
    }
}

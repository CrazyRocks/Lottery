package com.bob.lottery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bob.lottery.bean.User;
import com.bob.lottery.engine.UserEngineImpl;
import com.bob.lottery.net.protocol.Message;
import com.bob.lottery.net.protocol.element.CurrentIssueElement;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //creatXml();
        //测试线程
        MyThread test=new MyThread();
        test.start();
    }


    private static final String TAG="XmlTest";


    public void creatXml(){
        Message message=new Message();
        CurrentIssueElement element=new CurrentIssueElement();
        element.getLotteryid().setTagValue("118");
        String xml=message.getXml(element);
        Log.i(TAG, xml);
    }

    public void UserLoginTest(){
        UserEngineImpl engine=new UserEngineImpl();
        User user=new User();
        user.setUsername("12321");
        user.setPassword("123321");
        Message login = engine.login(user);
        System.out.println(TAG+login.getBody().getOelement().getErrorcode());
        System.out.println(TAG+login.getBody().getOelement().getErrormsg());
    }


    public class MyThread extends Thread{
        @Override
        public void run() {
            try{
                UserLoginTest();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}

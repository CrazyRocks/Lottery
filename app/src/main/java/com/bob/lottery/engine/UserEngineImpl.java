package com.bob.lottery.engine;

import android.util.Xml;

import com.bob.lottery.util.ConstantValue;
import com.bob.lottery.bean.User;
import com.bob.lottery.net.HttpClientUtil;
import com.bob.lottery.protocol.Message;
import com.bob.lottery.element.UserLoginElement;
import com.bob.lottery.util.DES;

import org.apache.commons.codec.digest.DigestUtils;
import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.io.StringReader;

/**
 * Created by Administrator on 2016/1/30.
 */

//用户登录
public class UserEngineImpl extends BaseEngine implements UserEngine{

    //用户登录
    @Override
    public Message login(User user) {
        //1.获取登录用的xml
        //创建登录用的Element
        UserLoginElement element = new UserLoginElement();
        //设置用户数据
        element.getActpassword().setTagValue(user.getPassword());
        Message message = new Message();
        message.getHeader().getUsername().setTagValue(user.getUsername());
        String xml = message.getXml(element);
        System.out.println(xml);

        //如果第三步比对通过result
        Message result=getResult(xml);

        if (result!=null) {
            //4.请求结果处理，解析明文
            XmlPullParser parser = Xml.newPullParser();
            try {
                DES des = new DES();
                String body = "<body>" + des.authcode(result.getBody().getServiceBodyInsideDESInfo(),
                        "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";
                parser.setInput(new StringReader(body));

                int eventType = parser.getEventType();
                String name;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            name = parser.getName();
                            if ("errorcode".equals(name)) {
                                result.getBody().getOelement().setErrorcode(parser.nextText());
                                System.out.println(name);
                            }
                            if ("errormsg".equals(name)) {
                                result.getBody().getOelement().setErrormsg(parser.nextText());
                                System.out.println(name);

                            }
                            break;
                    }
                    eventType = parser.next();
                }

                System.out.println(result.getBody().getOelement().getErrorcode());
                System.out.println(result.getBody().getOelement().getErrormsg());

                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }


    //用户登录
    public Message loginOne(User user) {
        //1.获取登录用的xml
        //创建登录用的Element
        UserLoginElement element = new UserLoginElement();
        //设置用户数据
        element.getActpassword().setTagValue(user.getPassword());
        Message message = new Message();
        message.getHeader().getUsername().setTagValue(user.getUsername());
        String xml = message.getXml(element);
        System.out.println(xml);

        //2.发送xml到服务器，等待回复
        //在此之前没有网络类型判断
        HttpClientUtil util = new HttpClientUtil();
        InputStream is = util.sendXml(ConstantValue.LOTTERY_URI, xml);
        System.out.println(is);
        //判断输入非空
        if (is != null) {
            Message result = new Message();
            //3.数据校验
            XmlPullParser parser = Xml.newPullParser();
            try {
                parser.setInput(is,ConstantValue.ENCONDING);
                int eventType = parser.getEventType();
                String name;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            name = parser.getName();
                            if ("timestamp".equals(name)) {
                                result.getHeader().getTimestamp().setTagValue(parser.nextText());
                                System.out.println(name);
                            }
                            if ("digest".equals(name)) {
                                result.getHeader().getDigest().setTagValue(parser.nextText());
                                System.out.println(name);
                            }
                            if ("body".equals(name)) {
                                result.getBody().setServiceBodyInsideDESInfo(parser.nextText());
                                System.out.println(name);
                            }
                            break;
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }




            //原数据还原
            //明文body
            DES des = new DES();
            String body = "<body>" + des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";
            System.out.println(body);
            String orgInfo = result.getHeader().getTimestamp().getTagValue() + ConstantValue.AGENTER_PASSWORD + body;
            System.out.println(orgInfo);
            //生成手机端MD5
            String md5Hex = DigestUtils.md5Hex(orgInfo);
            System.out.println(md5Hex);
            if (md5Hex.equals(result.getHeader().getDigest().getTagValue())) {
                //4.请求结果处理，解析明文
                parser = Xml.newPullParser();
                try {
                    parser.setInput(new StringReader(body));

                    int eventType = parser.getEventType();
                    String name;

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        switch (eventType) {
                            case XmlPullParser.START_TAG:
                                name = parser.getName();
                                if ("errorcode".equals(name)) {
                                    result.getBody().getOelement().setErrorcode(parser.nextText());
                                    System.out.println(name);
                                }
                                if ("errormsg".equals(name)) {
                                    result.getBody().getOelement().setErrormsg(parser.nextText());
                                    System.out.println(name);

                                }
                                break;
                        }
                        eventType = parser.next();
                    }

                    System.out.println(result.getBody().getOelement().getErrorcode());
                    System.out.println(result.getBody().getOelement().getErrormsg());

                    return result;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
   }

}

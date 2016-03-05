package com.bob.lottery.engine;

import android.util.Xml;

import com.bob.lottery.bean.ShoppingCart;
import com.bob.lottery.bean.Ticket;
import com.bob.lottery.element.BalanceElement;
import com.bob.lottery.element.BetElement;
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

    @Override
    public Message getBalance(User user) {
        BalanceElement element = new BalanceElement();

        Message message = new Message();
        message.getHeader().getUsername().setTagValue(user.getUsername());
        String xml = message.getXml(element);

        Message result = super.getResult(xml);

        if (result != null) {

            // 第四步：请求结果的数据处理
            // body部分的第二次解析，解析的是明文内容

            XmlPullParser parser = Xml.newPullParser();
            try {

                DES des = new DES();
                String body = "<body>" + des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";

                parser.setInput(new StringReader(body));

                int eventType = parser.getEventType();
                String name;

                BalanceElement resultElement = null;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            name = parser.getName();
                            if ("errorcode".equals(name)) {
                                result.getBody().getOelement().setErrorcode(parser.nextText());
                            }
                            if ("errormsg".equals(name)) {
                                result.getBody().getOelement().setErrormsg(parser.nextText());
                            }

                            // 正对于当前请求
                            if ("element".equals(name)) {
                                resultElement = new BalanceElement();
                                result.getBody().getElements().add(resultElement);
                            }

                            if ("investvalues".equals(name)) {
                                if (resultElement != null) {
                                    resultElement.setInvestvalues(parser.nextText());
                                }
                            }

                            break;
                    }
                    eventType = parser.next();
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    @Override
    public Message bet(User user) {
        BetElement element = new BetElement();
        element.getLotteryid().setTagValue(ShoppingCart.getInstance().getLotteryid().toString());

        // 彩票的业务里面：
        // ①关于注数的计算
        // ②关于投注信息封装（用户投注号码）

        // 010203040506|01^01020304050607|01

        StringBuffer codeBuffer = new StringBuffer();
        for (Ticket item : ShoppingCart.getInstance().getTickets()) {
            codeBuffer.append("^").append(item.getRedNum().replaceAll(" ", "")).append("|").append(item.getBlueNum().replaceAll(" ", ""));
        }

        element.getLotterycode().setTagValue(codeBuffer.substring(1));

        element.getIssue().setTagValue(ShoppingCart.getInstance().getIssue());
        element.getLotteryvalue().setTagValue((ShoppingCart.getInstance().getLotteryvalue() * 100) + "");

        element.getLotterynumber().setTagValue(ShoppingCart.getInstance().getLotterynumber().toString());
        element.getAppnumbers().setTagValue(ShoppingCart.getInstance().getAppnumbers().toString());
        element.getIssuesnumbers().setTagValue(ShoppingCart.getInstance().getIssuesnumbers().toString());

        element.getIssueflag().setTagValue((ShoppingCart.getInstance().getIssuesnumbers() > 1) ? "1" : "0");

        Message message = new Message();
        message.getHeader().getUsername().setTagValue(user.getUsername());

        String xml = message.getXml(element);

        Message result = super.getResult(xml);

        if (result != null) {

            // 第四步：请求结果的数据处理
            // body部分的第二次解析，解析的是明文内容

            XmlPullParser parser = Xml.newPullParser();
            try {

                DES des = new DES();
                String body = "<body>" + des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";

                parser.setInput(new StringReader(body));

                int eventType = parser.getEventType();
                String name;

                BetElement resultElement = null;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            name = parser.getName();
                            if ("errorcode".equals(name)) {
                                result.getBody().getOelement().setErrorcode(parser.nextText());
                            }
                            if ("errormsg".equals(name)) {
                                result.getBody().getOelement().setErrormsg(parser.nextText());
                            }

                            // 正对于当前请求
                            if ("element".equals(name)) {
                                resultElement = new BetElement();
                                result.getBody().getElements().add(resultElement);
                            }

                            if ("actvalue".equals(name)) {
                                if (resultElement != null) {
                                    resultElement.setActvalue(parser.nextText());
                                }
                            }

                            break;
                    }
                    eventType = parser.next();
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return null;
    }


}

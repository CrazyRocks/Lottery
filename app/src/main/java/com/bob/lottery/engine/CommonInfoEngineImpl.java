package com.bob.lottery.engine;

import android.util.Xml;

import com.bob.lottery.element.CurrentIssueElement;
import com.bob.lottery.protocol.Message;
import com.bob.lottery.util.ConstantValue;
import com.bob.lottery.util.DES;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;

/**
 * Created by Administrator on 2016/3/4.
 */
public class CommonInfoEngineImpl extends BaseEngine implements CommonInfoEngine {
    @Override
    public Message getCurrentIssueInfo(Integer lotteryId) {
        //1.获取xml
        CurrentIssueElement element=new CurrentIssueElement();
        element.getLotteryid().setTagValue(lotteryId.toString());
        Message message=new Message();
        String xml = message.getXml(element);
        Message result = super.getResult(xml);
        if (result!=null){
            //4.请求结果处理，解析明文
            XmlPullParser parser = Xml.newPullParser();
            try {
                DES des = new DES();
                String body = "<body>" + des.authcode(result.getBody().getServiceBodyInsideDESInfo(),
                        "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";
                parser.setInput(new StringReader(body));

                int eventType = parser.getEventType();
                String name;
                //返回结果
                CurrentIssueElement resultElement=null;

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

                            if ("element".equals(name)){
                                resultElement=new CurrentIssueElement();
                                result.getBody().getElements().add(resultElement);
                            }
                            //解析特殊的数据
                            if ("issue".equals(name)){
                                if (resultElement!=null){
                                    resultElement.setIssue(parser.nextText());
                                }
                            }

                            if ("lasttime".equals(name)){
                                if (resultElement!=null){
                                    resultElement.setLasttime(parser.nextText());
                                }
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
}

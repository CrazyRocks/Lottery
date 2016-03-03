package com.bob.lottery.engine;

import android.util.Xml;

import com.bob.lottery.net.HttpClientUtil;
import com.bob.lottery.protocol.Message;
import com.bob.lottery.util.ConstantValue;
import com.bob.lottery.util.DES;

import org.apache.commons.codec.digest.DigestUtils;
import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/3/2.
 */
public abstract class BaseEngine {

    public Message getResult(String xml){
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
                parser.setInput(is, ConstantValue.ENCONDING);
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

            //3.原数据还原
            //明文body
            DES des = new DES();
            String body = "<body>" + des.authcode(result.getBody().getServiceBodyInsideDESInfo(),
                    "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";
            System.out.println(body);
            String orgInfo = result.getHeader().getTimestamp().getTagValue() +
                    ConstantValue.AGENTER_PASSWORD + body;
            System.out.println(orgInfo);
            //生成手机端MD5
            String md5Hex = DigestUtils.md5Hex(orgInfo);
            System.out.println(md5Hex);
            if (md5Hex.equals(result.getHeader().getDigest().getTagValue())) {
                //比对通过
                return result;
            }
        }

        return null;
    }

}

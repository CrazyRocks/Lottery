package com.bob.lottery.protocol;

import android.util.Xml;

import com.bob.lottery.util.ConstantValue;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;

/**
 * Created by Administrator on 2016/1/30.
 */
//协议封装
public class Message {
    //协议头
    private Header header=new Header();
    //协议内容
    private Body body=new Body();

    public Header getHeader() {
        return header;
    }
    public Body getBody() {
        return body;
    }

    //序列化协议
    public void serializerMessage(XmlSerializer serializer){
        try{
            serializer.startTag(null,"message");
            serializer.attribute(null, "version", "1.0");
            //传入body.getWholeBody()进行MD5加密，body.getWholeBody()并未进行DES加密
            header.serializerHeader(serializer, body.getWholeBody());
            //获取完整的明文
            //body.serializerBody(serializer);
            serializer.startTag(null,"body");
            //DES加密
            serializer.text(body.getBodyInsideDESInfo());
            serializer.endTag(null,"body");
            serializer.endTag(null,"message");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //获取请求的xml文件
    public String getXml(Element element){
        if (element==null){
            throw new IllegalArgumentException("element is null");
        }
        //按标识需要设置
        header.getTransactiontype().setTagValue(element.getTransactionType());
        //请求内容需要设置
        body.getElements().add(element);


        //序列化器
        XmlSerializer serializer= Xml.newSerializer();
        StringWriter writer=new StringWriter();
        try {
            //设置输出
            serializer.setOutput(writer);
            serializer.startDocument(ConstantValue.ENCONDING, null);
            //调用序列化
            this.serializerMessage(serializer);
            serializer.endDocument();
            return writer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //需要处理的问题
    //1.MD5明文
    //2.请求的标识
    //3.请求的内容
    //4.加密的body
    //5.通用的Element


    @Override
    public String toString() {
        return super.toString();
    }
}

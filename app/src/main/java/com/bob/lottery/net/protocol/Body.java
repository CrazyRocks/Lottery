package com.bob.lottery.net.protocol;

import android.util.Xml;

import com.bob.lottery.ConstantValue;
import com.bob.lottery.util.DES;

import org.apache.commons.lang3.StringUtils;
import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/30.
 */
//消息体节点
public class Body {
    //标识集合
    private List<Element> elements=new ArrayList<>();

    //处理服务器回复
    private String serviceBodyInsideDESInfo;//服务器恢复到body中的DES加密信息
    private Oelement oelement=new Oelement();

    public Oelement getOelement() {
        return oelement;
    }

    public String getServiceBodyInsideDESInfo() {
        return serviceBodyInsideDESInfo;
    }

    public void setServiceBodyInsideDESInfo(String serviceBodyInsideDESInfo) {
        this.serviceBodyInsideDESInfo = serviceBodyInsideDESInfo;
    }

    //序列化请求
    public void serializerBody(XmlSerializer serializer){
        try{
            serializer.startTag(null,"body");
            serializer.startTag(null, "elements");
            for (Element element:elements){
                element.serializerElement(serializer);
            }
            serializer.endTag(null,"elements");
            serializer.endTag(null,"body");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //获取到完整体的body
    public String getWholeBody() {
        StringWriter writer=new StringWriter();
        XmlSerializer temp = Xml.newSerializer();
        try{
            temp.setOutput(writer);
            this.serializerBody(temp);
            //必须要关闭
            temp.flush();
            return writer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public List<Element> getElements(){
        return elements;
    }

    //获取body里面的DES加密数据
    public String getBodyInsideDESInfo(){
        //加密数据
        String wholeBody=getWholeBody();
        String orgDesInfo= StringUtils.substringBetween(wholeBody, "<body>", "</body>");
        //加密
        //加密调试---2
        //加密算法实现不同：（服务器端与移动端，空格与符号，看不见的字符（回车换行））
        //加密密钥
        //加密的原始数据不同
        DES des=new DES();
        return des.authcode(orgDesInfo,"DECODE", ConstantValue.DES_PASSWORD);
    }

}

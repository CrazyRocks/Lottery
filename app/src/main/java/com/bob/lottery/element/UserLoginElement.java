package com.bob.lottery.element;

import com.bob.lottery.protocol.Element;
import com.bob.lottery.protocol.Leaf;

import org.xmlpull.v1.XmlSerializer;

/**
 * Created by Administrator on 2016/1/30.
 */
//用户登录请求
public class UserLoginElement extends Element {
    private Leaf actpassword=new Leaf("actpassword");

    @Override
    public void serializerElement(XmlSerializer serializer) {
        try{

            serializer.startTag(null,"element");
            actpassword.serializerLeaf(serializer);
            serializer.endTag(null,"element");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getTransactionType() {
        return "14001";
    }

    public Leaf getActpassword() {
        return actpassword;
    }
}

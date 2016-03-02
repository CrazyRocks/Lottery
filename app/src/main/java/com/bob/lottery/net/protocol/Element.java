package com.bob.lottery.net.protocol;

import org.xmlpull.v1.XmlSerializer;

/**
 * Created by Administrator on 2016/1/30.
 */
//请求数据包装
public abstract class Element {
    //不会将所有请求的叶子对象放到Element里面
    //Element将作为所有请求的代表（公共部分）
    //公共部分：
    //1.每个请求都必须序列化自己
    public abstract void serializerElement(XmlSerializer serializer);
    //2.每个请求都有自己的标识
    public abstract String getTransactionType();


    //包含的内容
    //序列化
    //特有：请求的标识
//    private Leaf lotteryid=new Leaf("lotteryid");
//    private Leaf issues=new Leaf("issues","1");
//
//    public Leaf getLotteryid() {
//        return lotteryid;
//    }

//
//    //序列化请求
//    public void serializerElement(XmlSerializer serializer){
//        try{
//
//            serializer.startTag(null,"element");
//            lotteryid.serializerLeaf(serializer);
//            issues.serializerLeaf(serializer);
//            serializer.endTag(null,"element");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }


//    //获取标识
//    public String getTransactionType(){
//        return "12002";
//    }
}

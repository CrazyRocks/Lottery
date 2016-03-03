package com.bob.lottery.protocol;

import com.bob.lottery.util.ConstantValue;

import org.apache.commons.codec.digest.DigestUtils;
import org.xmlpull.v1.XmlSerializer;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2016/1/30.
 */
//头节点的封装
public class Header {
    //第一部分
    private Leaf agenterid=new Leaf("agenterid", ConstantValue.AGENTERID);
    private Leaf source=new Leaf("source",ConstantValue.SOURCE);
    private Leaf compress=new Leaf("compress",ConstantValue.COMPRESS);
    //第二部分
    private Leaf messagerid=new Leaf("messagerid");
    private Leaf timestamp=new Leaf("timestamp");
    private Leaf digest=new Leaf("digest");
    //第三部分
    private Leaf transactiontype=new Leaf("transactiontype");
    private Leaf username=new Leaf("username");


    //序列化头
    public void serializerHeader(XmlSerializer serializer,String body){
        try{

            //将第二部分设置数据
            SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
            String time=format.format(new Date());
            timestamp.setTagValue(time);
            //时间戳+6为随机数
            Random random=new Random();
            int num=random.nextInt(999999)+1;//左闭右开
            DecimalFormat decimalFormat=new DecimalFormat("000000");
            messagerid.setTagValue(time+decimalFormat.format(num));

            //digest:时间戳+代理商密码+完整的明文
            String orgInfo=time+ConstantValue.AGENTER_PASSWORD+body;
            //加密
            String md5Hex = DigestUtils.md5Hex(orgInfo);
            digest.setTagValue(md5Hex);

            serializer.startTag(null,"header");
            agenterid.serializerLeaf(serializer);
            source.serializerLeaf(serializer);
            compress.serializerLeaf(serializer);

            messagerid.serializerLeaf(serializer);
            timestamp.serializerLeaf(serializer);
            digest.serializerLeaf(serializer);

            transactiontype.serializerLeaf(serializer);
            username.serializerLeaf(serializer);
            serializer.endTag(null,"header");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Leaf getAgenterid() {
        return agenterid;
    }

    public Leaf getUsername() {
        return username;
    }

    public Leaf getTransactiontype() {
        return transactiontype;
    }


    //处理服务器回复

    public Leaf getTimestamp() {
        return timestamp;
    }

    public Leaf getDigest() {
        return digest;
    }

}

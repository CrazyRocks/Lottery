package com.bob.lottery.element;

import com.bob.lottery.protocol.Element;
import com.bob.lottery.protocol.Leaf;

import org.xmlpull.v1.XmlSerializer;

/**
 * Created by Administrator on 2016/1/30.
 */
//获得当前销售期
public class CurrentIssueElement extends Element{
    private Leaf lotteryid=new Leaf("lotteryid");
    private Leaf issues=new Leaf("issues","1");

    //处理回复
    private String issue;
    private String lasttime;



    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }


    @Override
    public void serializerElement(XmlSerializer serializer) {
        try{
            serializer.startTag(null,"element");
            lotteryid.serializerLeaf(serializer);
            issues.serializerLeaf(serializer);
            serializer.endTag(null,"element");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getTransactionType() {
        return "12002";
    }

    public Leaf getLotteryid() {
        return lotteryid;
    }
}

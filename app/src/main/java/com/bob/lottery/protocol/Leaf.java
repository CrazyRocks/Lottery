package com.bob.lottery.protocol;


import org.xmlpull.v1.XmlSerializer;

/**
 * Created by Administrator on 2016/1/30.
 */

//简单叶子
public class Leaf {
    //1.包含的内容
    //2.序列化xml
    private String tagName;
    private String tagValue;

    public Leaf(String tagName) {
        super();
        this.tagName = tagName;
    }

    public Leaf(String tagName, String tagValue) {
        this.tagName = tagName;
        this.tagValue = tagValue;
    }


    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    //序列化叶子
    public void serializerLeaf(XmlSerializer serializer){
        try{
            serializer.startTag(null, tagName);
            if (tagValue==null){
                tagValue="";
            }
            serializer.text(tagValue);
            serializer.endTag(null,tagName);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

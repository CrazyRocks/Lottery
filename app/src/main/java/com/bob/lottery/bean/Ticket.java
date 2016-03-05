package com.bob.lottery.bean;

/**
 * Created by Administrator on 2016/3/5.
 */
//用户投注信息封装
public class Ticket {
    private String redNum;
    private String blueNum;
    private int num;

    public String getRedNum() {
        return redNum;
    }

    public void setRedNum(String redNum) {
        this.redNum = redNum;
    }

    public String getBlueNum() {
        return blueNum;
    }

    public void setBlueNum(String blueNum) {
        this.blueNum = blueNum;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

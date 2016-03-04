package com.bob.lottery.engine;

import com.bob.lottery.protocol.Message;

/**
 * Created by Administrator on 2016/3/4.
 */
//公共数据处理
public interface CommonInfoEngine  {
    //获取当前销售期：integer:彩种标识
    Message getCurrentIssueInfo(Integer integer);
}

package com.bob.lottery.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bob.lottery.R;
import com.bob.lottery.element.CurrentIssueElement;
import com.bob.lottery.engine.CommonInfoEngine;
import com.bob.lottery.factory.BeanFactory;
import com.bob.lottery.protocol.Element;
import com.bob.lottery.protocol.Message;
import com.bob.lottery.protocol.Oelement;
import com.bob.lottery.util.ConstantValue;
import com.bob.lottery.util.PromptManager;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016/3/4.
 */

//购彩大厅
public class Hall extends BaseUI {
    //1.加载Layout
    //2.初始化控件
    //3.设置监听
    //4.异步获取彩种当前销售期信息


    //中间容器
    private TextView ssqIssue;
    //双色球投注
    private ImageView ssqBet;

    public Hall(Context context) {
        super(context);
    }

    //初始化
    public void init() {
        showInMiddle = (LinearLayout) View.inflate(context, R.layout.il_hall, null);
        ssqIssue = (TextView) findViewById(R.id.ii_hall_ssq_summary);
        ssqBet = (ImageView) findViewById(R.id.ii_hall_ssq_bet);
        getCurrentIssueInfo();
    }

    @Override
    public void setListener() {
        ssqBet.setOnClickListener(this);
    }

    @Override
    public int getID() {
        return ConstantValue.VIEW_HALL;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    //获取当前销售期信息
    private void getCurrentIssueInfo() {
       // Integer... params---->ConstantValue.SSQ
        new MyHttpTask<Integer>(){
            //同run方法，在子线程运行
            @Override
            protected Message doInBackground(Integer... params) {
                CommonInfoEngine engine= BeanFactory.getImpl(CommonInfoEngine.class);
                return engine.getCurrentIssueInfo(params[0]);
            }

            @Override
            protected void onPostExecute(Message result) {
                //更新界面
                if (result!=null){
                    Oelement oelement = result.getBody().getOelement();
                    if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())){
                        changeMotice(result.getBody().getElements().get(0));
                    }else {
                        PromptManager.showToast(context,oelement.getErrormsg());
                    }
                }else {
                    //网络不通，权限，服务器。。。
                    //如何提示
                    PromptManager.showToast(context,"服务器忙，稍后重试...");
                }
                super.onPostExecute(result);
            }
        }.executeProxy(ConstantValue.SSQ);
    }

    //界面提示
    private void changeMotice(Element element) {
        CurrentIssueElement currentIssueElement= (CurrentIssueElement) element;
        String issue = currentIssueElement.getIssue();
        String lasttime = getLasttime(currentIssueElement.getLasttime());
        //第ISSUE期，TIME停销售
        String info=context.getResources().getString(R.string.is_hall_common_summary);
        info=StringUtils.replaceEach(info, new String[]{"ISSUE","TIME"}, new String[]{issue,lasttime});
        ssqIssue.setText(info);
    }


    /**
     * 将秒时间转换成日时分格式
     *
     * @param lasttime
     * @return
     */
    public String getLasttime(String lasttime) {
        StringBuffer result = new StringBuffer();
        if (StringUtils.isNumericSpace(lasttime)) {
            int time = Integer.parseInt(lasttime);
            int day = time / (24 * 60 * 60);
            result.append(day).append("天");
            if (day > 0) {
                time = time - day * 24 * 60 * 60;
            }
            int hour = time / 3600;
            result.append(hour).append("时");
            if (hour > 0) {
                time = time - hour * 60 * 60;
            }
            int minute = time / 60;
            result.append(minute).append("分");
        }
        return result.toString();
    }
}

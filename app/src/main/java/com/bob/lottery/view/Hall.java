package com.bob.lottery.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bob.lottery.R;
import com.bob.lottery.util.ConstantValue;

/**
 * Created by Administrator on 2016/3/4.
 */

//购彩大厅
public class Hall extends BaseUI {
    //1.加载Layout
    //2.初始化控件
    //3.设置监听

    //中间容器
    private TextView ssqIssue;
    //双色球投注
    private ImageView ssqBet;

    public Hall(Context context) {
        super(context);
    }

    //初始化
    public void init() {
        showInMiddle= (LinearLayout) View.inflate(context, R.layout.il_hall,null);
        ssqIssue= (TextView) findViewById(R.id.ii_hall_ssq_summary);
        ssqBet= (ImageView) findViewById(R.id.ii_hall_ssq_bet);
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
}

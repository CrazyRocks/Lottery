package com.bob.lottery.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.bob.lottery.util.ConstantValue;

/**
 * Created by Administrator on 2016/3/3.
 */
//第一界面
public class FirstUI extends BaseUI{

    public FirstUI(Context context) {
        super(context);

    }

    @Override
    public void init() {

    }

    @Override
    public void setListener() {

    }

    //获取需要在中间容器加载的控件
    public View getChild()
    {
        //简单界面：
        TextView textView = new TextView(context);

        LayoutParams layoutParams = textView.getLayoutParams();
        layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        textView.setLayoutParams(layoutParams);

        textView.setBackgroundColor(Color.BLUE);
        textView.setText("这是第一个界面");

        return textView;
    }

    @Override
    public int getID() {
        return ConstantValue.VIEW_FISRT;
    }
}

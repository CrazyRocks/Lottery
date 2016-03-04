package com.bob.lottery.view;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.bob.lottery.util.ConstantValue;


/**
 * 第二个简单的界面
 * @author Administrator
 *
 */
public class SecondUI extends BaseUI{
	
	public SecondUI(Context context) {
		super(context);
	}

	@Override
	public void init() {

	}

	@Override
	public void setListener() {

	}


	/**
	 * 获取需要在中间容器加载的控件
	 * @return
	 */
	public View getChild()
	{
		//简单界面：
		TextView textView = new TextView(context);

		LayoutParams layoutParams = textView.getLayoutParams();
		layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		textView.setLayoutParams(layoutParams);

		textView.setBackgroundColor(Color.RED);
		textView.setText("这是第二个界面");
		
		return textView;
	}

	@Override
	public int getID() {
		return ConstantValue.VIEW_SECOND;
	}
}

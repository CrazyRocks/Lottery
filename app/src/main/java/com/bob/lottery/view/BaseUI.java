package com.bob.lottery.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bob.lottery.R;

//所有界面的基类，界面通用化
public abstract class BaseUI implements View.OnClickListener{
	protected Context context;
	//显示到中间容器
	protected ViewGroup showInMiddle;

	public BaseUI(Context context) {
		this.context = context;
		init();
		setListener();
	}

	//初始化
	public abstract void init();
	//设置监听
	public abstract void setListener();
	
	//获取需要在中间容器加载的内容
	public  View getChild(){
		//关于第三参数：root  (ListView 自己内部调用了onMeasure()做了如下处理，所以正常处理)
		//root=null
		//showInMiddle.getLayoutParams()==null
		//root!=null
		//return root
		if (showInMiddle.getLayoutParams()==null){
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.MATCH_PARENT);
			showInMiddle.setLayoutParams(params);
		}
		return showInMiddle;
	}

	//获得界面的标识，容器比对依据
	public abstract int getID();

	@Override
	public void onClick(View v) {

	}

	public View findViewById(int id){
		return showInMiddle.findViewById(id);
	}
	
}

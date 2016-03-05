package com.bob.lottery.view;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bob.lottery.R;
import com.bob.lottery.net.NetUtil;
import com.bob.lottery.protocol.Message;
import com.bob.lottery.util.PromptManager;

//所有界面的基类，界面通用化
public abstract class BaseUI implements View.OnClickListener{
	protected Context context;
	//显示到中间容器
	protected ViewGroup showInMiddle;
	public Bundle bundle;

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

	//出去
	public  void onPause(){};
	//进入
	public  void onResume(){};

	//传数据
	public void setBundle(Bundle bundle) {
		this.bundle=bundle;
	}


	//访问网络的工具, //AsyncTask内部封装好了线程池，CORE_SIZE=5
	public abstract class MyHttpTask<Params> extends AsyncTask<Params,Void, Message>{

		//类似于Thread.start方法
		//由于final无法Override,省略网络判断
		public final AsyncTask<Params,Void, Message> executeProxy(Params... params){
			if (NetUtil.checkNet(context)) {
				return super.execute(params);
			} else {
				PromptManager.showNoNetWork(context);
			}
			return null;
		}
	}
	
}

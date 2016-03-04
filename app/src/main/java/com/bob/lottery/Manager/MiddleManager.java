package com.bob.lottery.Manager;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.bob.lottery.R;
import com.bob.lottery.util.ConstantValue;
import com.bob.lottery.view.BaseUI;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;

/**
 * Created by Administrator on 2016/3/4.
 */
//中间容器管理者
public class MiddleManager extends Observable{

    private static MiddleManager instance=new MiddleManager();
    public static MiddleManager getInstance(){
        return instance;
    }
    private RelativeLayout middleContainer;

    public void setMiddleContainer(RelativeLayout middleContainer){
        this.middleContainer=middleContainer;
    }

    public Context getContext(){
        return middleContainer.getContext();
    }

    //利用手机内存换应用的运行速度
    private Map<String,BaseUI> VIEWCAHE=new HashMap<>();
    private BaseUI currentUI;//当前展示
    private LinkedList<String> HISTORY=new LinkedList<>();//用户操作历史纪录


    //处理反复点击对象的重用
    public void changeUI(Class<? extends BaseUI> targetClazz){
        //判断当前正在展示的界面
        if (currentUI!=null && currentUI.getClass()==targetClazz)
        {
            return;
        }

        BaseUI targetUI=null;
        String key=targetClazz.getSimpleName();
        //一旦创建过，重用
        if (VIEWCAHE.containsKey(key)){
            targetUI=VIEWCAHE.get(key);
        }else {
            try {
                Constructor<? extends BaseUI> constructor = targetClazz.getConstructor(Context.class);
                targetUI=constructor.newInstance(getContext());
                VIEWCAHE.put(key,targetUI);
            } catch (Exception e) {
                throw new RuntimeException("构造问题");
            }
        }
        //切换核心
        middleContainer.removeAllViews();
        //FadeUtil.fadeOut(firstUIChild, 2000);
        View child = targetUI.getChild();
        middleContainer.addView(child);
        child.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.ia_view_change));
        // FadeUtil.fadeIn(child, 2000, 1000);
        currentUI=targetUI;

        //将当前显示的界面保存到栈顶
        HISTORY.addFirst(key);
        //当前容器切换成功，处理另外两个容器的变化
        changeTitleAndBottom();
    }

    //容器的联动处理
    private void changeTitleAndBottom() {
        //1.界面一对应未登录标题和通用导航
        //2.界面二对应标题和玩法导航

        //方案一：存在的问题，比对依据：名称，字节码
        //名称：在界面处理之初要将所有名称确定
        //字节码：将所有的界面都创建完成

        //方案二：更换比对依据:id
        //方案2.1：降低容器耦合度
        //中间容器变动的时候，中间容器“通知”其他容器，传递标识id进行容器内容的切换
        //1.广播：多个应用之间
        //2.接口监听
        //3.观察者模式：应用内部多监听

        //  被观察者{
        //  List<观察者> lists;
        //  boolean isChanged;
        //  setChange(){
        //  isChanged=true;
        //  }
        //  notify{
        //  for(观察者 item:lists){
        //  item.update();
        // }
        // }
        // }

        //1.被观察者对象====中间对象
        //2.观察者---标题和底部导航（左右菜单）
        //3.建立关系，观察者添加到容器
        //4.一旦中间容器变动，修改BOOLEAN,然后通知所有观察者，update（）
        setChanged();
        notifyObservers(currentUI.getID());
    }

    //返回处理
    public boolean goBack() {
        //异常
        if (HISTORY.size()>0){
            //误操作（不退出应用）
            if (HISTORY.size()==1){
                return false;
            }
            //移除栈顶元素
            HISTORY.removeFirst();
            //异常
            if (HISTORY.size()>0){
                //获取新的栈顶元素
                String key=HISTORY.getFirst();
                //拿到对象
                BaseUI targetUI=VIEWCAHE.get(key);
                //移除容器view
                middleContainer.removeAllViews();
                //重新添加，更新ui
                middleContainer.addView(targetUI.getChild());
                //设置当前展示的是谁
                currentUI=targetUI;
                return true;
            }

        }
        return false;
    }
}

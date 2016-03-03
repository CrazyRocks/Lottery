package com.bob.lottery.net;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.bob.lottery.util.GlobalParams;

/**
 * Created by Administrator on 2016/1/30.
 */
public class NetUtil {
    //检查用户的网络
    public static boolean checkNet(Context context){
        //判断WIFI连接
        boolean isWIFI=isWIFIConnection(context);
        //判断Mobile连接
        boolean isMOBILE=isMOBILEConnection(context);
        //若为Mobile连接，判断是那个APN选中了
        if(isMOBILE) {
            //APN选中的代理信息是否有内容，wap/net方式
            readAPN(context);
        }
        if (!isWIFI && !isMOBILE){
            return false;
        }
        return true;
    }

    private static void readAPN(Context context) {
        Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");//4.0模拟器屏蔽掉该权限

        // 操作联系人类似
        ContentResolver resolver = context.getContentResolver();
        // 判断是哪个APN被选中了
        Cursor cursor = resolver.query(PREFERRED_APN_URI, null, null, null, null);

        if(cursor!=null&&cursor.moveToFirst())
        {
            GlobalParams.PROXY=cursor.getString(cursor.getColumnIndex("proxy"));
            System.out.println(GlobalParams.PROXY);
            System.out.println(GlobalParams.PORT);
            GlobalParams.PORT=cursor.getInt(cursor.getColumnIndex("port"));
        }

    }

    private static boolean isMOBILEConnection(Context context) {
        ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo!=null){
            return  networkInfo.isConnected();
        }
        return false;
    }

    private static boolean isWIFIConnection(Context context) {
        ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo!=null){
            return  networkInfo.isConnected();
        }
        return false;
    }
}

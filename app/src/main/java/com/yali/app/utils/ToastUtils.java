package com.yali.app.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 该类
 *
 * @author jingyali
 * @version v1.0 2019-05-06 09:30
 */
public class ToastUtils {
    public static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}

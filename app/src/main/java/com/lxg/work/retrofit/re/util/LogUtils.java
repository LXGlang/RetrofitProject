package com.lxg.work.retrofit.re.util;

import android.text.TextUtils;
import android.util.Log;

import com.lxg.work.retrofit.BuildConfig;

import java.util.List;

/**
 * Created by Lxg on 2018/5/2 0002.
 * 日志信息类
 */
public class LogUtils {
    private static boolean DEBUG = BuildConfig.DEBUG;
    private static String TAG = "我是日志:   ";

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(new StringBuffer(TAG).append("---").append(getTag(getCallerStackTraceElement())).toString(), msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Log.e(new StringBuffer(TAG).append("---").append(getTag(getCallerStackTraceElement())).toString(), msg);
        }
    }

    public static void JSON(Object msg) {
        if (DEBUG) {
            Log.e(new StringBuffer(TAG).append("---").append(getTag(getCallerStackTraceElement())).toString(), "\n");
            JsonLogUtils.json(msg);
        }
    }

    public static void List(List msg) {
        if (DEBUG) {
            for (int i = 0; i < msg.size(); i++) {
                Log.e(new StringBuffer(TAG).append("---").append(getTag(getCallerStackTraceElement())).toString(), "\n");
                LogUtils.JSON(msg.get(i));
            }
        }
    }

    private static String getTag(StackTraceElement element) {
        String tag = "%s.%s(Line:%d)"; // 占位符
        String callerClazzName = element.getClassName(); // 获取到类名

        callerClazzName = callerClazzName.substring(callerClazzName
                .lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, element.getMethodName(),
                element.getLineNumber()); // 替换
        tag = TextUtils.isEmpty(TAG) ? tag : TAG + ":"
                + tag;
        return tag;
    }

    /**
     * 获取线程状态
     *
     * @return
     */
    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[5];
    }
}

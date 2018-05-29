package com.lxg.work.retrofit.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lxg on 2018/5/2 0002.
 */
public class JsonLogUtils {

    private static final int JSON_INDENT = 4;
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }

    public static void json(Object msg) {
        String[] contents = wrapperContent(5, msg);
        String tags = contents[0];
        String msgs = contents[1];
        String headString = contents[2];
        printJson(tags, msgs, headString);
    }

    private static void printJson(String tag, String msg, String headString) {

        String message;
        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(JSON_INDENT);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(JSON_INDENT);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        message = headString + LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        LogUtils.e("lines.length" + lines.length);
        if (lines.length > 99) {
            printLine(tag, true);
            LogUtils.i("打印日志行数" + lines.length + ",超过99行,改为直接打印:" + msg);
            printLine(tag, false);

        } else {
            printLine(tag, true);
            for (String line : lines) {
                Log.e(tag, "║ " + line);
            }
            printLine(tag, false);
        }
    }

    private static String[] wrapperContent(int stackTraceIndex, Object objects) {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        StackTraceElement targetElement = stackTrace[stackTraceIndex];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1];
        }

        if (className.contains("$")) {
            className = className.split("\\$")[0];
        }

        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();

        if (lineNumber < 0) {
            lineNumber = 0;
        }

        String tag = "";

        String msg = getObjectsString(objects);
        String headString = "[ (" + className + ":" + lineNumber + ")#" + methodName + " ] ";

        return new String[]{tag, msg, headString};
    }

    private static String getObjectsString(Object objects) {

        return objects == null ? "NULL" : objects.toString();
    }
}

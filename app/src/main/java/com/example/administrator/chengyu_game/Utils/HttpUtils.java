package com.example.administrator.chengyu_game.Utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.administrator.chengyu_game.CallBack.GetUserCallBack;
import com.example.administrator.chengyu_game.CallBack.HttpCallBack;
import com.example.administrator.chengyu_game.CallBack.UploadUserCallBack;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/9 2316.
 * author:zhuangbichen
 */

public class HttpUtils {

    public static final String IP_ADDRESS = "http://192.168.8.102:8001/";

    public static void getText(final String address, final HttpCallBack callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                StringBuffer stringBuffer = new StringBuffer();
                String result = "";
                try{
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String str;
                    while((str = bufferedReader.readLine()) != null){
                        stringBuffer.append(str);
                    }
//                    System.out.println(stringBuffer);
                    result = stringBuffer.toString().replace("\"", "\'");
                    if(callback != null){
                        callback.onTextSuccess(result);
                    }
                    inputStream.close();
                    bufferedReader.close();
                }catch (Exception e){
                    if(callback != null){
                        callback.onTextFail();
                    }
                    e.printStackTrace();
                    System.out.println("出错啦：" + result);
                } finally {
                    if(connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();

    }

    public static void getImg(final String address, final HttpCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    System.out.println("下载图片");
                    InputStream inputStream = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if (callBack != null) {
                        if (bitmap != null) {
                            callBack.onImgSuccess(bitmap);
                        }
                        else {
                            callBack.onImgFail();
                        }
                    }
                    inputStream.close();
                }
                catch (Exception e) {
                    if (callBack != null)
                        callBack.onImgFail();
                    e.printStackTrace();
                }
                finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void loginAndRegister(final String address, final Map<String, String> params, final HttpCallBack callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    System.out.println("url：" + address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    byte[] bytes = getBytes(params);
                    connection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(bytes);
                    System.out.println("登录");
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String str;
                    StringBuffer stringBuffer = new StringBuffer();
                    while((str = bufferedReader.readLine()) != null){
                        stringBuffer.append(str);
                    }
                    System.out.println(stringBuffer);
                    if(callback != null){
                        callback.onTextSuccess(stringBuffer.toString());
                    }
                    inputStream.close();
                    outputStream.close();
                    bufferedReader.close();
                }
                catch (Exception e) {
                    if (callback != null)
                        callback.onTextFail();
                    e.printStackTrace();
                }
                finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void uploadUser(final String address, final Map<String, String> params, final HttpCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    System.out.println("url：" + address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    byte[] bytes = getBytes(params);
                    connection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(bytes);
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String str;
                    StringBuffer stringBuffer = new StringBuffer();
                    while((str = bufferedReader.readLine()) != null){
                        stringBuffer.append(str);
                    }
                    if(callBack != null){
                        callBack.onTextSuccess(stringBuffer.toString());
                    }
                    inputStream.close();
                    outputStream.close();
                    bufferedReader.close();
                }
                catch (Exception e) {
                    if (callBack != null)
                        callBack.onTextFail();
                    e.printStackTrace();
                }
                finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void getUser(final String address, final HttpCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String str;
                    StringBuffer stringBuffer = new StringBuffer();
                    while((str = bufferedReader.readLine()) != null){
                        stringBuffer.append(str);
                    }
                    if(callBack != null){
                        callBack.onTextSuccess(stringBuffer.toString());
                    }
                    inputStream.close();
                    bufferedReader.close();
                }
                catch (Exception e) {
                    if (callBack != null)
                        callBack.onTextFail();
                    e.printStackTrace();
                }
                finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private static byte[] getBytes(Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString().getBytes();
    }

}

package com.example.administrator.chengyu_game.Model;

import android.graphics.Bitmap;

import com.example.administrator.chengyu_game.CallBack.HttpCallBack;
import com.example.administrator.chengyu_game.CallBack.LoginCallBack;
import com.example.administrator.chengyu_game.CallBack.RegisterCallBack;
import com.example.administrator.chengyu_game.Utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/19 0019.
 */

public class LoginModelImple {

    private String mLoginUrl = HttpUtils.IP_ADDRESS + "chengyu/user/login";
    private String mRegisterUrl = HttpUtils.IP_ADDRESS + "chengyu/user/signup";


    public void login(String username, String password, final LoginCallBack loginCallBack) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("username", username);
        tempMap.put("password", password);
        System.out.println("username：" + username);
        // TODO 修改登录URL
        String url = mLoginUrl;
//        System.out.println("mLoginUrl:" + url);
        HttpUtils.loginAndRegister(url, tempMap, new HttpCallBack() {
            @Override
            public void onTextSuccess(String response) {
                if (response.equals("0")) {
                    System.out.println("0000000");
                    loginCallBack.loginFailed("账号密码错误，请重新输入！");
                }
                else {
                    // TODO 输入登录成功代码
                    loginCallBack.loginSuccess(response);
                }
            }

            @Override
            public void onImgSuccess(Bitmap bitmap) {

            }

            @Override
            public void onTextFail() {
                loginCallBack.loginFailed("网络出错");
            }

            @Override
            public void onImgFail() {

            }
        });
    }

    public void register(String username, String password, final RegisterCallBack registerCallBack) {
        // TODO 修改注册URL
        String url = mRegisterUrl;
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("username", username);
        tempMap.put("password", password);
        HttpUtils.loginAndRegister(url, tempMap, new HttpCallBack() {
            @Override
            public void onTextSuccess(String response) {
                if (response.equals("0")) {
                    registerCallBack.registerFailed("注册失败");
                }
                else {
                    registerCallBack.registerSuccess();
                }
            }

            @Override
            public void onImgSuccess(Bitmap bitmap) {

            }

            @Override
            public void onTextFail() {
                registerCallBack.registerFailed("网络出错");
            }

            @Override
            public void onImgFail() {

            }
        });
    }


}

package com.example.administrator.chengyu_game.Model;

import android.graphics.Bitmap;

import com.example.administrator.chengyu_game.Bean.User;
import com.example.administrator.chengyu_game.CallBack.GetUserCallBack;
import com.example.administrator.chengyu_game.CallBack.HttpCallBack;
import com.example.administrator.chengyu_game.Utils.HttpUtils;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2019/5/16 0016.
 */

public class LevelModelImple {

    private User user;

    private String getUserUrl = HttpUtils.IP_ADDRESS + "chengyu/user/get_information?";


    public LevelModelImple() {

    }

    public void getSelectLevel(String username, final GetUserCallBack userCallBack) {
        HttpUtils.getUser(getUserUrl+"username="+username, new HttpCallBack() {
            @Override
            public void onTextSuccess(String response) {
                if (response.equals("0")) {
                    userCallBack.onFailed();
                }
                else {
                    userCallBack.onSuccess(response);
                }
            }

            @Override
            public void onImgSuccess(Bitmap bitmap) {

            }

            @Override
            public void onTextFail() {
                userCallBack.onFailed();
            }

            @Override
            public void onImgFail() {

            }
        });
    }

}

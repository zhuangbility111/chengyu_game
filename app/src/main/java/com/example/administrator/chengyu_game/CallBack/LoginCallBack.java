package com.example.administrator.chengyu_game.CallBack;

/**
 * Created by Administrator on 2019/4/18 0018.
 */

public interface LoginCallBack {
    void loginSuccess(String userJson);

    void loginFailed(String reason);
}

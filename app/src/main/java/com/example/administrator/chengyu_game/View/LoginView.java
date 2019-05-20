package com.example.administrator.chengyu_game.View;

/**
 * Created by Administrator on 2019/4/13 0013.
 */

public interface LoginView {

    void loginSuccess(String userJson);

    void loginFailed(String reason);

    void registerSuccess();

    void registerFailed(String reason);

}

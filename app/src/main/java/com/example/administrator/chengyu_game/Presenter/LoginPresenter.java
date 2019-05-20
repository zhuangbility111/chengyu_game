package com.example.administrator.chengyu_game.Presenter;

import android.os.Handler;
import android.util.Log;

import com.example.administrator.chengyu_game.CallBack.LoginCallBack;
import com.example.administrator.chengyu_game.CallBack.RegisterCallBack;
import com.example.administrator.chengyu_game.Model.ChengyuModel;
import com.example.administrator.chengyu_game.Model.ChengyuModelImple;
import com.example.administrator.chengyu_game.Model.LoginModelImple;
import com.example.administrator.chengyu_game.View.LoginActivity;
import com.example.administrator.chengyu_game.View.LoginView;
import com.example.administrator.chengyu_game.View.MainActivity;



/**
 * Created by Administrator on 2019/4/18 0018.
 */

public class LoginPresenter {

    public LoginModelImple loginModelImple;
    public LoginView loginView;


    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
        loginModelImple = new LoginModelImple();
    }

    public void login(String username, String password) {
        loginModelImple.login(username, password, new LoginCallBack() {
            @Override
            public void loginSuccess(final String userJson) {
                LoginActivity.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginView.loginSuccess(userJson);
                    }
                });

            }

            @Override
            public void loginFailed(final String reason) {
                LoginActivity.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(reason);
                        loginView.loginFailed(reason);
                    }
                });
            }
        });
    }

    public void register(String username, String password) {
        loginModelImple.register(username, password, new RegisterCallBack() {
            @Override
            public void registerSuccess() {
                LoginActivity.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginView.registerSuccess();
                    }
                });
            }

            @Override
            public void registerFailed(final String reason) {
                LoginActivity.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginView.registerFailed(reason);
                    }
                });
            }
        });
    }
}

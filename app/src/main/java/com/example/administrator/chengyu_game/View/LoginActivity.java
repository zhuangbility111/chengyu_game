package com.example.administrator.chengyu_game.View;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.chengyu_game.Bean.User;
import com.example.administrator.chengyu_game.Presenter.LoginPresenter;
import com.example.administrator.chengyu_game.R;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView{

    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtLogin;
    private Button mBtRegister;
    private LoginPresenter loginPresenter;
    public static Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this);
        initView();
    }

    private void initView() {
        mEtUsername = (EditText) findViewById(R.id.mEt_username);
        mEtPassword = (EditText) findViewById(R.id.mEt_password);
        mBtLogin = (Button) findViewById(R.id.mBt_login);
        mBtLogin.setOnClickListener(this);
        mBtRegister = (Button)findViewById(R.id.mBt_register);
        mBtRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mBt_login:{
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                if (username.equals("")) {
                    Toast.makeText(this, "账号为空", Toast.LENGTH_LONG).show();
                }
                else if (password.equals("")) {
                    Toast.makeText(this, "密码为空", Toast.LENGTH_LONG).show();
                }
                else {
                    loginPresenter.login(username, password);
                }

            }break;
            case R.id.mBt_register:{
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                if (username.equals("")) {
                    Toast.makeText(this, "账号为空", Toast.LENGTH_LONG).show();
                }
                else if (password.equals("")) {
                    Toast.makeText(this, "密码为空", Toast.LENGTH_LONG).show();
                }
                else {
                    loginPresenter.register(username, password);
                }
            }break;
        }
    }

    @Override
    public void loginSuccess(String userJson) {
        Intent intent = new Intent(this, ShowLevelActivity.class);
        intent.putExtra("username", new Gson().fromJson(userJson, User.class).getUsername());
        startActivity(intent);
        this.finish();
    }

    @Override
    public void loginFailed(String reason) {
        System.out.println(reason);
        Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
    }

    @Override
    public void registerSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
        String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        loginPresenter.login(username, password);
    }

    @Override
    public void registerFailed(String reason) {
        Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
    }
}

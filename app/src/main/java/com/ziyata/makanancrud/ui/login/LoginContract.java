package com.ziyata.makanancrud.ui.login;

import android.content.Context;

import com.ziyata.makanancrud.model.login.login.LoginData;

public interface LoginContract {
    interface View{
        void showProgress();
        void hideProgress();
        void loginSuccess(String message, LoginData loginData);
        void loginFailure(String message);
        void usernameError(String message);
        void passwordError(String message);
        void isLogin();
    }
    interface Presenter{
     void doLogin(String username, String password);
     void saveDataUser(Context context, LoginData loginData);
     void checkLogin(Context context);
    }
}

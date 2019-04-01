package com.ziyata.makanancrud.ui.register;

import com.ziyata.makanancrud.model.login.login.LoginData;

public interface RegisterContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showError(String message);
        void showRegisterSucces(String message);
    }

    interface Presenter{
        void doRegisterUser(LoginData loginData);
    }
}

package com.ziyata.makanancrud.ui.profil;

import android.content.Context;

import com.ziyata.makanancrud.model.login.login.LoginData;

public interface ProfilContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showSuccessUpdate(String message);
        void showDataUser(LoginData loginData);
    }

    interface Presenter{
        void updateDataUser(Context context, LoginData loginData);
        void getDataUser(Context context);
        void logoutSession(Context context);
    }
}

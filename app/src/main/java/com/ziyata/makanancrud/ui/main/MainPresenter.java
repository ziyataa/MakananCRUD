package com.ziyata.makanancrud.ui.main;

import android.content.Context;

import com.ziyata.makanancrud.utils.SessionManager;

public class MainPresenter implements MainContract.Presenter {
    @Override
    public void logoutSession(Context context) {
        // Membuat object SessionManager untuk dapat digunakan
        SessionManager msessionManager = new SessionManager(context);
        msessionManager.logout();
    }
}

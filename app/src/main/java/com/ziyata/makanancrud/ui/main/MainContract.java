package com.ziyata.makanancrud.ui.main;

import android.content.Context;

public interface MainContract {

    interface View{

    }

    interface Presenter{
        void logoutSession(Context context);
    }
}

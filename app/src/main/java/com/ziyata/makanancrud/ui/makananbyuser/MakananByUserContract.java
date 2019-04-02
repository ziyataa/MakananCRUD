package com.ziyata.makanancrud.ui.makananbyuser;

import com.ziyata.makanancrud.model.login.makanan.MakananData;

import java.util.List;

public interface MakananByUserContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showFoodByUser(List<MakananData> foodByUserList);
        void showFailureMessage(String msg);
    }
    interface Presenter{
        void getListFoodByUser(String idUser);
    }
}

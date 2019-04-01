package com.ziyata.makanancrud.ui.detailmakanan;

import com.ziyata.makanancrud.model.login.makanan.MakananData;

public interface DetailMakananContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showDetailMakanan(MakananData makananData);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getDetailMakanan(String idMakanan);
    }
}

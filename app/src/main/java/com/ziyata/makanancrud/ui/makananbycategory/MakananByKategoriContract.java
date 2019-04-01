package com.ziyata.makanancrud.ui.makananbycategory;

import com.ziyata.makanancrud.model.login.makanan.MakananData;

import java.util.List;

public interface MakananByKategoriContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showFoodByKategori(List<MakananData> foodNewsList);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getListFoodByKategori(String idKategori);
    }
}

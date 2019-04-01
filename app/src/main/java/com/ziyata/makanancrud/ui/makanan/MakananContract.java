package com.ziyata.makanancrud.ui.makanan;

import com.ziyata.makanancrud.model.login.makanan.MakananData;

import java.util.List;

public interface MakananContract {
    interface View{
        void showProgrees();
        void hideProgress();
        void showFoodNewsList(List<MakananData> foodNewsList);
        void showFoodPopulerList(List<MakananData> foodPopulerList);
        void showFoodKategoriList(List<MakananData> foodKategoriList);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getListFoodNews();
        void getListFoodPopuler();
        void getListFoodKategori();
    }
}

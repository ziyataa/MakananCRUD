package com.ziyata.makanancrud.ui.detailmakananbyuser;

import android.content.Context;
import android.net.Uri;

import com.ziyata.makanancrud.model.login.makanan.MakananData;

import java.util.List;

public interface DetailMakananByUserContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showDetailMakanan(MakananData makananData);
        void showMessage(String msg);
        void successDelete();
        void showSpinnerCategory(List<MakananData> categoryDataList);
    }

    interface Presenter{
        void getCategory();
        void getDetailMakanan(String idMakanan);
        void updateDataMakanan(Context context, Uri filePath,
                               String namaMakanan,
                               String descMakanan,
                               String idCategory,
                               String namaFotoMakanan);
        void deleteMakanan(String idMakanan, String namaFotoMakanan);
    }
}

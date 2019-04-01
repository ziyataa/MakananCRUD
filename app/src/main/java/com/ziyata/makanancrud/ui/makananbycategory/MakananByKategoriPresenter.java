package com.ziyata.makanancrud.ui.makananbycategory;

import com.ziyata.makanancrud.data.remote.ApiClient;
import com.ziyata.makanancrud.data.remote.ApiInterface;
import com.ziyata.makanancrud.model.login.makanan.MakananResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananByKategoriPresenter implements MakananByKategoriContract.Presenter {

    private final MakananByKategoriContract.View view;
    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

    public MakananByKategoriPresenter(MakananByKategoriContract.View view) {
        this.view = view;
    }

    @Override
    public void getListFoodByKategori(String idKategori) {
        view.showProgress();
        if (idKategori.isEmpty()){
            view.showFailureMessage("ID Kategori tidak ada");
            return;
        }

        Call<MakananResponse> call = mApiInterface.getMakananByCategory(Integer.valueOf(idKategori));
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showFoodByKategori(response.body().getMakananDataList());
                    }else {
                        view.showFailureMessage(response.body().getMessage());
                    }
                }else {
                    view.showFailureMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.showFailureMessage(t.getMessage());
            }
        });
    }
}

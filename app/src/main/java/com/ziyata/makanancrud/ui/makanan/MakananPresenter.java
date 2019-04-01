package com.ziyata.makanancrud.ui.makanan;

import com.ziyata.makanancrud.data.remote.ApiClient;
import com.ziyata.makanancrud.data.remote.ApiInterface;
import com.ziyata.makanancrud.model.login.makanan.MakananResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananPresenter implements MakananContract.Presenter {
    // TODO 1 Menyiapkan variable yang dibutuhkan
    private final MakananContract.View view;
    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

    public MakananPresenter(MakananContract.View view) {
        this.view = view;
    }

    @Override
    public void getListFoodNews() {
        view.showProgrees();
        Call<MakananResponse> call = mApiInterface.getMakananBaru();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body().getMakananDataList() != null){
                    view.showFoodNewsList(response.body().getMakananDataList());
                }else {
                    view.showFailureMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailureMessage(t.getMessage());
            }
        });
    }

    @Override
    public void getListFoodPopuler() {
        view.showProgrees();
        Call<MakananResponse> call = mApiInterface.getMakananPopuler();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body().getMakananDataList() != null){
                    view.showFoodPopulerList(response.body().getMakananDataList());
                }else {
                    view.showFailureMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailureMessage(t.getMessage());
            }
        });
    }

    @Override
    public void getListFoodKategori() {
        view.showProgrees();
        Call<MakananResponse> call = mApiInterface.getKategoriMakanan();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body().getMakananDataList() != null){
                    view.showFoodKategoriList(response.body().getMakananDataList());
                }else {
                    view.showFailureMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
               //rc view.hideProgress();
                view.showFailureMessage(t.getMessage());
            }
        });
    }
}

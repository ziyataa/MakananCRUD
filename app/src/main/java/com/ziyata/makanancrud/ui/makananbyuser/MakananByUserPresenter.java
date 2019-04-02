package com.ziyata.makanancrud.ui.makananbyuser;

import com.ziyata.makanancrud.data.remote.ApiClient;
import com.ziyata.makanancrud.data.remote.ApiInterface;
import com.ziyata.makanancrud.model.login.makanan.MakananResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananByUserPresenter implements MakananByUserContract.Presenter {

    private final MakananByUserContract.View view;
    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

    public MakananByUserPresenter(MakananByUserContract.View view) {
        this.view = view;
    }

    @Override
    public void getListFoodByUser(String idUser) {
        view.showProgress();

        if (idUser.isEmpty()){
            view.showFailureMessage("ID USER tidak ada");
            return;
        }

        Call<MakananResponse> call = mApiInterface.getMakananByUser(Integer.valueOf(idUser));
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showFoodByUser(response.body().getMakananDataList());
                    }else {
                        view.showFailureMessage(response.body().getMessage());
                    }
                }else {
                    view.showFailureMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.showFailureMessage(t.getMessage());
            }
        });
    }
}


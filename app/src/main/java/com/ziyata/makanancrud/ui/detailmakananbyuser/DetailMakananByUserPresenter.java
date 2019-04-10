package com.ziyata.makanancrud.ui.detailmakananbyuser;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.ziyata.makanancrud.data.remote.ApiClient;
import com.ziyata.makanancrud.data.remote.ApiInterface;
import com.ziyata.makanancrud.model.login.detailmakanan.DetailMakananResponse;
import com.ziyata.makanancrud.model.login.makanan.MakananResponse;
import com.ziyata.makanancrud.utils.Constant;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMakananByUserPresenter implements DetailMakananByUserContract.Presenter {

    private final DetailMakananByUserContract.View view;
    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);
    private File imageFile;

    public DetailMakananByUserPresenter(DetailMakananByUserContract.View view) {
        this.view = view;
    }

    @Override
    public void getCategory() {
        view.showProgress();

        Call<MakananResponse> call = mApiInterface.getKategoriMakanan();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showSpinnerCategory(response.body().getMakananDataList());
                    }else {
                        view.showMessage(response.body().getMessage());
                    }
                }else {
                    view.showMessage("Data Kosong");
                }

            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showMessage(t.getMessage());

            }
        });
    }

    @Override
    public void getDetailMakanan(String idMakanan) {
        view.showProgress();
        if (idMakanan.isEmpty()){
            view.showMessage("ID Makanan tidak ada");
            view.hideProgress();
            return;
        }

        Call<DetailMakananResponse> call = mApiInterface.getDetailMakanan(Integer.valueOf(idMakanan));
        call.enqueue(new Callback<DetailMakananResponse>() {
            @Override
            public void onResponse(Call<DetailMakananResponse> call, Response<DetailMakananResponse> response) {
                view.hideProgress();
                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showDetailMakanan(response.body().getMakananData());
                    }else{
                        view.showMessage(response.body().getMessage());
                    }
                }else {
                    view.showMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<DetailMakananResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void updateDataMakanan(Context context, Uri filePath, String namaMakanan, String descMakanan, String idCategory, String namaFotoMakanan, String idMakanan) {
        view.showProgress();

        // Mengecek foto, namaMakanan, dan descMakanan apakah sudah terisi
        if (namaMakanan.isEmpty()){
            view.showMessage("Nama makanan kosong");
            view.hideProgress();
            return;
        }

        if (descMakanan.isEmpty()){
            view.showMessage("Desc makanan kosong");
            view.hideProgress();
            return;
        }

        if (filePath == null){
            // Mengambil alamat file image
            File myFile = new File(filePath.getPath());
            Uri selectedImage = getImageContentUri(context, myFile, filePath);
            String partImage = getPath(context, selectedImage);
            imageFile = new File(partImage);
        }

        // Mengambil id user dari shared pref
        SharedPreferences pref = context.getSharedPreferences(Constant.pref_name,0);
        String idUser = pref.getString(Constant.KEY_USER_ID,"");

        // Megambil tanggal sekarang dengan format yyyy-MM-dd HH:mm:ss
        String sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // Memasukkan data yang diperlukan ke dalam request body dengan tipe data untuk dikirim ke API
        RequestBody requestBody =  RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part mPartImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);

        // Membungkus nama makanan
        RequestBody mNamaMakanan = RequestBody.create(MediaType.parse("multipart/form-data"), namaMakanan);
        RequestBody mdescMakanan = RequestBody.create(MediaType.parse("multipart/form-data"), descMakanan);
        RequestBody mNamaFotoMakanan = RequestBody.create(MediaType.parse("multipart/form-data"), namaFotoMakanan);
        RequestBody datetime = RequestBody.create(MediaType.parse("multipart/form-data"), sdf);

        Call<MakananResponse> call = mApiInterface.updateMakanan(
                Integer.valueOf(idMakanan),
                Integer.valueOf(idCategory),
                mNamaMakanan,
                mdescMakanan,
                mNamaFotoMakanan,
                datetime,
                mPartImage);
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();

                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showMessage(response.body().getMessage());
                        view.successUpdate();
                    }else {
                        view.showMessage(response.body().getMessage());
                    }
                }else {
                    view.showMessage("Data kurang atau endpoint bermasalah");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void deleteMakanan(String idMakanan, String namaFotoMakanan) {
        view.showProgress();

        if (idMakanan.isEmpty()){
            view.showMessage("ID Makanan tidak ada");
            return;
        }
        if (namaFotoMakanan.isEmpty()){
            view.showMessage("Nama foto makanan tidak ada");
            return;
        }

        Call<MakananResponse> call = mApiInterface.deleteMakanan(Integer.valueOf(idMakanan),namaFotoMakanan);
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showMessage(response.body().getMessage());
                        view.successDelete();
                    }else {
                        view.showMessage(response.body().getMessage());
                    }
                }else {
                    view.showMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showMessage(t.getMessage());
            }
        });
    }

    private String getPath(Context context, Uri filepath) {
        Cursor cursor = context.getContentResolver().query(filepath, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ",
                new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private Uri getImageContentUri(Context context, File imageFile, Uri filePath) {
        String fileAbsolutePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{fileAbsolutePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Apabila file gambar sudah pernah diapakai namun ada kondisi lain yang belum diketahui
            // Apabila file gambar sudah pernah dipakai pengambilan bukan di galery

            Log.i("Isi Selected if", "Masuk cursor ada");
            return filePath;

        } else {
            Log.i("Isi Selected else", "cursor tidak ada");
            if (imageFile.exists()) {
                // Apabila file gambar baru belum pernah di pakai
                Log.i("Isi Selected else", "imagefile exists");
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, fileAbsolutePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                // Apabila file gambar sudah pernah dipakai
                // Apabila file gambar sudah pernah dipakai di galery
                Log.i("Isi Selected else", "imagefile tidak exists");
                return filePath;
            }
        }
    }
}

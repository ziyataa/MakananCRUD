package com.ziyata.makanancrud.ui.uploadmakanan;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.ziyata.makanancrud.data.remote.ApiClient;
import com.ziyata.makanancrud.data.remote.ApiInterface;
import com.ziyata.makanancrud.model.login.makanan.MakananResponse;
import com.ziyata.makanancrud.model.login.uploadmakanan.UploadMakananResponse;
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

public class UploadMakananPresenter implements UploadMakananContract.Presenter {

    // TODO 1 Menyiapkan variable yang dibutuhkan
    private final UploadMakananContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public UploadMakananPresenter(UploadMakananContract.View view) {
        this.view = view;
    }

    @Override
    public void getCategory() {
        view.showProgress();
        Call<MakananResponse> call = apiInterface.getKategoriMakanan();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
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
                Log.i("cek failure", "onFailure" + t.getMessage());
            }
        });
    }

    @Override
    public void uploadMakanan(Context context, Uri filePath, String namaMakanan, String descMakanan, String idCategory) {
        view.showProgress();

        if (namaMakanan.isEmpty()){
            view.showMessage("Nama makanan tidak boleh kosong");
            view.hideProgress();
            return;
        }

        if (descMakanan.isEmpty()){
            view.showMessage("Desc makanan tidak boleh kosong");
            view.hideProgress();
            return;
        }

        if (filePath == null){
            view.showMessage("Silahkan pilih gambar");
            view.hideProgress();
            return;
        }

        // Mengambil alamat file image
        Log.i("isi filepath", "uploadMakanan:"+String.valueOf(filePath));
        File myFile = new File(filePath.getPath());
        Log.i("isi 2", "myfile:"+String.valueOf(myFile));

        Uri selectedImage = getImageContentUri(context, myFile, filePath);
        Log.i("isi 3", "selected" +String.valueOf(selectedImage));
        File imageFile = null;
        if (selectedImage != null){

            String partImage = getPath(context, selectedImage);
            Log.i("isi 4", "partimg: "+partImage);

            imageFile = new File(partImage);
            Log.i("isi 5", "imgfile: "+String.valueOf(imageFile));
        }else {
            imageFile = myFile;
            Log.i("isi imageFile else", "imgFile: " + String.valueOf(imageFile));
        }

        // Mengambil id user dari SharedPreference
        SharedPreferences pref = context.getSharedPreferences(Constant.pref_name, 0);
        String idUser = pref.getString(Constant.KEY_USER_ID,"");
        Log.i("cek", "uploadMakanan: " + idUser +" "+ idCategory);


        // Mengambil tanggal sekarang dengan default yyyy-MM-dd HH:mm:ss
        String sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // Memasukkan data yang diperlukan ke dalam request body dengan tipe form-data untuk dikirim ke API
        final RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-data"),imageFile);
        MultipartBody.Part mPartImage = MultipartBody.Part.createFormData("image", imageFile.getName(),reqBody);

        RequestBody mNamaMakanan = RequestBody.create(MediaType.parse("multipart/form-data"), namaMakanan);
        RequestBody mDescMakanan = RequestBody.create(MediaType.parse("multipart/form-data"), descMakanan);
        RequestBody dateTime = RequestBody.create(MediaType.parse("multipart/form-data"), sdf);

        int idUserInt = Integer.valueOf(idUser);
        int idCategoryInt = Integer.valueOf(idCategory);

        // Mengirim data ke API
        Call<UploadMakananResponse> call = apiInterface.uploadMakanan(idUserInt,idCategoryInt,mNamaMakanan,mDescMakanan,dateTime,mPartImage);
        call.enqueue(new Callback<UploadMakananResponse>() {
            @Override
            public void onResponse(Call<UploadMakananResponse> call, Response<UploadMakananResponse> response) {
                view.hideProgress();
                if (response.body().getResult() == 1){
                    view.showMessage(response.body().getMessage());
                    view.successUpload();
                }else {
                    view.showMessage(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<UploadMakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showMessage(t.getMessage());
                Log.d("Gagal", "ON FAILURE : " + t.getMessage());
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


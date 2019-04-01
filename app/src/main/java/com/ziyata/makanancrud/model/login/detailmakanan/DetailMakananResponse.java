package com.ziyata.makanancrud.model.login.detailmakanan;

import com.google.gson.annotations.SerializedName;
import com.ziyata.makanancrud.model.login.makanan.MakananData;

public class DetailMakananResponse {
    @SerializedName("result")
    private int result;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private MakananData makananData;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MakananData getMakananData() {
        return makananData;
    }

    public void setMakananData(MakananData makananData) {
        this.makananData = makananData;
    }
}

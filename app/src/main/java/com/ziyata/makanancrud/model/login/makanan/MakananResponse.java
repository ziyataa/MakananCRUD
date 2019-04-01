package com.ziyata.makanancrud.model.login.makanan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MakananResponse {
    @SerializedName("result")
    private int result;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<MakananData> makananDataList;

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

    public List<MakananData> getMakananDataList() {
        return makananDataList;
    }

    public void setMakananDataList(List<MakananData> makananDataList) {
        this.makananDataList = makananDataList;
    }
}
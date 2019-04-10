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

    @SerializedName("url")
    private String url;

    @SerializedName("name")
    private String naem;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNaem() {
        return naem;
    }

    public void setNaem(String naem) {
        this.naem = naem;
    }

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

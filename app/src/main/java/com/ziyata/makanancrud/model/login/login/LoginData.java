package com.ziyata.makanancrud.model.login.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LoginData implements Parcelable{

    @SerializedName("id_user")
    private String id_user;

    @SerializedName("nama_user")
    private String namaUser;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("jenkel")
    private String jenkel;

    @SerializedName("no_telp")
    private String noTelp;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("level")
    private String level;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJenkel() {
        return jenkel;
    }

    public void setJenkel(String jenkel) {
        this.jenkel = jenkel;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public static Creator<LoginData> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id_user);
        dest.writeString(this.namaUser);
        dest.writeString(this.alamat);
        dest.writeString(this.jenkel);
        dest.writeString(this.noTelp);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.level);
    }

    public LoginData() {
    }

    protected LoginData(Parcel in) {
        this.id_user = in.readString();
        this.namaUser = in.readString();
        this.alamat = in.readString();
        this.jenkel = in.readString();
        this.noTelp = in.readString();
        this.username = in.readString();
        this.password = in.readString();
        this.level = in.readString();
    }

    public static final Creator<LoginData> CREATOR = new Creator<LoginData>() {
        @Override
        public LoginData createFromParcel(Parcel source) {
            return new LoginData(source);
        }

        @Override
        public LoginData[] newArray(int size) {
            return new LoginData[size];
        }
    };
}

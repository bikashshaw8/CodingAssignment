package com.poc.assignment.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class AlbumDataModel {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("id")
    @Expose
    private String albumId;
    @SerializedName("title")
    @Expose
    private String title;

    public AlbumDataModel(String userId, String albumId,String title) {
        this.userId = userId;
        this.albumId = albumId;
        this.title = title;
    }
    public AlbumDataModel(){

    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

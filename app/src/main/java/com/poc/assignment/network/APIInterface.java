package com.poc.assignment.network;


import com.poc.assignment.model.AlbumDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("albums/")
    Call<List<AlbumDataModel>> getAlbumList();
}
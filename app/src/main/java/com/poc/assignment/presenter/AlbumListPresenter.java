package com.poc.assignment.presenter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.poc.assignment.interfaces.BasePresenter;
import com.poc.assignment.interfaces.IAlbumListView;
import com.poc.assignment.interfaces.IBasicUI;
import com.poc.assignment.model.AlbumDataModel;
import com.poc.assignment.network.RetrofitUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumListPresenter extends BasePresenter<IBasicUI.View> {
    Activity activity;
    IAlbumListView view;

    public AlbumListPresenter(IAlbumListView v) {
        this.view = v;
    }

    @Override
    public void onAttach(IBasicUI.View v) {
        super.onAttach(v);
        fetchAlbumList();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        view = null;
    }

    public void fetchAlbumList() {
        getView().showProgressLoader(true);
        Callback<List<AlbumDataModel>> responseCallback = new Callback<List<AlbumDataModel>>() {
            @Override
            public void onResponse(Call<List<AlbumDataModel>> call, Response<List<AlbumDataModel>> response) {
                try {

                    if (null != response && response.isSuccessful()) {
                        List<AlbumDataModel> list = response.body();
                        if (list != null && list.size() > 0) {
                            view.showAlbumList(list);
                        } else {
                            view.showError();
                        }
                    } else {
                        view.showError();
                    }
                    getView().showProgressLoader(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<AlbumDataModel>> call, Throwable t) {
                getView().showProgressLoader(false);
                view.showError();
            }
        };
        RetrofitUtils.getInstance().getService().getAlbumList().enqueue(responseCallback);

    }
}

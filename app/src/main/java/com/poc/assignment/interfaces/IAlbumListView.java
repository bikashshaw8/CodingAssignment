package com.poc.assignment.interfaces;

import com.poc.assignment.model.AlbumDataModel;

import java.util.ArrayList;
import java.util.List;

public interface IAlbumListView extends IBasicUI.View {

//    interface View extends IBasicUI.View {
        public void showAlbumList(List<AlbumDataModel> list);
//    }
}

package com.poc.assignment.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.poc.assignment.adapter.AlbumListAdapter;
import com.poc.assignment.database.DBManager;
import com.poc.assignment.interfaces.IAlbumListView;
import com.poc.assignment.interfaces.IBasicUI;
import com.poc.assignment.model.AlbumDataModel;
import com.poc.assignment.presenter.AlbumListPresenter;
import com.poc.assignment.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IAlbumListView {

    private Button btnRefresh;
    private RecyclerView rvAlbums;
    private TextView tvErrorMsg;
    private AlbumListPresenter presenter;
    private AlbumListAdapter albumAdapter;
//    private ProgressBar pbLoader;
    private ProgressDialog mProgressDialog;
    private ArrayList<AlbumDataModel> albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListView();
        presenter = new AlbumListPresenter(this);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.fetchAlbumList();
            }
        });
        presenter.onAttach(this);
    }

    private void initView() {
        rvAlbums = findViewById(R.id.rvAlbums);
        tvErrorMsg = findViewById(R.id.tvErrorMsg);
        btnRefresh = findViewById(R.id.btnRefresh);
//        pbLoader = findViewById(R.id.pbLoader);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please Wait...");
    }

    @Override
    public void showAlbumList(List<AlbumDataModel> list) {

//        btnRefresh.setVisibility(View.VISIBLE);
        if(list!=null && list.size()>0){
            albumList.clear();
            albumList.addAll(new ArrayList<AlbumDataModel>(list));
            DBManager.getInstance(this).open().delete();//Clear Old list
            DBManager.getInstance(this).insert(albumList);//Insert Updated List
            DBManager.getInstance(this).close();
        }
        setUpList();

    }

    private void initListView(){
        DividerItemDecoration divider = new DividerItemDecoration(rvAlbums.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.list_divider_shape));

        albumList = new ArrayList<AlbumDataModel>();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvAlbums.setLayoutManager(llm);
        rvAlbums.addItemDecoration(divider);

        albumAdapter = new AlbumListAdapter(this, albumList);
        rvAlbums.setAdapter(albumAdapter);
    }

    private void setUpList() {
        albumList.clear();
        albumList.addAll(DBManager.getInstance(this).open().getAlbumList());
        DBManager.getInstance(this).close();
        if (albumList == null || albumList.size()==0) {
            rvAlbums.setVisibility(View.GONE);
            tvErrorMsg.setVisibility(View.VISIBLE);
//            tvErrorMsg.setText("No Data found");
        } else {
//            tvErrorMsg.setText("");
            tvErrorMsg.setVisibility(View.GONE);
            rvAlbums.setVisibility(View.VISIBLE);

            Collections.sort(albumList, new Comparator<AlbumDataModel>(){
                public int compare(AlbumDataModel obj1, AlbumDataModel obj2) {
                    return obj1.getTitle().compareToIgnoreCase(obj2.getTitle());
                }
            });
            albumAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showProgressLoader(boolean isShow) {
        if(isShow){
            mProgressDialog.show();
        } else{
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showError() {
//        btnRefresh.setVisibility(View.VISIBLE);
        Toast.makeText(this,R.string.strErrorMsg,Toast.LENGTH_SHORT).show();
        setUpList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}

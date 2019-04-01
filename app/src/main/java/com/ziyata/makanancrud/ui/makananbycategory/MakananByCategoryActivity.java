package com.ziyata.makanancrud.ui.makananbycategory;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ziyata.makanancrud.R;
import com.ziyata.makanancrud.adapter.AdapterMakanan;
import com.ziyata.makanancrud.model.login.makanan.MakananData;
import com.ziyata.makanancrud.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MakananByCategoryActivity extends AppCompatActivity implements MakananByKategoriContract.View {

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.txt_info)
    TextView txtInfo;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.rv_makanan)
    RecyclerView rvMakanan;
    @BindView(R.id.sr_makanan)
    SwipeRefreshLayout srMakanan;

    private MakananByKategoriPresenter mMakananByKategoriPresenter = new MakananByKategoriPresenter(this);
    private String idCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makanan_by_category);
        ButterKnife.bind(this);

        // Mengambil idKategori dari kiriman halaman sebelumnya
        idCategory = getIntent().getStringExtra(Constant.KEY_EXTRA_ID_CATEGORY);

        // Merequest makanan by id category
        mMakananByKategoriPresenter.getListFoodByKategori(idCategory);

        // Mensetting swiperefresh menghilangkan loading dan memanggil ulang data makanan by category
        srMakanan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srMakanan.setRefreshing(false);
                mMakananByKategoriPresenter.getListFoodByKategori(idCategory);
            }
        });
    }

    @Override
    public void showProgress() {
        rlProgress.setVisibility(View.VISIBLE);
        srMakanan.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        rlProgress.setVisibility(View.GONE);
        rvMakanan.setVisibility(View.VISIBLE);
        srMakanan.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFoodByKategori(List<MakananData> foodNewsList) {
        rvMakanan.setLayoutManager(new LinearLayoutManager(this));
        rvMakanan.setAdapter(new AdapterMakanan(AdapterMakanan.TYPE_4,this, foodNewsList));
    }

    @Override
    public void showFailureMessage(String msg) {
        srMakanan.setVisibility(View.VISIBLE);
        rvMakanan.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
        txtInfo.setText(msg);
    }
}

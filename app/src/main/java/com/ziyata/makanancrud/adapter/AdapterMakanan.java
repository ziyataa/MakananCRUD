package com.ziyata.makanancrud.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ziyata.makanancrud.R;
import com.ziyata.makanancrud.model.login.makanan.MakananData;
import com.ziyata.makanancrud.ui.detailmakanan.DetailMakananActivity;
import com.ziyata.makanancrud.ui.makananbycategory.MakananByCategoryActivity;
import com.ziyata.makanancrud.utils.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterMakanan extends RecyclerView.Adapter<AdapterMakanan.ViewHolder> {
    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    public static final int TYPE_3 = 3;
    public static final int TYPE_4 = 4;

    Integer viewType;
    private final Context context;
    private final List<MakananData> makananDataList;

    public AdapterMakanan(Integer viewType, Context context, List<MakananData> makananDataList) {
        this.viewType = viewType;
        this.context = context;
        this.makananDataList = makananDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (viewType) {
            case TYPE_1:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_news, null);
                return new FoodNewsViewHolder(view);
            case TYPE_2:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_populer, null);
                return new FoodPopulerViewHolder(view);
            case TYPE_3:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_kategori, null);
                return new FoodKategoriViewHolder(view);
            case TYPE_4:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_by_kategori, null);
                return new FoodNewsViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        // Mengambil data lalu memasukkan ke dalam model
        final MakananData makananData = makananDataList.get(i);

        int mViewType = viewType;
        switch (mViewType) {
            case TYPE_1:
                // Membuat holder untuk dapat mengakses widget
                FoodNewsViewHolder foodNewsViewHolder = (FoodNewsViewHolder) holder;

                // Request option untuk error dan placheholder gambar
                RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options).into(foodNewsViewHolder.imgMakanan);

                // Menampilkan title dan jumlah view
                foodNewsViewHolder.txtTitle.setText(makananData.getNamaMakanan());
                foodNewsViewHolder.txtView.setText(makananData.getView());

                // Menampilkan waktu upload
                foodNewsViewHolder.txtTime.setText(newDate(makananData.getInsertTime()));

                // Membuat onClick
                foodNewsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Berpindah halaman ke detail
                        context.startActivity(new Intent(context, DetailMakananActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));
                    }
                });
                break;
            case TYPE_2:
                // Membuat holder untuk dapat mengakses widget
                FoodPopulerViewHolder foodPopulerViewHolder = (FoodPopulerViewHolder) holder;

                // Request option untuk error dan placheholder gambar
                RequestOptions options2 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options2).into(foodPopulerViewHolder.imgMakanan);

                // Menampilkan title dan jumlah view
                foodPopulerViewHolder.txtTitle.setText(makananData.getNamaMakanan());
                foodPopulerViewHolder.txtView.setText(makananData.getView());

                // Menampilkan waktu upload
                foodPopulerViewHolder.txtTime.setText(newDate(makananData.getInsertTime()));


                // Membuat onClick
                foodPopulerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DetailMakananActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));
                    }
                });
                break;
            case TYPE_3:
                // Membuat holder untuk dapat mengakses widget
                FoodKategoriViewHolder foodKategoriViewHolder = (FoodKategoriViewHolder) holder;

                // Request option untuk error dan placheholder gambar
                RequestOptions options3 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options3).into(foodKategoriViewHolder.image);

                // Menampilkan title dan jumlah view
                foodKategoriViewHolder.txtNamaKategory.setText(makananData.getNamaKategori());

                // Membuat onClick
                foodKategoriViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("cek idkategori adapter", "onClick: " + makananData.getIdKategori());
                        context.startActivity(new Intent(context, MakananByCategoryActivity.class).putExtra(Constant.KEY_EXTRA_ID_CATEGORY, makananData.getIdKategori()));
                    }
                });
                break;
            case TYPE_4:
                // Membuat holder untuk dapat mengakses widget
                FoodNewsViewHolder foodNewsViewHolder2 = (FoodNewsViewHolder) holder;

                // Request option untuk error dan placheholder gambar
                RequestOptions options4 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context)
                        .load(makananData.getUrlMakanan())
                        .apply(options4)
                        .into(foodNewsViewHolder2.imgMakanan);

                // Menampilkan title dan jumlah view
                foodNewsViewHolder2.txtTitle.setText(makananData.getNamaMakanan());
                foodNewsViewHolder2.txtView.setText(makananData.getView());

                // Menampilkan waktu upload
                foodNewsViewHolder2.txtTime.setText(newDate(makananData.getInsertTime()));

                // Membuat onClick
                foodNewsViewHolder2.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Berpindah halaman ke detail
                        context.startActivity(new Intent(context, DetailMakananActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));
                    }
                });
                break;
        }
    }

    private String newDate(String insertTime) {
        // Membuat variable penampung tanggal
        Date date = null;
        // Membuat penampung date dengan format yang baru
        String newDate = insertTime;
        // Membuat format sesuai dengan tangal yang sudah dimiliki
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        // Megubah tanggal yang dimiliki menjadi tipe date
        try {
            date = sdf.parse(insertTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Kita cek format date yang kita miliki sesuai dengan yang kita inginkan
        if (date != null) {
            // Mengubah date yang dimiliki menjadi format date yang baru
            newDate = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(date);
        }
        return newDate;
    }

    @Override
    public int getItemCount() {
        return makananDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class FoodNewsViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;

        public FoodNewsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class FoodPopulerViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;
        @BindView(R.id.txt_time)
        TextView txtTime;

        public FoodPopulerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class FoodKategoriViewHolder extends ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.txt_nama_kategory)
        TextView txtNamaKategory;

        public FoodKategoriViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

package com.ziyata.makanancrud.ui.profil;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ziyata.makanancrud.R;
import com.ziyata.makanancrud.model.login.login.LoginData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment implements ProfilContract.View {


    @BindView(R.id.picture)
    CircleImageView picture;
    @BindView(R.id.fabChoosePic)
    FloatingActionButton fabChoosePic;
    @BindView(R.id.layoutPicture)
    RelativeLayout layoutPicture;
    @BindView(R.id.edt_nama)
    EditText edtNama;
    @BindView(R.id.edt_alamat)
    EditText edtAlamat;
    @BindView(R.id.edt_notelp)
    EditText edtNotelp;
    @BindView(R.id.spin_gender)
    Spinner spinGender;
    @BindView(R.id.layoutProfil)
    CardView layoutProfil;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.layoutJenkel)
    CardView layoutJenkel;
    Unbinder unbinder;

    // TODO 1 Menyiapkan variable yang dibutuhkan
    private ProfilPresenter mProfilPresenter = new ProfilPresenter(this);

    private String idUser, nama, alamat, noTelp;
    private int gender;
    private Menu action;

    private String mGender;
    private static final int GENDER_MALE = 1;
    private static final int GENDER_FEMALE = 2;
    private ProgressDialog progressDialog;


    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        unbinder = ButterKnife.bind(this, view);

        // Mensetting spinner
        setupSpinner();

        // Mengambil data yang dikerjakan oleh presenter
        mProfilPresenter.getDataUser(getContext());

        // Mengambil option menu di fragment
        setHasOptionsMenu(true);



        return view;
    }

    private void setupSpinner() {
        // Membuat adapter spinner
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.array_gender_options, android.R.layout.simple_spinner_item);
        // Menampilkan spinner 1 line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Memasukkan adapter spinner ke dalam widget spinner kita
        spinGender.setAdapter(genderSpinnerAdapter);

        // Listener spinner
        spinGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Mengambil posisi item yang dipilih
                String selection = (String) adapterView.getItemAtPosition(i);
                // Mencek posisi apakah ada isinya
                if (!TextUtils.isEmpty(selection)) {
                    // Mencek apakah 1 atau 2 yang dipilih user
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = "L";
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = "P";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Saving . . .");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showSuccessUpdate(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        // Mengambil data ulang
        mProfilPresenter.getDataUser(getContext());
    }

    @Override
    public void showDataUser(LoginData loginData) {
// Mengubah widget agar tidak bisa diedit
        readMode();

        // Memasukkan data yang sudah di ambil oleh presenter
        idUser = loginData.getId_user();
        nama = loginData.getNamaUser();
        alamat = loginData.getAlamat();
        noTelp = loginData.getNoTelp();
        if (loginData.getJenkel().equals("L")) {
            gender = 1;
        } else {
            gender = 2;
        }

        if (!TextUtils.isEmpty(idUser)) {
            // Menset nama title action bar
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profil" +  nama);

            // Menampilkan data ke layar
            edtNama.setText(nama);
            edtAlamat.setText(alamat);
            edtNotelp.setText(noTelp);
            // Mencek gender dan memilih sesuai gender untuk ditampilkan pada spinner
            switch (gender) {
                case GENDER_MALE:
                    Log.i("cek male", String.valueOf(gender));
                    spinGender.setSelection(0);
                    break;
                case GENDER_FEMALE:
                    spinGender.setSelection(1);
                    break;
            }
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profil");
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
        // Melakukan perintah logout ke presenter
        mProfilPresenter.logoutSession(getContext());
        // Menutup mainactivity
        getActivity().finish();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_editor, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:

                editMode();
                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;
            case R.id.menu_save:
                // Mencek idUser apakah ada isinya
                if (!TextUtils.isEmpty(idUser)) {
                    // Mencek apakah semua field masih kosong
                    if (TextUtils.isEmpty(edtNama.getText().toString()) ||
                            TextUtils.isEmpty(edtAlamat.getText().toString()) ||
                            TextUtils.isEmpty(edtNotelp.getText().toString())) {
                        // Menampilkan alert dialog untuk memberitahu user tidak boleh ada field yg kosong
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                        alertDialog.setMessage("Please complete the field!");
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();

                    } else {
                        // Apabila user sudah mengisi semua field
                        LoginData loginData = new LoginData();
                        // Mengisi inputan user ke model logindata
                        loginData.setId_user(idUser);
                        loginData.setNamaUser(edtNama.getText().toString());
                        loginData.setAlamat(edtAlamat.getText().toString());
                        loginData.setNoTelp(edtNotelp.getText().toString());
                        loginData.setJenkel(mGender);

                        // Mengirim data ke presenter untuk dimasukkan ke dalam database
                        mProfilPresenter.updateDataUser(getContext(), loginData);

                        readMode();
                        action.findItem(R.id.menu_edit).setVisible(true);
                        action.findItem(R.id.menu_save).setVisible(false);
                    }
                } else {
                    readMode();
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("RestrictedApi")
    private void editMode() {
        edtNama.setFocusableInTouchMode(true);
        edtAlamat.setFocusableInTouchMode(true);
        edtNotelp.setFocusableInTouchMode(true);


        spinGender.setEnabled(true);
        fabChoosePic.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("RestrictedApi")
    private void readMode() {

        edtNama.setFocusableInTouchMode(false);
        edtNotelp.setFocusableInTouchMode(false);
        edtAlamat.setFocusableInTouchMode(false);
        edtNama.setFocusable(false);
        edtAlamat.setFocusable(false);
        edtNotelp.setFocusable(false);

        spinGender.setEnabled(false);
        fabChoosePic.setVisibility(View.INVISIBLE);
        }
    }

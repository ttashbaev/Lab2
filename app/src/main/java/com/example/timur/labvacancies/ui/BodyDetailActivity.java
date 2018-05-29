package com.example.timur.labvacancies.ui;

import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.timur.labvacancies.R;
import com.example.timur.labvacancies.StartApplication;
import com.example.timur.labvacancies.data.SQLiteHelper;
import com.example.timur.labvacancies.data.dto.VacancyModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BodyDetailActivity extends BaseActivity implements OnClickListener {
    private TextView mTvHeader, mTvBody, mTvSiteAddress, mTvData, mTvProfession, mTvSalary, mTvNumber;
    private CheckBox cbFav;
    private ArrayList<VacancyModel> newlist = new ArrayList<>();
    private Button mBtnCall;
    private LinearLayout mTbPrev, mTbNext;
    private int mPosition;
    private VacancyModel mVacanciesModel;
    private SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);

        initToolbar();
        initUI();

        sqLiteHelper = StartApplication.get(this).getHelper();
        Intent intent = getIntent();
        mPosition = intent.getIntExtra("position", 0);
        newlist = intent.getParcelableArrayListExtra("arrayList");

        mVacanciesModel = newlist.get(mPosition);
        //mVacanciesModel = mModelList.get(mPosition);

        sqLiteHelper.saveViewedVacancy(newlist.get(mPosition));
        disableButton();
        getDataFromListView(mVacanciesModel);
    }

    private void initUI() {
        mTvBody = findViewById(R.id.tvBody);
        mTvHeader = findViewById(R.id.tvHeader);
        mTvData = findViewById(R.id.tvData);
        mTvProfession = findViewById(R.id.tvProfession);
        mTvSalary = findViewById(R.id.tvSalary);
        mTvNumber = findViewById(R.id.tvNumber);
        mTvSiteAddress = findViewById(R.id.tvSiteAddress);
        mBtnCall = findViewById(R.id.btnCall);
        mTbPrev = findViewById(R.id.tbPrev);
        mTbNext = findViewById(R.id.tbNext);
        cbFav = findViewById(R.id.cbFav);

        mBtnCall.setOnClickListener(this);
        mTbNext.setOnClickListener(this);
        mTbPrev.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCall:
                if (ActivityCompat.checkSelfPermission(this, permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel: " + mTvNumber.getText().toString()));
                    startActivity(intent);
                }
                break;
            case R.id.tbPrev:
                if (mPosition == 0) {
                    mTbPrev.setVisibility(View.INVISIBLE);
                    return;
                }

                mPosition = mPosition - 1;
                disableButton();
                //mVacanciesModel = mModelList.get(mPosition);
                mVacanciesModel = newlist.get(mPosition);
                mTbNext.setVisibility(View.VISIBLE);
                getDataFromListView(mVacanciesModel);
                sqLiteHelper.saveViewedVacancy(newlist.get(mPosition));
                break;
            case R.id.tbNext:
                if (mPosition == (newlist.size() - 1)) {
                    mTbNext.setVisibility(View.INVISIBLE);
                    return;
                }
                mPosition = mPosition + 1;
                disableButton();
                //mVacanciesModel = mModelList.get(mPosition);
                mVacanciesModel = newlist.get(mPosition);
                mTbPrev.setVisibility(View.VISIBLE);
                getDataFromListView(mVacanciesModel);
                sqLiteHelper.saveViewedVacancy(newlist.get(mPosition));
                break;
        }


    }

    private void disableButton() {
        if (mPosition == 0) {
            mTbPrev.setVisibility(View.INVISIBLE);

        } else if (newlist.size() - 1 == mPosition) {
            mTbNext.setVisibility(View.INVISIBLE);
        }
    }

    private void getDataFromListView(VacancyModel model) {
        //model = mModelList.get(mPosition);
        //mVacanciesModel = (VacancyModel) getIntent().getSerializableExtra("position");
        if (model.getProfession().equals("Не определено")) {
            mTvProfession.setText(model.getHeader());
        } else {
            mTvProfession.setText(model.getProfession());
        }
        //mTvProfession.setText(mVacanciesModel.getProfession());
        mTvHeader.setText(model.getHeader());
        String input = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm, dd MMM yyyy", Locale.getDefault());
        String date = null;
        try {
            date = dateFormat.format(new Date(String.valueOf(new SimpleDateFormat(input, Locale.getDefault()).parse(model.getData()))));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mTvData.setText(date);
        if (!model.getSalary().equals("")) {
            mTvSalary.setText(model.getSalary());
        } else {
            mTvSalary.setText("Договорная");
        }

        String pid = sqLiteHelper.getFavItem(model.getPid());

        cbFav.setChecked(model.getPid().equals(pid));
        cbFav.setTag(model);
        cbFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view;

                if (cb.isChecked()) {
                    VacancyModel model = (VacancyModel) cb.getTag();
                    model.setChecked(cb.isChecked());
                    //Toast.makeText(context.getApplicationContext(), "test " + position, Toast.LENGTH_SHORT).show();
                    sqLiteHelper.saveFavouriteVacancy(newlist, mPosition);
                    //notifyDataSetChanged();
                    //ListViewAdapter.this.notifyAll();

                } else {
                    VacancyModel model = (VacancyModel) cb.getTag();
                    model.setChecked(cb.isChecked());
                    sqLiteHelper.deleteItem(model.getPid());
                    //(ListViewAdapter.this.notifyAll());
                    //notifyDataSetChanged();
                }

            }
        });
        //cbFav.setChecked(model.isChecked());
        //mTvSalary.setText(mVacanciesModel.getSalary());
        mTvSiteAddress.setText(model.getSiteAddress());
        String num = model.getTelephone();
        String[] num1 = num.split(" " + ";");
        //num = num.replace(" ", "");
        mTvNumber.setText(num1[0]);
        mTvBody.setText(model.getBody());
        //VacancyModel mVacanciesModel = getItem(position);
    }
}

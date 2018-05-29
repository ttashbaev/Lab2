package com.example.timur.labvacancies.ui;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.timur.labvacancies.R;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mDialog;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    protected void showProgressBar() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
            mDialog.setMessage(getString(R.string.please_wait));
            mDialog.show();
        }
    }

    protected void dismissProgressBar () {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mDialog = null;
    }

    protected void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tbSecond);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

    }
}

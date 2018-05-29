package com.example.timur.labvacancies.ui;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.timur.labvacancies.R;
import com.example.timur.labvacancies.data.ListViewAdapter;
import com.example.timur.labvacancies.data.SQLiteHelper;
import com.example.timur.labvacancies.data.dto.VacancyModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FavouriteVacanciesActivity extends BaseActivity implements
        OnItemClickListener,
        AdapterCallback {
    private SQLiteHelper sqLiteHelper;
    private ListView mListView;
    private List<VacancyModel> mList;
    private ListViewAdapter mAdapter;
    private int mPosition;
    private String TAG = "notify";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_vacancies);
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }

        initToolbar();

        sqLiteHelper = new SQLiteHelper(this);
        mListView = findViewById(R.id.lvFav);
        mList = new ArrayList<>();
        mList = sqLiteHelper.getFavouriteVacancies();
        if (mList.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Нет избранных вакансий", Toast.LENGTH_SHORT).show();
        } else {
            mAdapter = new ListViewAdapter(getApplicationContext(), mList, sqLiteHelper, this);
            mListView.setAdapter(mAdapter);
            mListView.setOnItemClickListener(this);
        }
        /* else {
            Toast.makeText(getApplicationContext(), "Нет избранных вакансий", Toast.LENGTH_SHORT).show();
        }*/


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            Log.d(TAG, "onResume: ");
        }
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), BodyDetailActivity.class);
        //intent.putExtra("position", arrayList.get(position));
        intent.putExtra("position", position);
        mPosition = position;
        intent.putExtra("size", mList.size());
        intent.putExtra("list", (Serializable) mList);
        startActivity(intent);
    }

    @Override
    public void deleteModel(VacancyModel model) {
        mAdapter.remove(model);
        mAdapter.notifyDataSetChanged();
    }
}

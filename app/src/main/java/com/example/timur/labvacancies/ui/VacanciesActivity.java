package com.example.timur.labvacancies.ui;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.timur.labvacancies.R;
import com.example.timur.labvacancies.StartApplication;
import com.example.timur.labvacancies.data.AuService;
import com.example.timur.labvacancies.data.ListViewAdapter;
import com.example.timur.labvacancies.data.SQLiteHelper;
import com.example.timur.labvacancies.data.dto.VacancyModel;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VacanciesActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        AdapterCallback {

    private TextView tvVersion;
    public ArrayList<VacancyModel> arrayList = new ArrayList<>();
    private ListView mListView;
    private ListViewAdapter mAdapter;
    private SwipyRefreshLayout mSwipeRefreshLayout;
    private SQLiteHelper sqLiteHelper;

    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbSecond);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorWhite));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sqLiteHelper = StartApplication.get(this).getHelper();
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mListView = findViewById(R.id.listView);
        //arrayList = new ArrayList<>();

        getData();


        mListView.setItemsCanFocus(true);
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), BodyDetailActivity.class);
                //intent.putExtra("position", arrayList.get(position));
                intent.putExtra("position", position);
                intent.putParcelableArrayListExtra("arrayList", arrayList);
                startActivity(intent);
                //mData.sendData(arrayList);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }


    private void getData(){
        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()){
                sqLiteHelper.clearVacancies();
                getAuVacancies();
                mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
            } else {
                arrayList = sqLiteHelper.getAllVacancies();
                mAdapter = new ListViewAdapter(getApplicationContext(), arrayList, sqLiteHelper, this);
                mListView.setAdapter(mAdapter);
                mSwipeRefreshLayout.setEnabled(false);
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                SearchFragment fragment = new SearchFragment();
                fragment.show(getSupportFragmentManager(), "search");
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.favorite:
                Intent intent = new Intent(getApplicationContext(), FavouriteVacanciesActivity.class);
                startActivity(intent);
                Log.d("favorite", "onOptionsItemSelected: ");
                break;
            case R.id.exit:
                DialogExitFragment dialogFragment = new DialogExitFragment();
                dialogFragment.show(getSupportFragmentManager(), "Dialog");
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getAuVacancies() {
        AuService service = StartApplication.get(getApplication()).getService();
        Log.d("TEST ", "getAuVacancies: ");
        showProgressBar();
        service.getVacancies("au", "get_all_vacancies", 20, mPage)
                .enqueue(new Callback<List<VacancyModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<VacancyModel>> call, @NonNull Response<List<VacancyModel>> response) {
                        Log.d("Success ", "onResponse: " + arrayList);
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (response.isSuccessful() && response.body() != null) {
                            arrayList.addAll(response.body());
                            updateData(arrayList);
                            /*mAdapter = new ListViewAdapter(getApplicationContext(), arrayList, this);
                            mListView.invalidateViews();
                            mListView.setAdapter(mAdapter);*/
                            dismissProgressBar();
                            sqLiteHelper.saveVacancies(arrayList);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<VacancyModel>> call, @NonNull Throwable t) {
                        Snackbar.make(findViewById(android.R.id.content), t.getMessage(), Snackbar.LENGTH_LONG).show();
                        Log.e("Error ", "onFailure: " + t.getMessage());
                    }
                });

    }



    private final SwipyRefreshLayout.OnRefreshListener onRefreshListener = new SwipyRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh(SwipyRefreshLayoutDirection direction) {
            sqLiteHelper.clearVacancies();
            mPage = mPage + 1;
            getAuVacancies();
        }
    };

    @Override
    public void deleteModel(VacancyModel model) {

    }

    private void updateData(List<VacancyModel> list) {

        /*ListViewAdapter adapter = new ListViewAdapter(this, arrayList, this);
        mListView.setAdapter(adapter);*/
        mAdapter = new ListViewAdapter(getApplicationContext(), arrayList, sqLiteHelper, this);
        mListView.invalidateViews();
        mListView.setAdapter(mAdapter);
        //Log.d("Success ", "updateData: " + model);
    }
}

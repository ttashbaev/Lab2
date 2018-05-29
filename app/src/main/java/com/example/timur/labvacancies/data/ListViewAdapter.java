package com.example.timur.labvacancies.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.timur.labvacancies.R;
import com.example.timur.labvacancies.data.dto.VacancyModel;
import com.example.timur.labvacancies.ui.AdapterCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Timur on 25.04.2018.
 */

public class ListViewAdapter extends ArrayAdapter<VacancyModel> {

    private List<VacancyModel> dataList;
    private Context context;
    private SQLiteHelper sqLiteHelper;
    private AdapterCallback mCallback;


    public ListViewAdapter(@NonNull Context context, List<VacancyModel> dataList, SQLiteHelper sqLiteHelper, AdapterCallback callback) {
        super(context, 0, dataList);
        this.dataList = dataList;
        this.context = context;
        this.mCallback = callback;
        this.sqLiteHelper = sqLiteHelper;
    }

    //@RequiresApi(api = VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.cbFav.setTag(getItem(position));
        holder.cbFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view;

                if (cb.isChecked()) {
                    VacancyModel model = (VacancyModel) cb.getTag();
                    model.setChecked(cb.isChecked());
                    //Toast.makeText(context.getApplicationContext(), "test " + position, Toast.LENGTH_SHORT).show();
                    sqLiteHelper.saveFavouriteVacancy(dataList, position);
                    //notifyDataSetChanged();
                    //ListViewAdapter.this.notifyAll();

                } else {
                    VacancyModel model = (VacancyModel) cb.getTag();
                    model.setChecked(cb.isChecked());
                    sqLiteHelper.deleteItem(model.getPid());
                    mCallback.deleteModel(model);
                    //(ListViewAdapter.this.notifyAll());
                    //notifyDataSetChanged();
                }

            }
        });
        /*Intent intent = new Intent(getContext(), Main3Activity.class);
        intent.putExtra("pos", position);*/
        final VacancyModel model = getItem(position);
        if (model.getProfession().equals("Не определено")) {
            holder.tvMainInfo.setText(model.getHeader());
        } else {
            holder.tvMainInfo.setText(model.getProfession());
        }

        String input = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm, dd MMM yyyy", Locale.getDefault());
        String date = null;
        try {
            date = dateFormat.format(new Date(String.valueOf(new SimpleDateFormat(input, Locale.getDefault()).parse(model.getData()))));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*String date = new SimpleDateFormat()
                .format(new Date(model.getData()));*/
        holder.tvData.setText(date);
        holder.tvInfo.setText(model.getHeader());
        if (!model.getSalary().equals("")) {
            holder.tvSalary.setText(model.getSalary());
        } else {
            holder.tvSalary.setText("Договорная");
        }
        String favItem = sqLiteHelper.getFavItem(model.getPid());


        if (model.getPid().equals(favItem)) {
            Log.d(favItem, "fav: " + model.getPid());
            holder.cbFav.setChecked(true);
            this.notifyDataSetChanged();
        } else {
            holder.cbFav.setChecked(model.isChecked());
            holder.cbFav.setTag(model);
            this.notifyDataSetChanged();
        }

        String pidItem = sqLiteHelper.getViewedItem(model.getPid());
        Log.d(pidItem, "getView: " + model.getPid());
        if (model.getPid().equals(pidItem)) {
            Log.d(pidItem, "getView: " + model.getPid());
            holder.viewed.setVisibility(View.VISIBLE);
        } else {
            holder.viewed.setVisibility(View.INVISIBLE);
            holder.viewed.setTag(model);
        }
        //}
        //sqLiteHelper.getFavouriteVacancies();


        this.notifyDataSetChanged();
        return convertView;
    }

    private class ViewHolder {
        private TextView tvMainInfo, tvInfo, tvData, tvSalary;
        private CheckBox cbFav;
        private LinearLayout viewed;

        public ViewHolder(View view) {
            tvMainInfo = view.findViewById(R.id.tvMainInfo);
            tvInfo = view.findViewById(R.id.tvInfo);
            tvData = view.findViewById(R.id.tvData);
            tvSalary = view.findViewById(R.id.tvSalary);
            cbFav = view.findViewById(R.id.cbFav);
            viewed = view.findViewById(R.id.linearViewed);
        }
    }
}

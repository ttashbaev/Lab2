package com.example.timur.labvacancies.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.timur.labvacancies.R;

import java.util.ArrayList;

/**
 * Created by Timur on 25.04.2018.
 */

public class ListVievAdapter extends ArrayAdapter<UserModel> {

    private ArrayList<UserModel> dataList;
    Context context;
    public ListVievAdapter(@NonNull Context context, ArrayList<UserModel> dataList) {
        super(context, 0, dataList);
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UserModel model = getItem(position);
        holder.tvMainInfo.setText(model.getProfession());
        holder.tvData.setText(model.getData());
        holder.tvInfo.setText(model.getProfile());
        holder.tvSalary.setText(model.getSalary());
        holder.cbFav.setChecked(model.isCheeked());
        //Picasso.get().load(model.getAvatar()).into(holder.ivAvatar);
        return convertView;
    }

    private class ViewHolder {
        private TextView tvMainInfo, tvInfo, tvData, tvSalary;
        private CheckBox cbFav;

        public ViewHolder (View view) {
            tvMainInfo = view.findViewById(R.id.tvMainInfo);
            tvInfo = view.findViewById(R.id.tvInfo);
            tvData = view.findViewById(R.id.tvData);
            tvSalary = view.findViewById(R.id.tvSalary);
            cbFav = view.findViewById(R.id.cbFav);
        }
    }
}

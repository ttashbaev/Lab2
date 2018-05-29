package com.example.timur.labvacancies.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.timur.labvacancies.R;

/**
 * Created by Timur on 28.02.2018.
 */

public class DialogExitFragment extends DialogFragment implements View.OnClickListener {
    public TextView tvQuestion;
    public Button btnNo, btnYes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        tvQuestion = view.findViewById(R.id.tvQuestion);
        btnNo = view.findViewById(R.id.btnNo);
        btnYes = view.findViewById(R.id.btnYes);
        btnNo.setOnClickListener(this);
        btnYes.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnYes:
                getActivity().finish();
                break;
            case R.id.btnNo:
                dismiss();
                break;
        }
    }

}

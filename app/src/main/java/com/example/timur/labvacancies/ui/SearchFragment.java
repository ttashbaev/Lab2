package com.example.timur.labvacancies.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.timur.labvacancies.R;

public class SearchFragment extends DialogFragment implements OnClickListener {

    private RadioButton mRbAll;
    private RadioButton mRbFullDay;
    private RadioButton mRbFlexTime;
    private RadioButton mRbRemote;
    private RadioButton mRbNight;
    private RadioButton mRbAllSalary;
    private RadioButton mRbFivePlus;
    private RadioButton mRbTenPlus;
    private RadioButton mRbThirtyPlus;

    private Button mBtnCancel;
    private Button mBtnOk;
    private RadioGroup mRgTermsLt, mRgTermsRt;
    private RadioGroup mRgSalaryLt, mRgSalaryRt;

    private int mPositionTerms = 0;
    private int mPositionSalary = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_test, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mRbAll = view.findViewById(R.id.rbAll);
        mRbFullDay = view.findViewById(R.id.rbFullDay);
        mRbFlexTime = view.findViewById(R.id.rbFlexTime);
        mRbRemote = view.findViewById(R.id.rbRemote);
        mRbNight = view.findViewById(R.id.rbNight);
        mRbAllSalary = view.findViewById(R.id.rbAllSalary);
        mRbFivePlus = view.findViewById(R.id.rbFivePlus);
        mRbTenPlus = view.findViewById(R.id.rbTenPlus);
        mRbThirtyPlus = view.findViewById(R.id.rbThirtyPlus);

        mBtnCancel = view.findViewById(R.id.btnCancel);
        mBtnOk = view.findViewById(R.id.btnOk);

        mRgSalaryLt = view.findViewById(R.id.rgSalaryLeft);
        mRgSalaryRt = view.findViewById(R.id.rgSalaryRight);
        mRgTermsLt = view.findViewById(R.id.rgTermsLeft);
        mRgTermsRt = view.findViewById(R.id.rgTermsRight);
       // mRgTest = view.findViewById(R.id.rgTerms1);

        mBtnOk.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);

        mRbAll.setChecked(true);
        mRbAllSalary.setChecked(true);

        setTagRB();

        mRgTermsLt.setOnCheckedChangeListener(listenerTermLeft);
        mRgTermsRt.setOnCheckedChangeListener(listenerTermRight);
        mRgSalaryLt.setOnCheckedChangeListener(listenerSalaryLeft);
        mRgSalaryRt.setOnCheckedChangeListener(listenerSalaryRight);

        return view;
    }

    private OnCheckedChangeListener listenerTermLeft = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                mRgTermsRt.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception(like Vladimir Volodin pointed out)
                mRgTermsRt.clearCheck(); // clear the second RadioGroup!
                mRgTermsRt.setOnCheckedChangeListener(listenerTermRight);
                //mRgTermsRt.getTag();
                //setTagRB();
                int radioButtonID = group.getCheckedRadioButtonId();
                View radioButton = group.findViewById(radioButtonID);
                int position = group.indexOfChild(radioButton);
                int pos = (int) radioButton.getTag();
                mPositionTerms = pos;
                Log.e("XXX2 " + mPositionTerms, "do the work " + position);
            }
        }
    };

    private OnCheckedChangeListener listenerTermRight = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                setTagRB();
                mRgTermsLt.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception(like Vladimir Volodin pointed out)
                mRgTermsLt.clearCheck(); // clear the second RadioGroup!
                mRgTermsLt.setOnCheckedChangeListener(listenerTermLeft); //reset the listener
                /*int chkId1 = mRgTermsLt.getCheckedRadioButtonId();
                int chkId2 = mRgTermsRt.getCheckedRadioButtonId();
                int realCheck = chkId1 == -1 ? chkId2 : chkId1;//reset the listener
                Log.e("XXX2", "do the work " + realCheck);*/

                /*int radioButtonID = group.getCheckedRadioButtonId();
                View radioButton = group.findViewById(radioButtonID);
                int position = group.indexOfChild(radioButton);*/
                int radioButtonID = group.getCheckedRadioButtonId();
                View radioButton = group.findViewById(radioButtonID);
                int position = group.indexOfChild(radioButton);
                int pos = (int) radioButton.getTag();
                mPositionTerms = pos;
                Log.e("XXX2 " + mPositionTerms, "do the work " + position);
            }
        }
    };

    private OnCheckedChangeListener listenerSalaryRight = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                setTagRB();
                mRgSalaryLt.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception(like Vladimir Volodin pointed out)
                mRgSalaryLt.clearCheck(); // clear the second RadioGroup!
                mRgSalaryLt.setOnCheckedChangeListener(listenerSalaryLeft); //reset the listener
                /*int chkId1 = mRgTermsLt.getCheckedRadioButtonId();
                int chkId2 = mRgTermsRt.getCheckedRadioButtonId();
                int realCheck = chkId1 == -1 ? chkId2 : chkId1;//reset the listener
                Log.e("XXX2", "do the work " + realCheck);*/

                /*int radioButtonID = group.getCheckedRadioButtonId();
                View radioButton = group.findViewById(radioButtonID);
                int position = group.indexOfChild(radioButton);*/
                int radioButtonID = group.getCheckedRadioButtonId();
                View radioButton = group.findViewById(radioButtonID);
                int position = group.indexOfChild(radioButton);
                int pos = (int) radioButton.getTag();
                mPositionSalary = pos;
                Log.e("XXX2 " + mPositionSalary, "do the work " + position);
            }
        }
    };

    private OnCheckedChangeListener listenerSalaryLeft = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                setTagRB();
                mRgSalaryRt.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception(like Vladimir Volodin pointed out)
                mRgSalaryRt.clearCheck(); // clear the second RadioGroup!
                mRgSalaryRt.setOnCheckedChangeListener(listenerSalaryRight); //reset the listener
                /*int chkId1 = mRgTermsLt.getCheckedRadioButtonId();
                int chkId2 = mRgTermsRt.getCheckedRadioButtonId();
                int realCheck = chkId1 == -1 ? chkId2 : chkId1;//reset the listener
                Log.e("XXX2", "do the work " + realCheck);*/

                /*int radioButtonID = group.getCheckedRadioButtonId();
                View radioButton = group.findViewById(radioButtonID);
                int position = group.indexOfChild(radioButton);*/
                int radioButtonID = group.getCheckedRadioButtonId();
                View radioButton = group.findViewById(radioButtonID);
                int position = group.indexOfChild(radioButton);
                int pos = (int) radioButton.getTag();
                mPositionSalary = pos;
                Log.e("XXX2 " + mPositionSalary, "do the work " + position);
            }
        }
    };

    private void setTagRB() {
        mRbAll.setTag(0);
        mRbFullDay.setTag(1);
        mRbFlexTime.setTag(2);
        mRbRemote.setTag(3);
        mRbNight.setTag(4);

        mRbAllSalary.setTag(0);
        mRbFivePlus.setTag(1);
        mRbTenPlus.setTag(2);
        mRbThirtyPlus.setTag(3);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCancel:
                mRgTermsRt.clearCheck();
                mRgTermsLt.clearCheck();
                mRgSalaryLt.clearCheck();
                mRgSalaryRt.clearCheck();
                mRbAll.setChecked(true);
                mRbAllSalary.setChecked(true);
                break;
            case R.id.btnOk:
                Intent intent = new Intent(getContext(), SearchResultVacanciesActivity.class);
                break;
        }
    }


}

package com.yurii_slipenkyi.quiz;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;


public class Fragment_cups extends Fragment {

    private TextView points;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cups, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        points = (TextView) Objects.requireNonNull(getView()).findViewById(R.id.points);
        UploadCups();
    }

    public void UploadCups() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        int cups = preferences.getInt(Questions_activity.KEY_CUPS, 0);
        points.setText(String.valueOf(cups));
    }
}
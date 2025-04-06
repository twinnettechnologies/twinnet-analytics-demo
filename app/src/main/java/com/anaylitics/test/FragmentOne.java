package com.anaylitics.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.twinnet_analytics.event.Analytics;

import org.json.JSONObject;

public class FragmentOne extends Fragment {


    public static FragmentOne getInstance() {
        return new FragmentOne();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_1, container, false);

        view.findViewById(R.id.addCustomEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject eventProperties = new JSONObject();
                    eventProperties.put("Button", "addCustomEvent");
                    Analytics.Companion.setCustomEvent("ButtonInfo", eventProperties);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        view.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject eventProperties = new JSONObject();
                    eventProperties.put("Button", "btn1");
                    Analytics.Companion.setCustomEvent("ButtonInfo", eventProperties);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        view.findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject eventProperties = new JSONObject();
                    eventProperties.put("Button", "btn2");
                    Analytics.Companion.setCustomEvent("ButtonInfo", eventProperties);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        return view;
    }
}

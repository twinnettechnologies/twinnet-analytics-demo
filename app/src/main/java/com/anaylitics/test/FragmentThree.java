package com.anaylitics.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anaylitics.test.ads.Admob_Banner_Mrc;
import com.anaylitics.test.ads.AdsLoad;

import org.json.JSONObject;

public class FragmentThree extends Fragment {


    public static FragmentThree getInstance() {
        return new FragmentThree();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_3, container, false);


        view.findViewById(R.id.showBanner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admob_Banner_Mrc.getInstance().loadNative(getContext(), new AdsLoad() {
                    @Override
                    public void onSuccess() {
                        Admob_Banner_Mrc.getInstance().showBanner(getContext(), view.findViewById(R.id.adsLayout));
                    }

                    @Override
                    public void onFailed() {

                    }
                });

            }
        });

        return view;
    }
}

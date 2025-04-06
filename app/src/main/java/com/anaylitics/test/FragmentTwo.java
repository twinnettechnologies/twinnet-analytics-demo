package com.anaylitics.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anaylitics.test.ads.Admob_Native;
import com.anaylitics.test.ads.AdsLoad;

import org.json.JSONObject;

public class FragmentTwo extends Fragment {


    public static FragmentTwo getInstance() {
        return new FragmentTwo();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_2, container, false);

        view.findViewById(R.id.showNative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admob_Native.getInstance().loadNative(getActivity(), new AdsLoad() {
                    @Override
                    public void onSuccess() {
                        Admob_Native.getInstance().showNative(getActivity(), view.findViewById(R.id.adsLayout));
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

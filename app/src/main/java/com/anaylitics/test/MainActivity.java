package com.anaylitics.test;

import androidx.appcompat.app.AppCompatActivity;
import com.anaylitics.test.ads.Admob_Interstitial;
import com.anaylitics.test.ads.AdsLoad;
import com.anaylitics.test.ads.setOnAdsClickListner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnLoadInter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admob_Interstitial.getInstance().loadInterstitial(MainActivity.this, new AdsLoad() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this, "Ads Loaded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed() {
                        Toast.makeText(MainActivity.this, "Ads Load Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.btnShowInter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admob_Interstitial.getInstance().showInterstitial(MainActivity.this, new setOnAdsClickListner() {
                    @Override
                    public void OnAdsClick() {
                        Toast.makeText(MainActivity.this, "Ads Closed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        findViewById(R.id.btncustom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CustomActivity.class));
            }
        });


    }


}
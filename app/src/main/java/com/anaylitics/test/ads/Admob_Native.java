package com.anaylitics.test.ads;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.anaylitics.test.AppUtil;
import com.anaylitics.test.Constant;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdValue;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.twinnet_analytics.myenum.AdsAction;
import com.twinnet_analytics.myenum.AdsCompany;
import com.twinnet_analytics.myenum.AdsType;
import com.twinnet_analytics.event.Analytics;

public class Admob_Native {

    private static String TAG = "Admob_Native";

    private NativeAd mnativeAd = null;
    private boolean isLoading = false;
    private boolean isShow = false;

    private String uniqId = "", adId = "";

    public void loadNative(Context mcontext, AdsLoad adsLoad) {
        context = mcontext;

        if (isLoading) return;

        isLoading = true;

        adId = "ca-app-pub-3940256099942544/2247696110";
        uniqId = String.valueOf(System.currentTimeMillis());
        Analytics.Companion.setAdsRequestEvent(AdsCompany.Admob, AdsType.Native, AdsAction.Request, mcontext, adId, uniqId);
        AppUtil.Log(TAG, "request -> mnativeAd");
        AdLoader adLoader = new AdLoader.Builder(context, adId)
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        AppUtil.Log(TAG, "onNativeAdLoaded-> mnativeAd");
                        Analytics.Companion.setAdsLoadEvent(AdsCompany.Admob, AdsType.Native, AdsAction.Loaded, mcontext, adId, uniqId);
                        mnativeAd = nativeAd;
                        isShow = true;
                        if(adsLoad != null) adsLoad.onSuccess();
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        AppUtil.Log(TAG, "onAdFailedToLoad ->  mnativeAd :" + loadAdError.getResponseInfo());
                        Analytics.Companion.setAdsFailedEvent(AdsCompany.Admob, AdsType.Native, AdsAction.Failed, mcontext, adId, uniqId, null, loadAdError == null ? Constant.not_found : String.valueOf(loadAdError.getCode()), loadAdError.getMessage() == null ? Constant.not_found : loadAdError.getMessage());
                        mnativeAd = null;
                        isLoading = false;
                        isShow = false;
                        if(adsLoad != null) adsLoad.onFailed();
                    }

                    @Override
                    public void onAdClicked() {
                        AppUtil.Log(TAG, "onAdClicked -> mnativeAd");
                        Analytics.Companion.setAdsClickEvevnt(AdsCompany.Admob, AdsType.Native, AdsAction.Click, context, adId, uniqId, null);
                        super.onAdClicked();
                    }

                    @Override
                    public void onAdImpression() {
                        AppUtil.Log(TAG, "onAdImpression -> mnativeAd ");
                        isLoading = false;
                        isShow = false;
                        Analytics.Companion.setAdsImpressionEvent(AdsCompany.Admob, AdsType.Native, AdsAction.Show, context, adId, uniqId, null);
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdLoaded() {
                        AppUtil.Log(TAG, "onAdLoaded -> mnativeAd");
                        super.onAdLoaded();
                    }

                    @Override
                    public void onAdOpened() {
                        AppUtil.Log(TAG, "onAdOpened -> mnativeAd");
                        super.onAdOpened();
                    }

                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder().setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT).build()).build();


        adLoader.loadAd(new AdRequest.Builder().build());
    }


    private Context context;

    private static Admob_Native admob_native = null;

    public static Admob_Native getInstance() {
        if (admob_native == null) {
            admob_native = new Admob_Native();
        }
        return admob_native;
    }


    public void showNative(Context mcontext, ViewGroup mviewGroup) {
        context = mcontext;

        if (mnativeAd != null && isShow) {
            AppUtil.Log(TAG, "showNative-> mnativeAd");

            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mnativeAd != null) {
                        Admob_Native_Inflate.inflate_native(mcontext, mviewGroup,mnativeAd);
                    }
                }
            });


            if (mnativeAd != null) {
                mnativeAd.setOnPaidEventListener(new OnPaidEventListener() {
                    @Override
                    public void onPaidEvent(@NonNull AdValue adValue) {
                        Analytics.Companion.setAdsPaidEvent(AdsCompany.Admob, AdsType.Native, mcontext, adId, uniqId, null, adValue.getCurrencyCode(), String.valueOf(adValue.getPrecisionType()), String.valueOf(adValue.getValueMicros()));
                    }
                });
            }
        }
    }



}

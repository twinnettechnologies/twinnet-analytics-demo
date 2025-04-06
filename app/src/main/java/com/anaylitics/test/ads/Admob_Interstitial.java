package com.anaylitics.test.ads;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.anaylitics.test.AppUtil;
import com.anaylitics.test.Constant;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.twinnet_analytics.event.Analytics;
import com.twinnet_analytics.myenum.AdsAction;
import com.twinnet_analytics.myenum.AdsCompany;
import com.twinnet_analytics.myenum.AdsType;


public class Admob_Interstitial {

    private static final String TAG = Admob_Interstitial.class.getSimpleName();

    private static Admob_Interstitial admob_interstitial = null;

    public static Admob_Interstitial getInstance() {
        if (admob_interstitial == null) {
            admob_interstitial = new Admob_Interstitial();
        }
        return admob_interstitial;
    }

    private boolean isShow = false;


    private InterstitialAd adManagerInterstitialAd = null;
    private boolean isLoading = false;
    private String uniqId = "";

    public void loadInterstitial(Context context, AdsLoad adsLoad) {

        if (adManagerInterstitialAd != null || isLoading) {
            return;
        }
        isLoading = true;


        String adId = "ca-app-pub-3940256099942544/1033173712";
        uniqId = String.valueOf(System.currentTimeMillis());
        Analytics.Companion.setAdsRequestEvent(AdsCompany.Admob, AdsType.Interstitial, AdsAction.Request, context, adId, uniqId);


        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, adId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                if (adsLoad != null) adsLoad.onSuccess();
                Analytics.Companion.setAdsLoadEvent(AdsCompany.Admob,  AdsType.Interstitial, AdsAction.Loaded, context, adId, uniqId);
                adManagerInterstitialAd = interstitialAd;
                isLoading = false;
                AppUtil.Log(TAG, "loadInterstitial-> onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                if (adsLoad != null) adsLoad.onFailed();
                AppUtil.Log(TAG, "loadInterstitial-> onAdFailedToLoad: " + loadAdError.getResponseInfo());
                Analytics.Companion.setAdsFailedEvent(AdsCompany.Admob, AdsType.Interstitial, AdsAction.Failed, context, adId, uniqId, null, loadAdError == null ? Constant.not_found : String.valueOf(loadAdError.getCode()), loadAdError.getMessage() == null ? Constant.not_found : loadAdError.getMessage());
                adManagerInterstitialAd = null;
                isLoading = false;
            }

        });
    }


    private static Context context;

    public void showInterstitial(Context mcontext, setOnAdsClickListner onAdsClickListner) {
        context = mcontext;
        if (adManagerInterstitialAd != null) {
            AppUtil.Log(TAG, "showInterstitial-> adManagerInterstitialAd");
            isShow = true;

            adManagerInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    AppUtil.Log(TAG, "showInterstitial-> adManagerInterstitialAd-> onAdDismissedFullScreenContent");
                    isShow = false;
                    adManagerInterstitialAd = null;
                    if (onAdsClickListner != null) onAdsClickListner.OnAdsClick();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                    AppUtil.Log(TAG, "showInterstitial-> adManagerInterstitialAd-> onAdFailedToShowFullScreenContent : " + adError.getMessage());
                    Analytics.Companion.setAdsFailedEvent(AdsCompany.Admob, AdsType.Interstitial, AdsAction.FailedOther, context,adManagerInterstitialAd.getAdUnitId() == null ? Constant.not_found : adManagerInterstitialAd.getAdUnitId(), uniqId, null, String.valueOf(adError.getCode()), adError.getMessage());
                    isShow = false;
                    if (onAdsClickListner != null) onAdsClickListner.OnAdsClick();
                    adManagerInterstitialAd = null;
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    AppUtil.Log(TAG, "showInterstitial-> adManagerInterstitialAd-> onAdShowedFullScreenContent");
                }

                @Override
                public void onAdClicked() {
                    AppUtil.Log(TAG, "showInterstitial-> adManagerInterstitialAd-> onAdClicked");
                    Analytics.Companion.setAdsClickEvevnt(AdsCompany.Admob, AdsType.Interstitial, AdsAction.Click, context, adManagerInterstitialAd.getAdUnitId(), uniqId, null);
                    super.onAdClicked();
                }

                @Override
                public void onAdImpression() {
                    AppUtil.Log(TAG, "showInterstitial-> adManagerInterstitialAd-> onAdImpression");
                    Analytics.Companion.setAdsImpressionEvent(AdsCompany.Admob, AdsType.Interstitial, AdsAction.Show, context, adManagerInterstitialAd.getAdUnitId(), uniqId, null);
                    super.onAdImpression();
                }

            });

            adManagerInterstitialAd.show((Activity) mcontext);
            adManagerInterstitialAd.setOnPaidEventListener(adValue ->
                    Analytics.Companion.setAdsPaidEvent(AdsCompany.Admob, AdsType.Interstitial, context, adManagerInterstitialAd.getAdUnitId(), uniqId, null, adValue.getCurrencyCode(), String.valueOf(adValue.getPrecisionType()), String.valueOf(adValue.getValueMicros()))
            );
        } else {
            AppUtil.Log(TAG, "showInterstitial->");
            if (onAdsClickListner != null) onAdsClickListner.OnAdsClick();
        }
    }

}

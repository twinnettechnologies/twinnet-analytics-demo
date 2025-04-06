package com.anaylitics.test.ads;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.anaylitics.test.AppUtil;
import com.anaylitics.test.Constant;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.twinnet_analytics.event.Analytics;
import com.twinnet_analytics.myenum.AdsAction;
import com.twinnet_analytics.myenum.AdsCompany;
import com.twinnet_analytics.myenum.AdsType;

public class Admob_Banner_Mrc {
    private static final String TAG = "Admob_Banner_Mrc";

    private AdView adManagerAdView = null;
    private boolean isLoaded = false;
    private Context context;
    private boolean isLoading = false;
    private String uniqId = "";
    private int subuniqId = 0;

    public void loadNative(Context mcontext, AdsLoad adsLoad) {
        context = mcontext;

        if (isLoading) return;

        isLoading = true;

        String adId = "ca-app-pub-3940256099942544/9214589741";
        uniqId = String.valueOf(System.currentTimeMillis());
        subuniqId = 0;
        Analytics.Companion.setAdsRequestEvent(AdsCompany.Admob, AdsType.Mrc, AdsAction.Request, mcontext, adId, uniqId);

        adManagerAdView = new AdView(context);
        adManagerAdView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        adManagerAdView.setAdUnitId(adId);
        AdRequest adRequestBanner = new AdRequest.Builder().build();
        adManagerAdView.loadAd(adRequestBanner);

        adManagerAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                AppUtil.Log(TAG, "onAdClicked->");
                Analytics.Companion.setAdsClickEvevnt(AdsCompany.Admob, AdsType.Mrc, AdsAction.Click, context, adManagerAdView.getAdUnitId(), uniqId, uniqId + "_" + subuniqId);
            }

            @Override
            public void onAdClosed() {}

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                AppUtil.Log(TAG, "onAdFailedToLoad-> adManagerAdView ->" + adError.getResponseInfo());
                Analytics.Companion.setAdsFailedEvent(AdsCompany.Admob, AdsType.Mrc, AdsAction.Failed, context, adId, uniqId, uniqId + "_" + subuniqId, adError == null ? Constant.not_found : String.valueOf(adError.getCode()), adError.getMessage() == null ? Constant.not_found : adError.getMessage());
                adManagerAdView = null;
                isLoading = false;
                if(adsLoad != null) adsLoad.onFailed();
            }

            boolean isIn = false;

            @Override
            public void onAdImpression() {
                AppUtil.Log(TAG, "onAdImpression->");
                if (!isIn) {
                    isIn = true;
                    isLoading = false;
                }
                subuniqId++;
                Analytics.Companion.setAdsImpressionEvent(AdsCompany.Admob, AdsType.Mrc, AdsAction.Show, context, adId, uniqId, uniqId + "_" + subuniqId);
            }

            @Override
            public void onAdLoaded() {
                isIn = false;
                AppUtil.Log(TAG, "onAdLoaded-> adManagerAdView");
                if (!isLoaded) {
                    if(adsLoad != null) adsLoad.onSuccess();
                    isLoaded = true;
                    Analytics.Companion.setAdsLoadEvent(AdsCompany.Admob, AdsType.Mrc, AdsAction.Loaded, context, adId, uniqId);
                }

            }

            @Override
            public void onAdOpened() {
                AppUtil.Log(TAG, "onAdOpened->");
            }
        });
    }


    public void showBanner(Context mcontext,ViewGroup viewGroup) {
        context = mcontext;
        if (adManagerAdView != null && !isLoaded) {
            AppUtil.Log(TAG, "showBanner -> adManagerAdView");
            ((LinearLayout) viewGroup).setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
            viewGroup.addView(adManagerAdView);
            adManagerAdView.setOnPaidEventListener(adValue -> Analytics.Companion.setAdsPaidEvent(AdsCompany.Admob, AdsType.Mrc, context, adManagerAdView.getAdUnitId(), uniqId, uniqId + "_" + subuniqId, adValue.getCurrencyCode(), String.valueOf(adValue.getPrecisionType()), String.valueOf(adValue.getValueMicros())));
        }
    }

    private static Admob_Banner_Mrc admob_banner_mrc = null;

    public static Admob_Banner_Mrc getInstance() {
        if (admob_banner_mrc == null) {
            admob_banner_mrc = new Admob_Banner_Mrc();
        }
        return admob_banner_mrc;
    }




}

package com.anaylitics.test.ads;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anaylitics.test.R;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

public class Admob_Native_Inflate {
    public static void inflate_native(Context mcontext, ViewGroup viewGroup, NativeAd mnativeAd) {
        viewGroup.removeAllViews();

        ((LinearLayout) viewGroup).setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view = (View) inflater.inflate(R.layout.ads_nativ_admob_big_card, null);
        viewGroup.addView(view);

        NativeAdView nativeAdView = view.findViewById(R.id.uadview);

        setAdsBind(nativeAdView, mnativeAd);

    }


    public static void setAdsBind(NativeAdView nativeAdView, NativeAd nativeAd) {

        if (nativeAd == null) {
            return;
        }

        MediaView ad_media = nativeAdView.findViewById(R.id.ad_media);

        if (nativeAd.getMediaContent() != null) {
            if (ad_media != null) {
                ad_media.setVisibility(View.VISIBLE);
                nativeAdView.setMediaView(ad_media);
                ad_media.setMediaContent(nativeAd.getMediaContent());
            }
        }else{
            ad_media.setVisibility(View.GONE);
        }

        TextView ad_headline = nativeAdView.findViewById(R.id.ad_headline);
        if (nativeAd.getHeadline() != null) {
            if (ad_headline != null) {
                nativeAdView.setHeadlineView(ad_headline);
                ((TextView) nativeAdView.getHeadlineView()).setText(nativeAd.getHeadline());
            }
        }

        TextView ad_body = nativeAdView.findViewById(R.id.ad_body);
        if (nativeAd.getBody() != null) {
            if (ad_body != null) {
                nativeAdView.setBodyView(ad_body);
                ((TextView) nativeAdView.getBodyView()).setText(nativeAd.getBody());
            }
        }

        ImageView ad_app_icon = nativeAdView.findViewById(R.id.ad_app_icon);
        if (nativeAd.getIcon() != null) {
            if (ad_app_icon != null) {
                if (nativeAd.getIcon().getDrawable() != null) {
                    nativeAdView.setIconView(ad_app_icon);
                    ((ImageView) nativeAdView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
                } else {
                    if (nativeAd.getStore() != null) {
                        ad_app_icon.setVisibility(View.INVISIBLE);
                    } else {
                        ad_app_icon.setVisibility(View.GONE);
                    }
                }
            } else {
                if (nativeAd.getStore() != null) {
                    ad_app_icon.setVisibility(View.INVISIBLE);
                } else {
                    ad_app_icon.setVisibility(View.GONE);
                }
            }
        } else {
            if (nativeAd.getStore() != null) {
                ad_app_icon.setVisibility(View.INVISIBLE);
            } else {
                ad_app_icon.setVisibility(View.GONE);
            }
        }

        TextView ad_button_name = nativeAdView.findViewById(R.id.ad_call_to_action);
        if (nativeAd.getCallToAction() != null) {
            if (ad_button_name != null) {
                ad_button_name.setVisibility(View.VISIBLE);
                nativeAdView.setCallToActionView(ad_button_name);
                ((TextView) nativeAdView.getCallToActionView()).setText(nativeAd.getCallToAction());
            }
        }else{
            ad_button_name.setVisibility(View.GONE);
        }


        nativeAdView.setNativeAd(nativeAd);
    }
}

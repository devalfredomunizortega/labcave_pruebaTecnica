package com.example.adslibrary;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class Utility{

    //BANNER AD
    public AdView bannerMethod(AdView adView){
        adView.loadAd(new AdRequest.Builder().build());
        return adView;
    }

    public void bannerLoadMethod(final AdView adView, final Button bannerButton){
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //valBanner = true;
                bannerButton.setVisibility(View.VISIBLE); //Display button when Ad loads
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d("TAG", "Failed to load.");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                adView.loadAd(new AdRequest.Builder().build());
            }
        });
    }



    //INTERSTITIAL AD
    public InterstitialAd intersitialMethod(InterstitialAd interstitialAd){
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/8691691433");
        interstitialAd.loadAd(new AdRequest.Builder().build());
        return interstitialAd;
    }

    public void intersitialLoadMethod(final InterstitialAd interstitialAd, final Button interstitialButton){
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //valInterstitial = true;
                interstitialButton.setVisibility(View.VISIBLE); //Display button when Ad loads
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d("TAG", "Failed to load.");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                //Reload Interstitial Ad
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }



    //REWARDED AD
    private RewardedAdLoadCallback rewardedAdLoadCallback;

    public RewardedAd rewardedMethod(RewardedAd rewardedAd, final Button rewardedButton){
        rewardedAdLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                rewardedButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), rewardedAdLoadCallback);
        return rewardedAd;
    }

    public RewardedAdLoadCallback getAdLoadCallback(){
        return rewardedAdLoadCallback;
    }

}


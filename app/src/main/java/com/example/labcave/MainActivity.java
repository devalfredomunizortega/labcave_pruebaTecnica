package com.example.labcave;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener{

    private Button bannerButton;
    private Button interstitialButton;
    private Button rewardedButton;

    private AdView adView;
    private InterstitialAd interstitialAd;
    private RewardedVideoAd rewardedVideoAd;
    private AdRequest adRequest;

    private Boolean valBanner = false;
    private Boolean valInterstitial = false;
    private Boolean valRewarded= false;

    private Boolean adBannerVisibility = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bannerButton = findViewById(R.id.bannerButton);
        interstitialButton = findViewById(R.id.interstitialButton);
        rewardedButton = findViewById(R.id.rewardedButton);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        //BANNER AD
        adView = findViewById(R.id.adBanner);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                valBanner = true;
                bannerButton.setVisibility(View.VISIBLE);
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
                adView.loadAd(adRequest);
            }
        });


        //INTERSTITIAL AD
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/8691691433");
        interstitialAd.loadAd(new AdRequest.Builder().build());

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                valInterstitial = true;
                interstitialButton.setVisibility(View.VISIBLE);
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


        //REWARDED AD
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());


    }



    public void onClickAdBanner(View view){
        if(valBanner == true && adBannerVisibility == false){
            Toast.makeText(this, "adView Loaded", Toast.LENGTH_SHORT).show();
            adView.setVisibility(View.VISIBLE);
            adBannerVisibility = true;
            bannerButton.setText("Ocultar");
        }else{
            adView.setVisibility(View.INVISIBLE);
            adBannerVisibility = false;
            bannerButton.setText("Banner");
        }

    }

    public void onClickAdInterstitial(View view){
        if(valInterstitial == true) {
            Toast.makeText(this, "InterstitialAd Loaded", Toast.LENGTH_SHORT).show();
            if (interstitialAd.isLoaded()) {
                interstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
        }
    }


    public void onClickAdRewarded(View view){
        if(valRewarded == true) {
            Toast.makeText(this, "RewardedVideoAd Loaded", Toast.LENGTH_SHORT).show();
            if (rewardedVideoAd.isLoaded()) {
                rewardedVideoAd.show();
            }
        }
    }



    @Override
    public void onRewardedVideoAdLoaded() {
        valRewarded = true;
        rewardedButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        //Reload Rewarded Video Ad
        rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {
        //Reload Rewarded Video Ad
        rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
    }
}

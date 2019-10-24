package com.example.labcave;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener{

    private ProgressBar spinner;

    //Buttons
    private Button bannerButton;
    private Button interstitialButton;
    private Button rewardedButton;

    private AdView adView;
    private InterstitialAd interstitialAd;
    private RewardedVideoAd rewardedVideoAd;
    private AdRequest adRequest;

    //Load validation variables
    private Boolean valBanner = false;
    private Boolean valInterstitial = false;
    private Boolean valRewarded= false;

    private Boolean adBannerVisibility = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buttons
        bannerButton = findViewById(R.id.bannerButton);
        interstitialButton = findViewById(R.id.interstitialButton);
        rewardedButton = findViewById(R.id.rewardedButton);

        //Hide buttons
        bannerButton.setVisibility(View.INVISIBLE);
        interstitialButton.setVisibility(View.INVISIBLE);
        rewardedButton.setVisibility(View.INVISIBLE);

        //Create Link URL Description Text View
        Spanned description = Html.fromHtml(getString(R.string.descriptionTextView));
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(description);
        descriptionTextView.setMovementMethod(LinkMovementMethod.getInstance());


        //Instructions Alert Dialog
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Instrucciones");
        alertDialog.setMessage("Haz clic en los botones (Banner, Interstitial y Rewarded) para ver los distintos tipos de Ads.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();


        //ADS
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        //BANNER AD
        adView = findViewById(R.id.adBanner);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(new AdRequest.Builder().build());

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                valBanner = true;
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


        //REWARDED AD
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());



    }




    //***********************************************************************************************************************
    //REWARDED METHODS
    //***********************************************************************************************************************

    @Override
    public void onRewardedVideoAdLoaded() {
        valRewarded = true;
        rewardedButton.setVisibility(View.VISIBLE); //Display button when Ad loads
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
        Log.d("TAG", "Failed to load.");
    }

    @Override
    public void onRewardedVideoCompleted() {
        //Reload Rewarded Video Ad
        rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
    }



    //***********************************************************************************************************************
    //BUTTONS
    //***********************************************************************************************************************

    //Click Banner Button
    public void onClickAdBanner(View view){
        if(valBanner && adBannerVisibility){
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

    //Click Interstitial Button
    public void onClickAdInterstitial(View view){
        if(valInterstitial) {
            Toast.makeText(this, "InterstitialAd Loaded", Toast.LENGTH_SHORT).show();
            if (interstitialAd.isLoaded()) {
                interstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
        }
    }


    //Click Rewarded Button
    public void onClickAdRewarded(View view){
        if(valRewarded) {
            Toast.makeText(this, "RewardedVideoAd Loaded", Toast.LENGTH_SHORT).show();
            if (rewardedVideoAd.isLoaded()) {
                rewardedVideoAd.show();
            }
        }
    }
}

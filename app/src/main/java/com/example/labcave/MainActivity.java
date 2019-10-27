package com.example.labcave;

import com.example.adslibrary.Utility;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;


public class MainActivity extends AppCompatActivity{

    //***********************************************************************************************************************
    //DECLARATIONS
    //***********************************************************************************************************************

    //Buttons
    private Button bannerButton;
    private Button interstitialButton;
    private Button rewardedButton;

    //Ads
    private AdView adView;
    private InterstitialAd interstitialAd;
    private RewardedAdLoadCallback rewardedAdLoadCallback;
    private RewardedAd rewardedAd;

    //Banner visibility
    private Boolean adBannerVisibility = false;



    //***********************************************************************************************************************
    //ON CREATE
    //***********************************************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Class Utility (Library: adsLibrary)
        Utility utilityClass = new Utility();

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


        //ADS--------

        //BANNER AD
        adView = findViewById(R.id.adBanner);
        adView = utilityClass.bannerMethod(adView); //Method banner

        //INTERSTITIAL AD
        interstitialAd = new InterstitialAd(this);
        interstitialAd = utilityClass.intersitialMethod(interstitialAd); //Method intersitial

        //REWARDED AD
        rewardedAd =  new RewardedAd(this, "ca-app-pub-3940256099942544/5224354917");
        rewardedAd = utilityClass.rewardedMethod(rewardedAd, rewardedButton);

        //-----------
    }



    //***********************************************************************************************************************
    //ON START
    //***********************************************************************************************************************

    @Override
    public void onStart(){
        super.onStart();

        //Class Utility (Library: adsLibrary)
        Utility utilityClass = new Utility();

        utilityClass.bannerLoadMethod(adView, bannerButton); //Banner Ad
        utilityClass.intersitialLoadMethod(interstitialAd, interstitialButton); //Interstitial Ad
        rewardedAdLoadCallback = utilityClass.getAdLoadCallback(); //Reward Ad
    }



    //***********************************************************************************************************************
    //ON CLICK BUTTONS
    //***********************************************************************************************************************

    //Click Banner Button
    public void onClickAdBanner(View view){
        //If the Ad is loaded AND the banner is hidden
        if(!adBannerVisibility){
            adView.setVisibility(View.VISIBLE);
            Toast.makeText(this, "adView Loaded", Toast.LENGTH_SHORT).show();
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
        //If the Ad is loaded
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
            Toast.makeText(this, "InterstitialAd Loaded", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    //Click Rewarded Button
    public void onClickAdRewarded(View view){
        //If the Ad is loaded
        if (rewardedAd.isLoaded()) {
            RewardedAdCallback adCallback = new RewardedAdCallback() {

                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // User earned reward.
                }

                public void onRewardedAdOpened() {
                    // Ad opened.
                }

                public void onRewardedAdClosed() {
                    // As closed.
                }

                public void onRewardedAdFailedToShow(int errorCode) {
                    // Ad failed to display
                }
            };
            rewardedAd.show(this, adCallback);
            Toast.makeText(this, "RewardedVideoAd Loaded", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
        }
    }
}

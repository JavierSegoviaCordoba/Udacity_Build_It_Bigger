package com.gradle.udacity.builditbigger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.gradle.udacity.jokefactory.DisplayJokeActivity;

public class MainActivityFragment extends Fragment implements JokeInterface {

    ProgressBar progressBar = null;
    private PublisherInterstitialAd publisherInterstitialAd;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity();

        if (isAdded() && activity != null) {
            publisherInterstitialAd = new PublisherInterstitialAd(activity);
            publisherInterstitialAd.setAdUnitId("/6499/example/interstitial");
            requestInterstitial();
            interstitialListeners();

            AdView adView = activity.findViewById(R.id.adView);
            AdRequest adRequest =
                    new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
            adView.loadAd(adRequest);

            Button button = activity.findViewById(R.id.joke_btn);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (publisherInterstitialAd.isLoaded()) {
                        publisherInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                }
            });

            progressBar = activity.findViewById(R.id.joke_progressbar);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getJoke() {
        EndpointAsyncTask jokeEndpoint = new EndpointAsyncTask();
        jokeEndpoint.setJokeInterface(this);
        jokeEndpoint.execute();
    }

    public void startDisplayJokeActivity(String joke) {
        if (getActivity() != null) {
            Activity activity = getActivity();
            Intent intent = new Intent(activity, DisplayJokeActivity.class);
            if (activity != null) {
                intent.putExtra(activity.getString(R.string.joke_tag), joke);
                activity.startActivity(intent);
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    private void requestInterstitial() {
        publisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
    }

    private void interstitialListeners() {
        publisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                requestInterstitial();
            }

            @Override
            public void onAdOpened() {
                requestInterstitial();
            }

            @Override
            public void onAdLeftApplication() {
                requestInterstitial();
            }

            @Override
            public void onAdClosed() {
                progressBar.setVisibility(View.VISIBLE);
                getJoke();
                requestInterstitial();
            }
        });
    }

    @Override
    public void onJokeSuccess(String joke) {
        startDisplayJokeActivity(joke);
    }

    @Override
    public void onJokeError() {
        if (getActivity() != null) {
            Toast.makeText(getActivity(),
                    getActivity().getResources().getString(R.string.backend_error),
                    Toast.LENGTH_SHORT).show();
        }

        progressBar.setVisibility(View.GONE);
    }
}

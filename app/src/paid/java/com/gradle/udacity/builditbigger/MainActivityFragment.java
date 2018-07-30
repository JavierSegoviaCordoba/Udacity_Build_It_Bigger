package com.gradle.udacity.builditbigger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gradle.udacity.jokefactory.DisplayJokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ProgressBar progressBar = null;
    public String loadedJoke = null;

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
            Button button = activity.findViewById(R.id.joke_btn);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    getJoke();
                }
            });

            progressBar = activity.findViewById(R.id.joke_progressbar);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getJoke() {
        new EndpointAsyncTask().execute(this);
    }

    public void startDisplayJokeActivity() {
        if (getActivity() != null) {
            Activity activity = getActivity();
            Intent intent = new Intent(activity, DisplayJokeActivity.class);
            Toast.makeText(activity, loadedJoke, Toast.LENGTH_LONG).show();
            if (activity != null) {
                intent.putExtra(activity.getString(R.string.joke_tag), loadedJoke);
                activity.startActivity(intent);
                progressBar.setVisibility(View.GONE);
            }
        }
    }
}

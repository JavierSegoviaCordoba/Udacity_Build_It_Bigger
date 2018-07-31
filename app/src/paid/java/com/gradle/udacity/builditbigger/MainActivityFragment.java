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
public class MainActivityFragment extends Fragment implements JokeInterface {

    ProgressBar progressBar = null;

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

            progressBar = activity.findViewById(R.id.joke_progressbar);
            progressBar.setVisibility(View.GONE);

            Button button = activity.findViewById(R.id.joke_btn);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    getJoke();
                }
            });
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

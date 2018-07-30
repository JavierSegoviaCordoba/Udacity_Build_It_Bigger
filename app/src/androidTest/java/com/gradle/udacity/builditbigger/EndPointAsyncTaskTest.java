package com.gradle.udacity.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EndPointAsyncTaskTest {


    @Test
    public void testDoInBackground() throws InterruptedException {
        final MainActivityFragment fragment = new MainActivityFragment();
        EndpointAsyncTask endPointAsyncTask = new EndpointAsyncTask();
        endPointAsyncTask.execute(fragment);
        Thread.sleep(3500);
        assertTrue("Error: "
                        + fragment.loadedJoke,
                fragment.loadedJoke != null);
    }
}

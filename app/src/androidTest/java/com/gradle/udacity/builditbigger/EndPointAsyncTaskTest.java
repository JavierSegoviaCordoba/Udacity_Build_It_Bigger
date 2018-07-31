package com.gradle.udacity.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class EndPointAsyncTaskTest implements JokeInterface {

    private CountDownLatch countDownLatch;
    private String joke;

    @Test
    public void testAsyncTask() {
        try {
            countDownLatch = new CountDownLatch(1);
            EndpointAsyncTask endpointAsyncTask = new EndpointAsyncTask();
            endpointAsyncTask.setJokeInterface(this);
            endpointAsyncTask.execute();
            countDownLatch.await(10, TimeUnit.SECONDS);
            assertNotNull("joke == null", joke);
            assertFalse("joke.isEmpty()", joke.isEmpty());
        } catch (Exception ex) {
            fail();
        }
    }

    @Override
    public void onJokeSuccess(String joke) {
        this.joke = joke;
        countDownLatch.countDown();
    }

    @Override
    public void onJokeError() {

    }
}

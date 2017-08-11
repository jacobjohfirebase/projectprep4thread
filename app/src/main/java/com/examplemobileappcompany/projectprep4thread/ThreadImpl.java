package com.examplemobileappcompany.projectprep4thread;

import android.app.Activity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by admin on 8/11/2017.
 */

public class ThreadImpl extends Thread {

    MainActivity activity;

    public ThreadImpl(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public void run() {
        final String prefix = "used Thread Class, ";

        Event event = new Event(prefix + "sent by EventBus");
        EventBus.getDefault().post(event);


        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.setRunOnUiText(prefix  + "sent by runOnUiThread");
                    }
                }
        );



        activity.handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.setHandlerText(prefix + "sent by Handler");
                    }
                }
        );


    }

}

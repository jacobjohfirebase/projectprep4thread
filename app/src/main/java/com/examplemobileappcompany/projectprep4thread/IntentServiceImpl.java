package com.examplemobileappcompany.projectprep4thread;

/**
 * Created by admin on 8/11/2017.
 */

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

public class IntentServiceImpl extends IntentService {

    public IntentServiceImpl(){
        super("");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public IntentServiceImpl(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("mylog", "in intent");
        final String prefix = "used IntentService, ";

        Event event = new Event(prefix + "sent by EventBus");
        EventBus.getDefault().post(event);
/*
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.setRunOnUiText(prefix + "sent by runOnUiThread");
            }
        });

        activity.handler.post(new Runnable() {
            @Override
            public void run() {
                activity.setHandlerText(prefix + "sent by Handler");
            }
        });*/
    }
}


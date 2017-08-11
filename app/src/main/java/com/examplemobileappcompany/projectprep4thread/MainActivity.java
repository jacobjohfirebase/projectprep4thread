package com.examplemobileappcompany.projectprep4thread;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/*
2. Create an application which have four buttons.
  -Each will start a new thread in a different way (Thread class, Runnable interface, Asynctask, Intent Service)
  -Communicate back to the UI thread using EventBus, runOnUIThread and Handler. Use all these methods in each of them.
  Each thread will update a different textview in the mainActivity.
 */
public class MainActivity extends AppCompatActivity {

    private TextView eventBusTextView;

    private TextView runOnUiTextView;

    private TextView handlerTextView;

    public Handler handler;

    public MainActivity mainActivity = this;


 /*   private Button threadButton;

    private Button runnableButton;

    private Button asyncTaskButton;

    private Button intentServiceButton;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventBusTextView = (TextView) findViewById(R.id.eventBusTextView);

        runOnUiTextView = (TextView) findViewById(R.id.runOnUiTextView);

        handlerTextView = (TextView) findViewById(R.id.handlerTextView);

        handler = new Handler();
    }

    public void setRunOnUiText(String text){
        runOnUiTextView.setText(text);
    }

    public void setHandlerText(String text){
        handlerTextView.setText(text);
    }

    public void startThreadClass(View view) {

        ThreadImpl thread = new ThreadImpl(this);
        thread.start();
    }

    public void startRunnable(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String prefix = "used Runnable Interface, ";

                Event event = new Event(prefix + "sent by EventBus");
                EventBus.getDefault().post(event);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.setRunOnUiText(prefix + "sent by runOnUiThread");
                    }
                });

                mainActivity.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.setHandlerText(prefix + "sent by Handler");
                    }
                });
            }
        }).start();
    }

    public void startAsyncTask(View view) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                final String prefix = "used AsyncTask, ";

                Event event = new Event(prefix + "sent by EventBus");
                EventBus.getDefault().post(event);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.setRunOnUiText(prefix + "sent by runOnUiThread");
                    }
                });

                mainActivity.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.setHandlerText(prefix + "sent by Handler");
                    }
                });
                return null;
            }
        }.execute();
    }

    public void startIntentService(View view) {

        Intent intent = new Intent(this, IntentServiceImpl.class);
        startService(intent);

        setRunOnUiText("impractical to use IntentService with RunOnUi as IntentService cannot reference an activity");
        setHandlerText("impractical to use IntentService with Handler as IntentService cannot reference an activity");
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(Event event){
        eventBusTextView.setText(event.getMessage());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}

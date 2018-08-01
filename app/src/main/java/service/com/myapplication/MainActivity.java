package service.com.myapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private final String TAG = "MainActivity";
    public static final String BROADCAST_ACTION = "service.com.broadcastreceiver";

    private Button startCounter;
    private  Button stopCounter;

    private TextView counterTxt;
    private MyBroadCastReceiver myBroadCastReceiver;
    private Intent myServiceIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeMembers();


        registerMyReceiver();
    }

    private void initializeMembers()
    {
        startCounter = findViewById(R.id.start);
        stopCounter = findViewById(R.id.stop);
        counterTxt = findViewById(R.id.textView);
        myBroadCastReceiver = new MyBroadCastReceiver();
        startCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMyService();
            }
        });
        stopCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myServiceIntent != null){
                    stopService(myServiceIntent);
                }
            }
        });
    }
    private void startMyService()
    {
        try
        {
            myServiceIntent = new Intent(this, TestServiceWithBroadcast.class);
            startService(myServiceIntent);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * This method is responsible to register an action to BroadCastReceiver
     * */
    private void registerMyReceiver() {

        try
        {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(BROADCAST_ACTION);
            registerReceiver(myBroadCastReceiver, intentFilter);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
    class MyBroadCastReceiver extends BroadcastReceiver
    {
        /*
        * Receives the counter from the service*/
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                long millisUntilFinished = intent.getIntExtra("countdown", 0);
                counterTxt.setText(String.valueOf(millisUntilFinished));
            }
        }
    }

    /**
     * This method called when this Activity finished
     * Override this method to unregister MyBroadCastReceiver
     * */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // make sure to unregister your receiver after finishing of this activity
        unregisterReceiver(myBroadCastReceiver);
    }
}

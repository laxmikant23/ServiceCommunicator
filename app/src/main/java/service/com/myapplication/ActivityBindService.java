package service.com.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

/**
 * Created by laxmikant on 01/08/18.
 */

public class ActivityBindService extends Activity {
    private BindService bindService;
    private boolean isBound = false;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_bind_service);

        textView = findViewById(R.id.dateText);
        findViewById(R.id.checkDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBound) {
                    textView.setText(String.valueOf(bindService.getCurrentDate()));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
        * Start the service
        * */
        Intent intent = new Intent(ActivityBindService.this, BindService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
         /*
         * Stop the service bounded
         * */
        if (isBound) {
            unbindService(serviceConnection);
        }
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BindService.MyBinder localIBinder = (BindService.MyBinder) iBinder;
            bindService = localIBinder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };
}

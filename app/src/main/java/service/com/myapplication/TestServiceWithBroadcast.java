package service.com.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by laxmikant on 01/08/18.
 */

public class TestServiceWithBroadcast extends Service {
    private final static String TAG = "BroadcastService";

    public static final String COUNTDOWN_BR = "service.com.broadcastreceiver";
    Intent broadcastIntent = new Intent(COUNTDOWN_BR);

    private CountDownTimer countDownTimer = null;
    private int count = 30;

    @Override
    public void onCreate() {
        super.onCreate();
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //Send counter via Boradcast Receiver
                if (count >= 0) {
                    count = count - 1;
                    Log.e(TAG, "" + count);
                    broadcastIntent.putExtra("countdown", count);
                    sendBroadcast(broadcastIntent);
                } else {
                    stopSelf();
                }
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "Timer finished");
            }
        };

        countDownTimer.start();
    }

    //Needs to oveerride this method returning NULL if you are not binding it to any component
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();

    }
}


package service.com.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by laxmikant on 01/08/18.
 */

public class BindService extends Service {
    Binder iBinder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    class MyBinder extends Binder {
        BindService getService(){
            return BindService.this;
        }
    }
    public Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }
}

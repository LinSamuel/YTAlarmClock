package sam.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Sam on 9/16/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("we are in the receiver", "hooray");

        //fetch extra strings from intent

        String extraString = intent.getExtras().getString("extra");
        Log.e("the key", extraString);

        //create intent for ringtone service

        Intent serviceIntent = new Intent(context, RingtonePlayerService.class);

        //pass the extra string from mainactivity to ringtone player service
        serviceIntent.putExtra("extra", extraString);

        //starts ringtone service
        context.startService(serviceIntent);
    }
}

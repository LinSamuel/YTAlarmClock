package sam.alarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Sam on 9/16/2016.
 */
public class RingtonePlayerService extends Service{

    public void watchYoutubeVideo(String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(appIntent);

        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    MediaPlayer media_song;
    int startId;
    boolean isRunning = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

/*
    public int onStartCommand(Intent intent){
        Log.e("hi", "hi2");
        return START_NOT_STICKY;
    }
*/

    /////


/*    private NotificationManager mNM;

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private int NOTIFICATION = R.string.local_service_started;

    *//**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     *//*
    public class LocalBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }*/

/*    @Override
    public void onCreate() {
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification();
    }*/




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        //get the extra string values
        String state = intent.getExtras().getString("extra");
        String videoID = intent.getExtras().getString("id");

        assert state != null;
        if (state.equals("on")){
            startId = 1;
        }
        else if (state.equals("off")){
            startId = 0;
        } else {
            startId = 0;
        }
        // if no music and user presses alarm on, alarm should go off
        if (!this.isRunning && startId == 1 ) {
            Log.e("No alarm",  "lets turn it on");

            watchYoutubeVideo(videoID);

            //set up notification manager
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            //set up intent that goes to main activity

            Intent intent_main_activity = new Intent(this.getApplicationContext(), MainActivity.class);

            // set pending intent
            PendingIntent pendingIntentMainActivity = PendingIntent.getActivity(this, 0, intent_main_activity, 0);

            //make notification parameters

            Notification notification_popup = new Notification.Builder(this)
                    .setContentTitle("Alarm went off")
                    .setContentText("Click here")
                    .setContentIntent(pendingIntentMainActivity)
                    .setSmallIcon(R.drawable.youtubeicon)
                    .setAutoCancel(true)
                    .build();

            //set up notification call command
            notificationManager.notify(0, notification_popup);
        }
        // if there is music and user presses alarm off, music should start
        else if (this.isRunning && startId == 0) {
            Log.e("There is music,", "lets turn it off");

/*            //stop music
            media_song.stop();
            media_song.reset();
            media_song.release();
            media_song = null;*/

            this.isRunning = false;
            this.startId = 0;
        }
        // if there is no music and user presses alarm off, do nothing
        else if (!this.isRunning && startId == 0) {
            Log.e("There is no music,",  "lets turn it off(nothing)");

            this.isRunning = false;
            this.startId = 0;

        }
        // if there is music and user presses alarm on, do nothing
        else if (!this.isRunning && startId == 1) {
            Log.e("There is music,",  "lets turn it on (nothing)");
            this.isRunning = true;
            this.startId = 1;
        }
        // ignore other error cases as well
        else {
            Log.e("else",  "else");
        }


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        // Tell the user we stopped.
        Toast.makeText(this, "destroy called", Toast.LENGTH_SHORT).show();
    }


}

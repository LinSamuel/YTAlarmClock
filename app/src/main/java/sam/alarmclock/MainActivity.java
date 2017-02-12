package sam.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    AlarmManager theAlarmManager;
    TimePicker theTimePicker;
    TextView update_text;
    TextView video_text;
    Context context;
    PendingIntent pendingIntent;
    String selectedURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        this.context = this;

        //initialize

        theTimePicker = (TimePicker) findViewById(R.id.timePicker);

        theAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        update_text = (TextView) findViewById(R.id.update_text);

        video_text = (TextView) findViewById(R.id.video_text);

        final Calendar calendar = Calendar.getInstance();

        Button start_alarm = (Button) findViewById(R.id.alarm_on);

        Button end_alarm = (Button) findViewById(R.id.alarm_off);

        //Create intent for alarm receiver
        final Intent alarmIntent = new Intent(this.context, AlarmReceiver.class);

        if (getIntent() != null){
            set_video_text(getIntent().getStringExtra("name") + " is the currently selected video");
            selectedURL = getIntent().getStringExtra("url");
        }

        if (start_alarm != null) {
            start_alarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //method that changes update_text
                    calendar.set(Calendar.HOUR_OF_DAY, theTimePicker.getHour());
                    calendar.set(Calendar.MINUTE, theTimePicker.getMinute());

                    String hour = String.valueOf(theTimePicker.getHour());
                    String minute = String.valueOf(theTimePicker.getMinute());

                    // Add an extra day if the time is earlier than the current day

                    System.out.println("calendar " + calendar.getTimeInMillis());
                    System.out.println("system " + System.currentTimeMillis());
                    if(calendar.getTimeInMillis() < System.currentTimeMillis()){
                        //calendar.setTimeInMillis(calendar.getTimeInMillis());
                        calendar.add(Calendar.DATE, 1);
                        System.out.println("adding an extra day... " + calendar.getTimeInMillis());
                    }

                    if (theTimePicker.getHour() > 12){
                        hour = String.valueOf(theTimePicker.getHour() - 12);
                    }

                    if (theTimePicker.getMinute() < 10){
                        minute = "0" + minute;
                    }

                    set_alarm_text(" Alarm on: " + hour + ":"+ minute);

                    //put extra string in intent, tells clock that you pressed alarm on button
                    alarmIntent.putExtra("extra", "on");
                    alarmIntent.putExtra("id", extractVideoID(selectedURL));


                    //Create pending intent for specified calendar time

                    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    //Set alarm manager
                    theAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
                }
            });
        }

        if (end_alarm != null) {
            end_alarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    set_alarm_text("Alarm is set off :(");

                    //Cancel pending intent

                    theAlarmManager.cancel(pendingIntent);

                    //put extra string in intent, tells clock that you pressed alarm off button
                    alarmIntent.putExtra("extra", "off");

                    sendBroadcast(alarmIntent);
                }
            });
        }





    }

    private void set_video_text(String s){
        video_text.setText(s);
    }

    private void set_alarm_text(String s) {
        update_text.setText(s);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onButtonClicked(View v){
        if (v.getId() == R.id.theYoutubeButton){
            Intent intent = new Intent(this,VideoListActivity.class);
            intent.putExtra("MESSAGE", "starting youtube list");
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Function that extracts the video ID from a youtube link in the form like "https://youtu.be/Z-tTmSY4m4M"
    // This format is the current format of the shared links from the actual YouTube mobile application
    // id will have the form like: //youtu.be/Z-tTmSY4m4M

    // TODO later, make this more robust, allow it to check for and accordingly for other link formats.
    public String extractVideoID(String id){
        System.out.println("the id: " + id);
        System.out.println("gonna shorten: " + id.substring(11));
        return id.substring(11);
    }


}

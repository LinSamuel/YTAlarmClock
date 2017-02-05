package sam.alarmclock;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


public class VideoListActivity extends AppCompatActivity {

    TextView testTextView;
    TextView textViewIntent;
    DBHandler dbHandler;
    String selectedURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        testTextView = (TextView) findViewById(R.id.textViewTest);
        textViewIntent = (TextView) findViewById(R.id.textViewIntent);
        dbHandler = new DBHandler(this, null, null, 1);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if(Intent.ACTION_SEND.equals(action) && type != null){
            if ("text/plain".equals(type)){
                handleYoutubeURL(intent);
            }
        }

        Intent currentIntent = NavUtils.getParentActivityIntent(this);

        PendingIntent pendingIntent =
                TaskStackBuilder.create(this)
                        // add all of DetailsActivity's parents to the stack,
                        // followed by DetailsActivity itself
                        .addNextIntentWithParentStack(currentIntent)
                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(pendingIntent);

        System.out.println("here");



        printDatabase();



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public void onBackPressed(){
        Intent vidIntent = new Intent(VideoListActivity.this,MainActivity.class);
        vidIntent.putExtra("title", selectedURL);
        startActivity(vidIntent);

    }

    public void handleYoutubeURL(Intent intent){
        String url = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (url != null){
            selectedURL = url;
            textViewIntent.setText(url);
        }
    }

    //Print the database
    public void printDatabase() {
        String dbString = dbHandler.databaseToString();
        testTextView.setText(dbString);
    }
    public void testButtonClicked(View view){
        System.out.println("hi0");
        VideoOption videoOption = new VideoOption("zdEhHLjtxDA");
        dbHandler.addVideoOption(videoOption);
        printDatabase();
    }
}

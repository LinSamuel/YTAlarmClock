package sam.alarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


public class VideoListActivity extends AppCompatActivity {

    TextView testTextView;
    TextView textViewIntent;
    DBHandler dbHandler;

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

    public void handleYoutubeURL(Intent intent){
        String url = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (url != null){
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

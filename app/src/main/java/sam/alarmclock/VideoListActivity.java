package sam.alarmclock;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class VideoListActivity extends AppCompatActivity {

    TextView testTextView;
    TextView textViewIntent;
    EditText userInput;
    DBHandler dbHandler;
    //Boolean flag used to check if a YoutubeURL has been shared, or else don't add to database
    Boolean urlPlaceHolder = false;

    // url and name of the current picked option on listview
    String selectedURL;
    String selectedName;

    String currentURL;
    ArrayList<String> urlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        testTextView = (TextView) findViewById(R.id.textViewTest);
        textViewIntent = (TextView) findViewById(R.id.textViewIntent);
        userInput = (EditText) findViewById(R.id.userInput);

        dbHandler = new DBHandler(this, null, null, 1);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if(Intent.ACTION_SEND.equals(action) && type != null){
            if ("text/plain".equals(type)){
                handleYoutubeURL(intent);
            }
        }

//        Intent currentIntent = NavUtils.getParentActivityIntent(this);
//
//        PendingIntent pendingIntent =
//                TaskStackBuilder.create(this)
//                        // add all of DetailsActivity's parents to the stack,
//                        // followed by DetailsActivity itself
//                        .addNextIntentWithParentStack(currentIntent)
//                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setContentIntent(pendingIntent);
//
//        System.out.println("here");

        //urlList = new ArrayList<String>();

//        urlList = dbHandler.databaseToStringList();

        setUpListView();


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

        //Send extra strings representing the selected name and url to main activity
        Intent vidIntent = new Intent(VideoListActivity.this,MainActivity.class);
        vidIntent.putExtra("name", selectedName);
        vidIntent.putExtra("url", selectedURL);
        System.out.println("sending URL from vlistactivity: "+ selectedURL);
        startActivity(vidIntent);

    }

    public void handleYoutubeURL(Intent intent){
        String url = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (url != null){
            selectedURL = url;
            currentURL = url;
            urlPlaceHolder = true;
            textViewIntent.setText(url);
        }
    }

    //Print the database
    public void printDatabase() {
        String dbString = dbHandler.databaseToString();
        testTextView.setText(dbString);
    }
    public void testButtonClicked(View view){
        //VideoOption videoOption = new VideoOption("zdEhHLjtxDA");
        if (urlPlaceHolder){
            VideoOption videoOption = new VideoOption(currentURL, userInput.getText().toString()); //Costructor is (videoURL, videoName)
            dbHandler.addVideoOption(videoOption);
            printDatabase();
            setUpListView();
        } else {
            Toast.makeText(VideoListActivity.this, "Share a valid Youtube URL first before adding video", Toast.LENGTH_SHORT).show();
        }

    }
    public void clearTable(View view){
        dbHandler.clearTable();
        printDatabase();
    }

    public void setUpListView(){
        urlList = dbHandler.databaseToStringList();

        ListAdapter customListAdapter = new OptionAdapter(this,urlList);//
        ListView customListView = (ListView) findViewById(R.id.VideoListView);
        customListView.setAdapter(customListAdapter);

        customListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String choice = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(VideoListActivity.this, choice, Toast.LENGTH_SHORT).show();

                        //Workaround to extract the title and URL, split by colons and extract the first and third indeces to get the title and URL
                        // the video links are expected to be in the form of:"https://youtu.be/Z-tTmSY4m4M"
                        String[] splittedChoice = choice.split(":");
                        selectedName = splittedChoice[1].split("\n")[0];
                        selectedURL = splittedChoice[3];
                    }
                }
        );


    }
}

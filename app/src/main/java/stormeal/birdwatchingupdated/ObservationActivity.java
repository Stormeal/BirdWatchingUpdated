package stormeal.birdwatchingupdated;

/**
 * Created by Alex on 15-04-2018.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ObservationActivity extends AppCompatActivity {
    private Observation observation;
    //private EditText titleView, authorView, publisherView, priceView;
    private EditText userIdView, createdView, latitudeView, longitudeView, placenameView, populationView, commentView, nameDanishView, nameEnglishView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation);

        Intent intent = getIntent();
        observation = (Observation) intent.getSerializableExtra("OBSERVATION");

        TextView headingView = findViewById(R.id.observation_heading);
        headingView.setText("Observation Id= " + observation.getId());

        userIdView = findViewById(R.id.observation_UserId_edittext);
        userIdView.setText(observation.getUserId());

        createdView = findViewById(R.id.observation_created_edittext);
        createdView.setText(observation.getCreated());

        latitudeView = findViewById(R.id.observation_latitude_edittext);
        latitudeView.setText(observation.getLatitude() + " ");

        longitudeView = findViewById(R.id.observation_longitude_edittext);
        longitudeView.setText(observation.getLongitude() + " ");

        placenameView = findViewById(R.id.observation_placename_edittext);
        placenameView.setText(observation.getPlacename());

        populationView = findViewById(R.id.observation_population_edittext);
        populationView.setText(observation.getPopulation()+ " ");

        commentView = findViewById(R.id.observation_comment_edittext);
        commentView.setText(observation.getComment());

        nameEnglishView = findViewById(R.id.observation_nameEnglish_edittext);
        nameEnglishView.setText(observation.getNameEnglish());

        nameDanishView = findViewById(R.id.observation_nameDanish_edittext);
        nameDanishView.setText(observation.getNameDanish());

    }

    public void deleteObservation(View view) {
        DeleteTask task = new DeleteTask();
        task.execute("http://birdobservationservice.azurewebsites.net/service1.svc/observation/" + observation.getId());
        finish();
    }

    public void updateObservation(View view) {
        // code missing: Left as an exercise
    }


    public void back(View view) {
        finish();
    }

    private class DeleteTask extends AsyncTask<String, Void, CharSequence> {
        @Override
        protected CharSequence doInBackground(String... urls) {
            String urlString = urls[0];
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");
                int responseCode = connection.getResponseCode();
                if (responseCode % 100 != 2) {
                    throw new IOException("Response code: " + responseCode);
                }
                return "Nothing";
            } catch (MalformedURLException e) {
                return e.getMessage() + " " + urlString;
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onCancelled(CharSequence charSequence) {
            super.onCancelled(charSequence);
            TextView messageView = findViewById(R.id.observation_message_textview);
            messageView.setText(charSequence);

        }
    }
}
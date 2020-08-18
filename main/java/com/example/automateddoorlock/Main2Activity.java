package com.example.automateddoorlock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    Button lock, unlock, logout;
    String urlLock, urlUnlock, param1, param2, locked, unlocked, current, unknownStatus, unknown;
    TextView status;
    ImageView padlock;
    int lockedID, unlockedID, unknownID;
    ProgressBar spinner;

    JSONParser jsonParser=new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        lock = (Button) findViewById(R.id.lock);
        unlock = (Button) findViewById(R.id.unlock);
        logout = (Button) findViewById(R.id.logout);
        param1 = "";
        param2 = "";
        status = (TextView)findViewById(R.id.status);
        padlock = (ImageView)findViewById(R.id.padlock);
        locked = "ic_action_locked";
        unlocked = "ic_action_unlocked";
        unknown = "ic_action_unknown";
        lockedID = getResources().getIdentifier(locked , "drawable", getPackageName());
        unlockedID = getResources().getIdentifier(unlocked, "drawable", getPackageName());
        unknownID = getResources().getIdentifier(unknown, "drawable", getPackageName());
        current = "";
        unknownStatus = "Status Unknown";

        urlLock = "http://192.168.0.10/cgi-bin/lock.py";
        urlUnlock = "http://192.168.0.10/cgi-bin/unlock.py";

        spinner.setVisibility(View.GONE);

        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AttemptLock attemptLogin = new AttemptLock();
                attemptLogin.execute(param1, param2);
                status.setText("Locking...");
                padlock.setVisibility(View.GONE);
                lock.setVisibility(View.GONE);
                unlock.setVisibility(View.GONE);
                spinner.setVisibility(View.VISIBLE);
            }
        });

        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AttemptUnlock attemptLogin = new AttemptUnlock();
                attemptLogin.execute(param1, param2);
                status.setText("Unlocking...");
                padlock.setVisibility(View.GONE);
                lock.setVisibility(View.GONE);
                unlock.setVisibility(View.GONE);
                spinner.setVisibility(View.VISIBLE);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(Main2Activity.this, Main4Activity.class);
                Main2Activity.this.startActivity(myIntent);
            }
        });
    }


    private class AttemptLock extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            String password = args[1];
            String name = args[0];

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("username", name));
            params.add(new BasicNameValuePair("password", password));
            JSONObject json = jsonParser.makeHttpRequest(urlLock, "POST", params);
            return json;
        }

        protected void onPostExecute(JSONObject result) {
            // dismiss the dialog once product deleted
            // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            if (result != null) {
                //Toast.makeText(getApplicationContext(), "Door Successfully Locked", Toast.LENGTH_SHORT).show();
                current = "Locked";
                status.setText(current);
                padlock.setVisibility(View.VISIBLE);
                padlock.setImageResource(lockedID);
            } else {
                status.setText(unknownStatus);
                padlock.setVisibility(View.VISIBLE);
                padlock.setImageResource(unknownID);
            }
            spinner.setVisibility(View.GONE);
            unlock.setVisibility(View.VISIBLE);
            lock.setVisibility(View.VISIBLE);
        }
    }
    private class AttemptUnlock extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected JSONObject doInBackground(String... args) {
                String password = args[1];
                String name= args[0];

                ArrayList params = new ArrayList();
                params.add(new BasicNameValuePair("username", name));
                params.add(new BasicNameValuePair("password", password));
                JSONObject json = jsonParser.makeHttpRequest(urlUnlock, "POST", params);
                return json;
            }

            protected void onPostExecute(JSONObject result) {
                // dismiss the dialog once product deleted
                // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

                if (result != null) {
                    //Toast.makeText(getApplicationContext(), "Door Successfully Unlocked", Toast.LENGTH_SHORT).show();
                    current = "Unlocked";
                    status.setText(current);
                    padlock.setVisibility(View.VISIBLE);
                    padlock.setImageResource(unlockedID);
                }
                else {
                    status.setText(unknownStatus);
                    padlock.setVisibility(View.VISIBLE);
                    padlock.setImageResource(unknownID);
                }
                spinner.setVisibility(View.GONE);
                unlock.setVisibility(View.VISIBLE);
                lock.setVisibility(View.VISIBLE);
            }
        }
}

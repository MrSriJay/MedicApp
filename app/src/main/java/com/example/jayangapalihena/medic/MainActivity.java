package com.example.jayangapalihena.medic;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private TextView label;
    public String raw = "";
    public String dataParsed = "";

    // ------------------------ Place your URL here ------------------------
    public String your_URL_Here="http://af256ba3.ngrok.io/";
    // ------------------------                     ------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        label = (TextView) findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("StaticFieldLeak") AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {

                        try {
                            URL url = new URL(your_URL_Here+raw);
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            InputStream inputStream = httpURLConnection.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                            StringBuffer response = new StringBuffer();
                            String inputLine;
                            while ((inputLine = bufferedReader .readLine()) != null) {
                                response.append(inputLine);
                            }
                            JSONObject obj_JSONObject = new JSONObject(response.toString());
                            JSONArray obj_JSONArray = obj_JSONObject.getJSONArray("result");
                            //arrayTags= new String[obj_JSONArray.length()];
                            for(int i=0; i<obj_JSONArray.length(); i++) {
                                //arrayTags[i]=obj_JSONArray.optString(i).toString();
                                //arrayTags[i]=dataParsed;
                                dataParsed=dataParsed+" \n\n"+obj_JSONArray.optString(i);
                            }

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        label.setText(dataParsed);
                        dataParsed = "";
                        label.setMovementMethod(new ScrollingMovementMethod());
                    }

                    @Override
                    protected void onPreExecute() {

                        super.onPreExecute();
                        label.setText("");
                        raw=editText.getText().toString();
                        ;
                    }
                }.execute();
            }
        });
    }
}

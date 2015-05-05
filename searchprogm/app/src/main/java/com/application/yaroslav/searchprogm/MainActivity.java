package com.application.yaroslav.searchprogm;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button theButton;
    private TextView formatTxt, contentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theButton = (Button) findViewById(R.id.button);
        theButton.setOnClickListener(this);
        formatTxt = (TextView) findViewById(R.id.scan_format);
        contentTxt = (TextView) findViewById(R.id.scan_content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.button) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {

                    // Set the Accept header
                    HttpHeaders requestHeaders = new HttpHeaders();
                    requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
                    HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

                    String url = "http://192.168.0.101:8080/api/food/get/40907000EAN_8";

                    // Create a new Rest Template instance
                    RestTemplate restTemplate = new RestTemplate();

                    // Make the HTTP GET request, marshaling the response from JSON to an array of Events
                    ResponseEntity<Food> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Food.class);
                    Food events = responseEntity.getBody();
                    // Make the HTTP GET request, marshaling the response to a String

//            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
//            scanIntegrator.initiateScan();

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    goNewView(v);
                }
            }.execute(null, null, null);
        }
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
//        if (scanningResult != null) {
//            String scanContent = scanningResult.getContents();
//            String scanFormat = scanningResult.getFormatName();
//            formatTxt.setText("FORMAT: " + scanFormat);
//            contentTxt.setText("CONTENT: " + scanContent);
//        } else{
//            Toast toast = Toast.makeText(getApplicationContext(),
//                    "No scan data received!", Toast.LENGTH_SHORT);
//            toast.show();
//        }
//    }

    public void goNewView(View v) {
        switch (v.getId()) {
            case R.id.button:
                // ������� ����� ������ Activity ����� ����������� �����
                Intent intent = new Intent(this, NewActivity.class);

                // ��������� ������ ���������� ����, � ������ ��������
                // �� ����� �� ����� �������� �������� � Intent
//                intent.putExtra("format", formatTxt.getText().toString());
//                intent.putExtra("context", contentTxt.getText().toString());

                // ���������� ����� Activity
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}

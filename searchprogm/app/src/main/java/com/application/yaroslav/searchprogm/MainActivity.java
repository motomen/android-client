package com.application.yaroslav.searchprogm;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.application.yaroslav.searchprogm.entity.CategoryItem;
import com.application.yaroslav.searchprogm.entity.Food;
import com.application.yaroslav.searchprogm.util.Constant;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private String partUrl = Constant.url;
    private Button theButton;
    private Button search;
    private Button catalog;
    private Food retryFood = null;
    private EditText editText;
    private ArrayList<CategoryItem> categoryItems = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theButton = (Button) findViewById(R.id.bsearch);
        theButton.setOnClickListener(this);
        search = (Button) findViewById(R.id.button);
        search.setOnClickListener(this);
        catalog = (Button) findViewById(R.id.catalog_button);
        catalog.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.editText);
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
        if (v.getId() == R.id.bsearch) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    if (editText.getText().toString().length() != 0) {
                        // Set the Accept header
                        HttpHeaders requestHeaders = new HttpHeaders();
                        requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
                        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

                        String url = partUrl + "/api/food/get/" + editText.getText().toString().trim(); //40907000EAN_8

                        // Create a new Rest Template instance
                        RestTemplate restTemplate = new RestTemplate();

                        // Make the HTTP GET request, marshaling the response from JSON to an array of Events
                        try {
                            ResponseEntity<Food> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Food.class);
                            HttpStatus httpStatus = responseEntity.getStatusCode(); // this for not found elem in database
                            Food events = responseEntity.getBody();
                            // Make the HTTP GET request, marshaling the response to a String
                            retryFood = events;
                        } catch (HttpClientErrorException e) {
                            e.printStackTrace();
                        }
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    goNewView(v);
                }
            }.execute(null, null, null);
        }

        if (v.getId() == R.id.button) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }

        if (v.getId() == R.id.catalog_button) {
            goNewView();
        }
    }

    public void goNewView() {
    //    if (categoryItems != null) {
         //   Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
       //     intent.putExtra("listCategory", categoryItems);
         //   startActivity(intent);
     //   }
        CategoryActivity categoryActivity = new CategoryActivity();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.itemListFragment, categoryActivity);
        ft.commit();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            editText.setText(scanContent + scanFormat);
        }
    }

    public void goNewView(View v) {
        switch (v.getId()) {
            case R.id.bsearch:
                if (retryFood != null) {
                    Intent intent = new Intent(this, NewActivity.class);
                    intent.putExtra("food", retryFood);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, NotFoundActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }
}

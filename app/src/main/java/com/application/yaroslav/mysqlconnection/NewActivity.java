package com.application.yaroslav.mysqlconnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.application.yaroslav.mysqlconnection.parser.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewActivity extends Activity {

    private Button back;
    private TextView name;
    private TextView lastName;

    private ProgressDialog pDialog;

    ArrayList<HashMap<String, String>> productsList;

    // Создаем JSON парсер
    JSONParser jParser = new JSONParser();

    // url получения списка всех продуктов
    private static String url_all_products = "localhost:8080/api/food/get/";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_FOOD = "food";
    private static final String TAG_NAME = "name";

    // тут будет хранится продукт
    JSONObject food = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        back = (Button) findViewById(R.id.back_button);
        name = (TextView) findViewById(R.id.nametxt);
        lastName = (TextView) findViewById(R.id.lastnametxt);
        // Hashmap for ListView
        productsList = new ArrayList<HashMap<String, String>>();
        // Принимаем имя
        String txtName = getIntent().getStringExtra("format");

        // Принимаем фамилию
        String txtLastname = getIntent().getStringExtra("context");
    }

    public void back(View v){
        switch (v.getId()) {
            case R.id.back_button:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * Фоновый Async Task для загрузки всех продуктов по HTTP запросу
     * */
    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Перед началом фонового потока Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewActivity.this);
            pDialog.setMessage("Загрузка продуктов. Подождите...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * Получаем все продукт из url
         * */
        protected String doInBackground(String... args) {
            // Будет хранить параметры
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // получаем JSON строк с URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

            Log.d("All Products: ", json.toString());

            try {
                // Получаем SUCCESS тег для проверки статуса ответа сервера
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // продукт найден
                    // Получаем масив из Продуктов
                    food = json.getJSONObject(TAG_NAME);
// добавляем HashList в ArrayList
                    productsList.add(map);
                } else {
                    // продукт не найден
                    // Запускаем Add New Product Activity
                    Intent i = new Intent(getApplicationContext(),
                            NewActivity.class);
                    // Закрытие всех предыдущие activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * После завершения фоновой задачи закрываем прогрес диалог
         * **/
        protected void onPostExecute(String file_url) {
            // закрываем прогресс диалог после получение все продуктов
            pDialog.dismiss();
            // обновляем UI форму в фоновом потоке
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Обновляем распарсенные JSON данные в ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            NewActivity.this, productsList,
                            R.layout.list_item, new String[] {
                            TAG_NAME},
                            new int[] {R.id.nametxt });
                    // обновляем listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}
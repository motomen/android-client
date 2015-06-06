package com.application.yaroslav.searchprogm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.yaroslav.searchprogm.entity.Food;
import com.application.yaroslav.searchprogm.entity.Ingredient;
import com.application.yaroslav.searchprogm.speedometer.Speedometer;
import com.application.yaroslav.searchprogm.util.Constant;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Created by Yaroslav on 02.05.2015.
 */

public class NewActivity extends Activity {

    private String partUrl = Constant.url;

    private Button back;

    private TextView ingredients;

    private TextView nameFood;

    private ImageView photo;

    private Ingredient retryIngredient;

    private TextView kcal;

    private TextView protein;

    private TextView carbs;

    private TextView fats;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_food);
        final Speedometer speedometer = (Speedometer) findViewById(R.id.Speedometer);

        back = (Button) findViewById(R.id.back_button);
        ingredients = (TextView) findViewById(R.id.ingredients);
        nameFood = (TextView) findViewById(R.id.namefood);
        photo = (ImageView) findViewById(R.id.foodView);
        kcal = (TextView) findViewById(R.id.kcal);
        protein = (TextView) findViewById(R.id.protein);
        carbs = (TextView) findViewById(R.id.carbs);
        fats = (TextView) findViewById(R.id.fats);
        Food food = (Food) getIntent().getSerializableExtra("food");

        speedometer.setCurrentSpeed(food.getRating().floatValue());

        kcal.setText(kcal.getText().toString() + food.getKcal().toString());
        nameFood.setText(nameFood.getText().toString() + " " + food.getName());
        String StrIngredient = food.getIngredients();

        prepareIngredientForTextView(StrIngredient);

        byte[] decodedString = Base64.decode(food.getPhoto(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        photo.setImageBitmap(decodedByte);

        protein.setText(food.getProtein().toString());
        fats.setText(food.getFats().toString());
        carbs.setText(food.getCarbs().toString());
    }

    public void back(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private ClickableSpan getClickableSpan(final String word) {
        return new ClickableSpan() {
            private TextPaint ds;

            @Override
            public void onClick(View widget) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        // Set the Accept header
                        HttpHeaders requestHeaders = new HttpHeaders();
                        requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
                        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

                        String url = partUrl + "/api/ingredients/get?name=" + word.trim(); //40907000EAN_8

                        // Create a new Rest Template instance
                        RestTemplate restTemplate = new RestTemplate();

                        // Make the HTTP GET request, marshaling the response from JSON to an array of Events
                        ResponseEntity<Ingredient> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Ingredient.class);
                        Ingredient events = responseEntity.getBody();
                        // Make the HTTP GET request, marshaling the response to a String
                        retryIngredient = events;

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        goNewView();
                    }
                }.execute(null, null, null);

                changeSpanBgColor(widget);
            }

            public void changeSpanBgColor(View widget) {
                updateDrawState(ds);
                widget.invalidate();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                if (this.ds == null) {
                    this.ds = ds;
                }
                ds.setARGB(150, 0, 0, 0);
            }
        };
    }

    public void goNewView() {
        if (retryIngredient != null) {
            Intent intent = new Intent(this, IngredientActivity.class);
            intent.putExtra("ingredient", retryIngredient);
            startActivity(intent);
        }
    }

    private void prepareIngredientForTextView(String val) {
        String Ingredients = val;
        String notUsedString = "";
        String ingredientBefore = "";
        String ingredientAfter = "";
        int start = 0;
        int end = 0;

        if (Ingredients.contains("<span>")) {
            Ingredients = Ingredients.replace("<span>", "");
        }

        if (Ingredients.contains("</span> ")) {
            Ingredients = Ingredients.replace("</span> ", "");
        }

        SpannableString ss = new SpannableString(Ingredients);

        // get all ingredient for String Ingredients
        // maybe symbol ", . :"
        for (String ingredient : Ingredients.split(",")) {
            // if ingredient contains :
            // example chocolate: cacao

            if (ingredient.contains(":") && !ingredient.contains(".")) {
                notUsedString = ingredient.substring(0, ingredient.lastIndexOf(": ") + 2);
                ingredient = ingredient.replace(notUsedString, "");
                start = Ingredients.indexOf(ingredient);
                end = start + ingredient.length();

                ClickableSpan clickSpan = getClickableSpan(ingredient);
                ss.setSpan(clickSpan, start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                continue;
            }

            if (ingredient.contains(".") && ingredient.contains(":")) {
                ingredientBefore = ingredient.substring(0, ingredient.lastIndexOf(".") + 1);
                ingredient = ingredient.replace(ingredientBefore, "");
                notUsedString = ingredient.substring(0, ingredient.lastIndexOf(": ") + 1);

                ingredient = ingredient.replace(notUsedString, "");
                ingredientAfter = ingredient.replace(notUsedString, "");

                start = Ingredients.indexOf(ingredientBefore);
                end = start + ingredientBefore.length();
                ClickableSpan clickSpan = getClickableSpan(ingredientBefore);
                ss.setSpan(clickSpan, start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                start = Ingredients.indexOf(ingredientAfter);
                end = start + ingredientAfter.length();
                clickSpan = getClickableSpan(ingredientAfter);
                ss.setSpan(clickSpan, start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                continue;
            }

            if (ingredient.contains(".") && !ingredient.contains(":")) {
                ingredientBefore = ingredient.substring(0, ingredient.lastIndexOf("."));
                ingredient = ingredientBefore.replace(ingredientBefore, ingredientBefore.substring(1, ingredientBefore.length()));
                start = Ingredients.indexOf(ingredient);
                end = start + ingredient.length();
                ClickableSpan clickSpan = getClickableSpan(ingredient);
                ss.setSpan(clickSpan, start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            }

            start = Ingredients.indexOf(ingredient);
            end = start + ingredient.length();
            ClickableSpan clickSpan = getClickableSpan(ingredient);
            ss.setSpan(clickSpan, start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        ingredients.setText(ss);
        ingredients.setMovementMethod(LinkMovementMethod.getInstance());
    }
}

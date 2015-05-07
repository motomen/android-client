package com.application.yaroslav.searchprogm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

/**
 * Created by Yaroslav on 02.05.2015.
 */

public class NewActivity extends Activity {

    private Button back;
    private TextView ingredients;
    private TextView nameFood;
    private ImageView photo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_food);

        back = (Button) findViewById(R.id.back_button);
        ingredients = (TextView) findViewById(R.id.ingredients);
        nameFood = (TextView) findViewById(R.id.namefood);
        photo = (ImageView) findViewById(R.id.foodView);

        Food food = (Food) getIntent().getSerializableExtra("food");

        nameFood.setText(nameFood.getText().toString() + " " + food.getName());
        String StrIngredient = food.getIngredients();

        prepareIngredientForTextView(StrIngredient);

        byte[] decodedString = Base64.decode(food.getPhoto(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        photo.setImageBitmap(decodedByte);
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

    private ClickableSpan getClickableSpan(final String word) {
        return new ClickableSpan() {
            private TextPaint ds;

            @Override
            public void onClick(View widget) {
                // go new activity aQuery.show(createAddingDialog(word));
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

package com.application.yaroslav.searchprogm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
        if (StrIngredient.contains("<span>")) {
            StrIngredient = StrIngredient.replace("<span>", "");
        }

        if (StrIngredient.contains("</span> ")) {
            StrIngredient = StrIngredient.replace("</span> ", "");
        }

        ingredients.setText(ingredients.getText().toString() + " " + StrIngredient);

        byte[] decodedString = Base64.decode(food.getPhoto(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        photo.setImageBitmap(decodedByte);

//        // выводим прин€тое им€
//        name.setText(name.getText().toString() + " " + txtName);
//
//        // ¬ыводим прин€тую фамилию
//        lastName.setText(lastName.getText().toString() + " " + txtLastname);
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

}

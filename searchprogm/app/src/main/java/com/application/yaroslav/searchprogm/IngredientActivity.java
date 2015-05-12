package com.application.yaroslav.searchprogm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.yaroslav.searchprogm.entity.Ingredient;

/**
 * Created by Yaroslav on 08.05.2015.
 */
public class IngredientActivity extends Activity {

    private TextView name;
    private TextView information;
    private ImageView photo;
    private TextView links;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_ingredient_inf);
        name = (TextView) findViewById(R.id.name_ingredient);
        information = (TextView) findViewById(R.id.information);
        photo = (ImageView) findViewById(R.id.ingredientView);
        links = (TextView) findViewById(R.id.links);
        Ingredient ingredient = (Ingredient) getIntent().getSerializableExtra("ingredient");

        name.setText(name.getText() + " " + ingredient.getNameIngredient());
        information.setText(information.getText() + " " + ingredient.getDescription());

        byte[] decodedString = Base64.decode(ingredient.getPhoto(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        photo.setImageBitmap(decodedByte);

        if (ingredient.getLinkList().size() > 0) {
            for (int i = 0; i < ingredient.getLinkList().size(); i++) {
                links.setText(links.getText() + ingredient.getLinkList().get(i).getAddress() + "\n");
            }
        } else {
            links.setVisibility(View.INVISIBLE);
        }
    }

    public void back(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent intent = new Intent(this, NewActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}

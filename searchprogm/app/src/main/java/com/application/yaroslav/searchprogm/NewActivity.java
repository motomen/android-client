package com.application.yaroslav.searchprogm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * Created by Yaroslav on 02.05.2015.
 */

public class NewActivity extends Activity {

    private Button back;
    private TextView name;
    private TextView lastName;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_food);

        back = (Button) findViewById(R.id.back_button);
        name = (TextView) findViewById(R.id.nametxt);
        lastName = (TextView) findViewById(R.id.lastnametxt);

//        // Принимаем имя
//        String txtName = getIntent().getStringExtra("name");
//
//        // Принимаем фамилию
//        String txtLastname = getIntent().getStringExtra("lastname");

//        // выводим принятое имя
//        name.setText(name.getText().toString() + " " + txtName);
//
//        // Выводим принятую фамилию
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

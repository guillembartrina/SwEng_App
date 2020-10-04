package com.github.bartrina.bootcamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Remove this line of code once I learn about asynchronous operations!
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        name = (EditText) findViewById(R.id.main_ptext_name);
    }

    public void goClicked(View view) {
        Intent intent = new Intent(this, GreetingActivity.class);
        intent.putExtra(GreetingActivity.EXTRA_NAME, name.getText().toString());
        startActivity(intent);
    }

    public void menu_weatherClicked(View view) {
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }
}
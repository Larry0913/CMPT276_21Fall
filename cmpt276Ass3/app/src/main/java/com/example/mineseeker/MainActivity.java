package com.example.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {
        startActivity(new Intent(this, GameActivity.class));
    }

    public void options(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void help(View view) {
        startActivity(new Intent(this, HelpActivity.class));
    }
}
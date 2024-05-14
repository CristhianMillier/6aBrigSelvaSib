package com.example.sib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GaleriaBrig extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria_brig);
    }

    public void regresar(View view){
        Intent nuevo = new Intent(this, Principal.class);
        startActivity(nuevo);
    }
}
package com.example.pruebafirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InicioActivity extends AppCompatActivity {

    TextView txtV_Correo, txtV_Contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        txtV_Correo = (TextView) findViewById(R.id.txtV_Correo);
        txtV_Contra = (TextView) findViewById(R.id.txtV_Contra);

        String correo = getIntent().getStringExtra("correo");
        txtV_Correo.setText("CORREO: " + correo);
        String contra = getIntent().getStringExtra("contra");
        txtV_Contra.setText("CONTRASEÑA: " + contra);

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        txtV_Correo.setText(preferences.getString("correo", ""));
        txtV_Contra.setText(preferences.getString("contra", ""));
    }

    public void Logout(View view) {
        Intent login = new Intent(this, MainActivity.class);
        startActivity(login);
    }

}

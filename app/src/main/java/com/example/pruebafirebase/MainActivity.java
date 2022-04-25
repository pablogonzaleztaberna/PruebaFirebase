package com.example.pruebafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText editTxtCorreo, editTxtContra;
    Button btnAcceder, btnRegistrarse;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        editTxtCorreo = (EditText) findViewById(R.id.editTxtCorreo);
        editTxtContra = (EditText) findViewById(R.id.editTxtContra);
        btnAcceder = (Button) findViewById(R.id.btnAcceder);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUser();
            }
        });

        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        editTxtCorreo.setText(preferences.getString("correo", ""));
        editTxtContra.setText(preferences.getString("contra", ""));
    }

    public void registrarUser() {
        String inputCorreo = editTxtCorreo.getText().toString();
        String inputContra = editTxtContra.getText().toString();

        if (inputCorreo.isEmpty()) {
            Toast.makeText(this, "INGRESA CORREO", Toast.LENGTH_SHORT).show();
        }
        else if (inputContra.isEmpty()) {
            Toast.makeText(this, "INGRESA CONTRASEÑA", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(inputCorreo, inputContra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "REGISTRO CON ÉXITO!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "ERROR EN EL REGISTR0", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


    public void loginUser() {
        String inputCorreo = editTxtCorreo.getText().toString();
        String inputContra = editTxtContra.getText().toString();

        if (inputCorreo.isEmpty()) {
            Toast.makeText(this, "INGRESA CORREO", Toast.LENGTH_SHORT).show();
        } else if (inputContra.isEmpty()) {
            Toast.makeText(this, "INGRESA CONTRASEÑA", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(inputCorreo, inputContra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "LOGIN CON ÉXITO!", Toast.LENGTH_LONG).show();

                        SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferencias.edit();
                        editor.putString("correo", inputCorreo);
                        editor.putString("contra", inputContra);
                        editor.commit();

                        Intent inicio = new Intent(getApplicationContext(), InicioActivity.class);
                        inicio.putExtra("correo", inputCorreo);
                        inicio.putExtra("contra", inputContra);
                        startActivity(inicio);
                    } else {
                        Toast.makeText(getApplicationContext(), "ERROR EN EL LOGIN", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

}
package com.example.ticketteatro.database;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketteatro.R;

public class DBTestActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private EditText etName, etApellido, etEmail, etTelefono, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_test);

        dbHelper = new DBHelper(this);

        etName = findViewById(R.id.etName);
        etApellido = findViewById(R.id.etApellido);
        etEmail = findViewById(R.id.etEmail);
        etTelefono = findViewById(R.id.etTelefono);
        etPassword = findViewById(R.id.etPassword);
        Button btnInsert = findViewById(R.id.btnInsert);

        btnInsert.setOnClickListener(v -> {
            String nombre = etName.getText().toString();
            String apellido = etApellido.getText().toString();
            String email = etEmail.getText().toString();
            String telefono = etTelefono.getText().toString();
            String password = etPassword.getText().toString();

            long id = dbHelper.insertUsuario(nombre, apellido, email, telefono, password);
            if (id > 0) {
                Toast.makeText(this, "Usuario insertado con ID: " + id, Toast.LENGTH_SHORT).show();
                etName.setText("" );
                etApellido.setText("" );
                etEmail.setText("" );
                etTelefono.setText("" );
                etPassword.setText("" );
            } else {
                Toast.makeText(this, "Error al insertar el usuario", Toast.LENGTH_SHORT).show();
            }


        });

    }
}

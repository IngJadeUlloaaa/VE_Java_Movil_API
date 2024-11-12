package com.example.entornovirtualudem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inicializa tu ImageView aquí
        ImageView imageProfile = findViewById(R.id.imageViewHome);

        // Carga la imagen de perfil
        loadProfileImage(imageProfile);

        // Configura la barra de menú personalizada
        setupCustomBottomMenu(R.id.toolbarBottomHome);

    }
}
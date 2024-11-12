package com.example.entornovirtualudem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatButton;

public class UserInfoActivity extends BaseActivity {

    private ImageView imageViewUserInfoPerfil;
    private ImageView imageViewUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        imageViewUserInfoPerfil = findViewById(R.id.imageViewUserInfoPerfil);

        imageViewUserInfo = findViewById(R.id.imageViewUserInfo);

        // Cargar la imagen de perfil
        loadProfileImage(imageViewUserInfoPerfil);
        loadProfileImage(imageViewUserInfo);

        setupCustomBottomMenu(R.id.toolbarBottomUserInfo);

        AppCompatButton buttonActualizar = findViewById(R.id.buttonActualizar);
        buttonActualizar.setOnClickListener(v -> {
            Intent intent = new Intent(UserInfoActivity.this, UpdateDataActivity.class);
            startActivity(intent);
        });
    }
}

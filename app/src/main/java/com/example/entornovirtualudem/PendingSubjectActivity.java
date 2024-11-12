package com.example.entornovirtualudem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class PendingSubjectActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_subject);

        // Inicializa tu ImageView aquí
        ImageView imageProfile = findViewById(R.id.imageViewPendingSubject);

        // Carga la imagen de perfil
        loadProfileImage(imageProfile);

        setupCustomBottomMenu(R.id.toolbarBottomPending_subject);
    }
}
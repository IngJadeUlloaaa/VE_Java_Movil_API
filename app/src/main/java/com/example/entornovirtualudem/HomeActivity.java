package com.example.entornovirtualudem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Recuperar accessToken
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String accessToken = prefs.getString("accessToken", null);

        if (accessToken == null) {
            Toast.makeText(this, "No se encontró el accessToken", Toast.LENGTH_SHORT).show();
            // Redirigir a Login si el token es nulo
            finish();
            return;
        }

        // Configurar navegación del menú
        setupMenuNavigation();
    }

    private void setupMenuNavigation() {
        // Obtener referencias a los botones del menú
        ImageButton btnPendingSubjects = findViewById(R.id.icnPendingSubjects);
        ImageButton btnAudit = findViewById(R.id.icnAudit);
        ImageButton btnHome = findViewById(R.id.icnHome);
        ImageButton btnReportCard = findViewById(R.id.icnReportCard);
        ImageButton btnProfile = findViewById(R.id.icnProfile);

        // Configurar listeners para cada botón
        btnPendingSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(PendingSubjectActivity.class);
            }
        });

        btnAudit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(AuditoryActivity.class);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(HomeActivity.class);
            }
        });

        btnReportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(SubjectReportActivity.class);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(UserInfoActivity.class);
            }
        });
    }

    private void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(HomeActivity.this, targetActivity);
        startActivity(intent);
    }
}

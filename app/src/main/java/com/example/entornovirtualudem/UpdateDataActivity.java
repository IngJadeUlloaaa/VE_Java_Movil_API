package com.example.entornovirtualudem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.io.IOException;
import java.io.InputStream;

public class UpdateDataActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

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
        Intent intent = new Intent(UpdateDataActivity.this, targetActivity);
        startActivity(intent);
    }
}

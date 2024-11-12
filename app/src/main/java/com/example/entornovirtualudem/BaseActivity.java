package com.example.entornovirtualudem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Configura la barra de menú personalizada con ImageButton.
     * Este método se llamará en las actividades que necesiten la barra de menú.
     */
    protected void setupCustomBottomMenu(int toolbarId) {
        Toolbar bottomToolbar = findViewById(toolbarId);

        if (bottomToolbar != null) {
            findViewById(R.id.action_profile).setOnClickListener(v -> {
                Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, UserInfoActivity.class));
            });

            findViewById(R.id.action_pending_subject).setOnClickListener(v -> {
                Toast.makeText(this, "Asignatura Pendiente", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, PendingSubjectActivity.class));
            });

            findViewById(R.id.action_subject_report).setOnClickListener(v -> {
                Toast.makeText(this, "Boleta de Asignatura", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, SubjectReportActivity.class));
            });

            findViewById(R.id.action_audit).setOnClickListener(v -> {
                Toast.makeText(this, "Auditoría", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AuditoryActivity.class));
            });

            findViewById(R.id.action_logout).setOnClickListener(v -> {
                Toast.makeText(this, "Saliendo...", Toast.LENGTH_SHORT).show();
                logoutUser();
            });

            findViewById(R.id.action_home).setOnClickListener(v ->  {
                Toast.makeText(this, "inicio", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,HomeActivity.class));
            });
        }
    }

    /**
     * Carga la imagen de perfil en uno o más ImageViews.
     */
    protected void loadProfileImage(ImageView... imageViews) {
        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        String imageUriString = preferences.getString("profile_image_uri", null);

        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            for (ImageView imageView : imageViews) {
                imageView.setImageURI(imageUri);
            }
        }
    }

    /**
     * Función de cerrar sesión compartida por todas las actividades.
     */
    private void logoutUser() {
        Toast.makeText(this, "Saliendo...", Toast.LENGTH_SHORT).show();
        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(this, LoginActivity.class));
        finish(); // Cerrar la actividad actual
    }
}

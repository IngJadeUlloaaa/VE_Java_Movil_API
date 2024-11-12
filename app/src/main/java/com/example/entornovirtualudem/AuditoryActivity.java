package com.example.entornovirtualudem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

public class AuditoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditory);

        // Inicializa tu ImageView aquí
        ImageView imageProfile = findViewById(R.id.imageViewAuditory);

        // Carga la imagen de perfil
        loadProfileImage(imageProfile);

        // Configura la barra de menú personalizada
        setupCustomBottomMenu(R.id.toolbarBottomAuditory);

        TextView auditoryInfo = findViewById(R.id.auditoryTextView);
        String auditoryText = getString(R.string.auditory);  // Obtener el texto del string resource
        auditoryInfo.setText(Html.fromHtml(auditoryText));

    }
}
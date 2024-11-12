package com.example.entornovirtualudem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SubjectReportActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_report);

        // Inicializa tu ImageView aquí
        ImageView imageProfile = findViewById(R.id.imageViewSubjectReport);

        // Carga la imagen de perfil
        loadProfileImage(imageProfile);

        // Configura la barra de menú personalizada
        setupCustomBottomMenu(R.id.toolbarBottomSubjectReport);

        TextView correoTextView = findViewById(R.id.textViewCorreo);

        // Supongamos que obtienes el correo del usuario logueado
        String correoUsuario = obtenerCorreoUsuario(); // Reemplázalo por tu método real de obtener el correo

        // Establece el texto con el correo del usuario
         correoTextView.setText("Correo institucional: " + correoUsuario);
    }

    private String obtenerCorreoUsuario() {
        // Aquí deberías obtener el correo desde el login o el backend.
        // Por ahora, puedes devolver un correo de ejemplo:
        return "ejemplo@udem.edu.ni";  // Cámbialo por el correo real del usuario
    }

}
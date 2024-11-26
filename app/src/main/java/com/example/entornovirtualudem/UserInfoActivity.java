package com.example.entornovirtualudem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserInfoActivity extends AppCompatActivity {

    private TextView textViewCareer, textViewShift, textViewAddress, textViewPhone, textViewFullName, textViewCardNumber, textViewEmailAddress;
    private AppCompatButton updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Inicializar los TextViews
        textViewCareer = findViewById(R.id.textViewCareer);
        textViewShift = findViewById(R.id.textViewShift);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewFullName = findViewById(R.id.textViewFullName);
        textViewCardNumber = findViewById(R.id.textViewCardNumber);
        textViewEmailAddress = findViewById(R.id.textViewEmailAddress);

        updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(v ->
                Toast.makeText(UserInfoActivity.this, "Update button clicked!", Toast.LENGTH_SHORT).show()
        );

        // Habilitar StrictMode para pruebas rápidas (usa AsyncTask o Retrofit en producción)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Obtener información del usuario
        String apiUrl = "http://192.168.137.222:3000/api/profile";
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String accessToken = prefs.getString("accessToken", null);

        if (accessToken != null) {
            getUserInfo(apiUrl, accessToken);
        } else {
            Toast.makeText(this, "Token no encontrado. Redirigiendo a inicio de sesión...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        setupMenuNavigation();
    }

    private void getUserInfo(String apiUrl, String token) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(15000);

            int status = urlConnection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Aquí intentamos determinar si la respuesta es un JSONArray o un JSONObject
                String responseStr = response.toString();
                if (responseStr.startsWith("[")) {
                    // Si la respuesta empieza con un corchete, probablemente es un JSONArray
                    JSONArray jsonArray = new JSONArray(responseStr);
                    JSONObject userInfo = jsonArray.getJSONObject(0); // Asumimos que tomamos el primer objeto del array
                    updateUI(userInfo);
                } else {
                    // Si no es un array, asumimos que es un JSONObject
                    JSONObject jsonResponse = new JSONObject(responseStr);
                    updateUI(jsonResponse);
                }

            } else {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    errorResponse.append(errorLine);
                }
                errorReader.close();
                Toast.makeText(this, "Error: " + errorResponse.toString(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(JSONObject userInfo) {
        try {
            textViewCareer.setText(userInfo.optString("careerName"));
            textViewShift.setText(userInfo.optString("shiftCareer"));
            textViewAddress.setText(userInfo.optString("addressStudents"));
            textViewPhone.setText(userInfo.optString("phone"));
            textViewFullName.setText(userInfo.optString("fullName"));
            textViewCardNumber.setText(userInfo.optString("code")); // Aquí debería ir "code" si ese es el número de tarjeta
            textViewEmailAddress.setText(userInfo.optString("email"));

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al actualizar la interfaz", Toast.LENGTH_SHORT).show();
        }
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
        Intent intent = new Intent(UserInfoActivity.this, targetActivity);
        startActivity(intent);
    }
}

package com.example.entornovirtualudem;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LoginActivity extends AppCompatActivity {

    private EditText inputCarnet, inputPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar vistas
        inputCarnet = findViewById(R.id.inputCode);
        inputPassword = findViewById(R.id.inputPasswd);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Deshabilitar políticas de seguridad para simplificar
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String carnet = inputCarnet.getText().toString();
                String password = inputPassword.getText().toString();
                loginUser(carnet, password);
            }
        });
    }

    private void loginUser(String code, String passwd) {
        try {
            URL url = new URL("http://192.168.0.9:3000/api/auth/users/login");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // Crear JSON con los datos ingresados por el usuario
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("code", code);  // Carnet ingresado
            jsonParam.put("passwd", passwd);  // Contraseña ingresada

            Log.d("LoginActivity", "Enviando solicitud de login: " + jsonParam.toString());
            int responseCode = 0;
            Log.d("LoginActivity", "Código de respuesta: " + responseCode);
            String response = null;
            Log.d("LoginActivity", "Respuesta del servidor: " + response);

            // Enviar los datos al servidor
            OutputStream os = urlConnection.getOutputStream();
            os.write(jsonParam.toString().getBytes("UTF-8"));
            os.close();

            // Verificar la respuesta del servidor
            responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer respuesta del servidor
                InputStream is = urlConnection.getInputStream();
                Scanner scanner = new Scanner(is);
                scanner.useDelimiter("\\A");
                response = scanner.hasNext() ? scanner.next() : "";
                scanner.close();

                // Parsear la respuesta JSON
                JSONObject jsonResponse = new JSONObject(response);
                String accessToken = jsonResponse.getString("accessToken");
                String refreshToken = jsonResponse.getString("refreshToken");

                // Guardar tokens y redirigir al HomeActivity
                saveTokensAndRedirect(accessToken, refreshToken);
            } else {
                // Mostrar mensaje de error si las credenciales no son válidas
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveTokensAndRedirect(String accessToken, String refreshToken) {
        // Guardar tokens de forma segura (ejemplo usando SharedPreferences)
        getSharedPreferences("user_prefs", MODE_PRIVATE)
                .edit()
                .putString("accessToken", accessToken)
                .putString("refreshToken", refreshToken)
                .apply();

        // Redirigir al HomeActivity
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
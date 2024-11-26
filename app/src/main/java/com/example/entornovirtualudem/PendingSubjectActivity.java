package com.example.entornovirtualudem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class PendingSubjectActivity extends BaseActivity {
    private TableLayout tablePendingSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_subject);

        // Obtener referencia al TableLayout
        tablePendingSubjects = findViewById(R.id.tablePendingSubjects);

        // Obtener el token de acceso almacenado
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String accessToken = prefs.getString("accessToken", null);

        if (accessToken != null) {
            fetchClasses(accessToken);
        } else {
            Toast.makeText(this, "No se encontró el token de acceso. Inicia sesión nuevamente.", Toast.LENGTH_SHORT).show();
        }

        // Configurar navegación del menú
        setupMenuNavigation();
    }

    private void fetchClasses(String token) {
        new Thread(() -> {
            try {
                URL url = new URL("http://192.168.137.222:3000/api/pendingSubjects");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Bearer " + token);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream is = connection.getInputStream();
                    Scanner scanner = new Scanner(is);
                    scanner.useDelimiter("\\A");
                    String response = scanner.hasNext() ? scanner.next() : "";
                    scanner.close();

                    runOnUiThread(() -> displayClasses(response));
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Error al obtener las clases: " + responseCode, Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void displayClasses(String jsonResponse) {
        try {
            JSONArray classesArray = new JSONArray(jsonResponse);

            // Agregar encabezados a la tabla
            TableRow headerRow = new TableRow(this);
            addCellToRow(headerRow, "Code", true);
            addCellToRow(headerRow, "Subject", true);
            addCellToRow(headerRow, "Grade", true);
            addCellToRow(headerRow, "Offered", true);
            tablePendingSubjects.addView(headerRow);

            // Poblar las filas con los datos
            for (int i = 0; i < classesArray.length(); i++) {
                JSONObject classObject = classesArray.getJSONObject(i);
                String code = classObject.getString("codeClasses");
                String name = classObject.getString("classesName");
                String grade = classObject.getString("gradeClasses");
                String note = classObject.getString("offered");

                TableRow row = new TableRow(this);
                addCellToRow(row, code, false);
                addCellToRow(row, name, false);
                addCellToRow(row, grade, false);
                addCellToRow(row, note, false);

                tablePendingSubjects.addView(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
        }
    }

    private void addCellToRow(TableRow row, String text, boolean isHeader) {
        TextView cell = new TextView(this);
        cell.setText(text);
        cell.setPadding(16, 16, 16, 16);
        cell.setTextSize(isHeader ? 22 : 18);
        cell.setBackgroundResource(isHeader ? R.drawable.table_header_bg : R.drawable.table_cell_bg);
        row.addView(cell);
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
        Intent intent = new Intent(PendingSubjectActivity.this, targetActivity);
        startActivity(intent);
    }
}
package com.example.entornovirtualudem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SubjectReportActivity extends BaseActivity {
    private TableLayout tableSubjectReportCard;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_report);

        // Obtener referencia al TableLayout
        tableSubjectReportCard = findViewById(R.id.tableSubjectReportCard);

        // Obtener el token de acceso almacenado
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String accessToken = prefs.getString("accessToken", null);

        if (accessToken != null) {
            fetchClasses(accessToken);
        } else {
            Toast.makeText(this, "No se encontró el token de acceso. Inicia sesión nuevamente.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchClasses(String token) {
        new Thread(() -> {
            try {
                URL url = new URL("http://192.168.23.39:3000/api/211572/subjectReportCard");
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
                    runOnUiThread(() -> Toast.makeText(this, "Error al obtener la boleta de asignatura: " + responseCode, Toast.LENGTH_SHORT).show());
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
            addCellToRow(headerRow, "Group", true);
            addCellToRow(headerRow, "Subject", true);
            addCellToRow(headerRow, "Day", true);
            addCellToRow(headerRow, "Time", true);
            addCellToRow(headerRow, "Class", true);
            addCellToRow(headerRow, "Classroom", true);
            tableSubjectReportCard.addView(headerRow);

            // Poblar las filas con los datos
            for (int i = 0; i < classesArray.length(); i++) {
                JSONObject classObject = classesArray.getJSONObject(i);
                String code = classObject.getString("codeClasses");
                String group = classObject.getString("classGroup");
                String subject = classObject.getString("classesName");
                String day = classObject.getString("classDay");
                String time = classObject.getString("timeClass");
                String aula = classObject.getString("classroomClass");
                String classroom = classObject.getString("classroom");

                TableRow row = new TableRow(this);
                addCellToRow(row, code, false);
                addCellToRow(row, group, false);
                addCellToRow(row, subject, false);
                addCellToRow(row, day, false);
                addCellToRow(row, time, false);
                addCellToRow(row, aula, false);
                addCellToRow(row, classroom, false);

                tableSubjectReportCard.addView(row);
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
}
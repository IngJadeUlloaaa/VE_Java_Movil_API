package com.example.entornovirtualudem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.io.IOException;
import java.io.InputStream;

public class UpdateDataActivity extends BaseActivity {

    private ImageView imageViewUpdateData;
    private ActivityResultLauncher<Intent> pickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        //encontrar id de la imagen
        imageViewUpdateData = findViewById(R.id.imageViewProfile);

        // Cargar la imagen de perfil
        loadProfileImage(imageViewUpdateData);

        setupCustomBottomMenu(R.id.toolbarBottomUpdateData);

        // Configura el ActivityResultLauncher para seleccionar una imagen de la galería
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri imageUri = data.getData();
                            handleImageSelection(imageUri);
                        }
                    }
                }
        );

        Button selectedPhotoButton = findViewById(R.id.buttonChangePhoto);
        selectedPhotoButton.setOnClickListener(v -> openGallery());

        Button updateButton = findViewById(R.id.buttonActualizar);
        updateButton.setOnClickListener(v -> updateProfile());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }

    private void handleImageSelection(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // Actualizar ImageView con la imagen seleccionada
            imageViewUpdateData.setImageBitmap(bitmap);

            // Guarda la URI de la imagen en SharedPreferences
            SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("profile_image_uri", imageUri.toString());
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateProfile() {
        // Lógica de actualización del perfil
        Toast.makeText(this, "Datos actualizados", Toast.LENGTH_SHORT).show();

        // Refrescar UserInfoActivity con la imagen actualizada
        Intent intent = new Intent(this, UserInfoActivity.class);
        startActivity(intent);
    }
}

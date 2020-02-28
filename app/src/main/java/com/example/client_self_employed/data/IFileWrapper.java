package com.example.client_self_employed.data;

import android.net.Uri;

import java.io.File;

/**
 * Интерфейс для работы с новыми файлами
 */
public interface IFileWrapper {
    Uri createNewImageURI(File imageFile);

    File createImageFile(String imageName);
}

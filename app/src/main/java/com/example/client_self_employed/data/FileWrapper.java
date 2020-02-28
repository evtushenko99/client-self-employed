package com.example.client_self_employed.data;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * Класс, создающий Uri для новой фотографии клиента
 */
public class FileWrapper implements IFileWrapper {
    private final Context mContext;
    private final File mStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

    public FileWrapper(Context context) {
        mContext = context;
    }

    public Uri createNewImageURI(String imageName) {
        File photoFile;
        photoFile = createImageFile(imageName);
        if (photoFile != null) {
            String authorities = mContext.getPackageName() + ".provider";
            return FileProvider.getUriForFile(mContext, authorities, photoFile);
        }
        return null;
    }

    @Override
    public Uri createNewImageURI(File imageFile) {
        if (imageFile != null) {
            String authorities = mContext.getPackageName() + ".provider";
            return FileProvider.getUriForFile(mContext, authorities, imageFile);
        }
        return null;
    }

    @Override
    public File createImageFile(String imageName) {
        if (imageName != null) {
            return new File(mStorageDirectory, imageName + ".jpg");
        } else {
            return null;
        }
    }
}

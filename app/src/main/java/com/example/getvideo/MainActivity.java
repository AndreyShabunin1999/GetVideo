package com.example.getvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String downloadLink = "";
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        runtimePermission();
        getClip();
    }

    public void runtimePermission()
    {
        Dexter.withContext(this).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    public void onClickDownload(View v) throws IOException {

        Uri uri = Uri.parse(downloadLink);
        DownloadManager downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        request.setDestinationInExternalFilesDir( this, filePath,spinner.getSelectedItem().toString() +".mp4");
        downloadManager.enqueue(request);
        Toast.makeText(getBaseContext(), "Загрузка началась", Toast.LENGTH_SHORT).show();
    }


    /*
        Заполнение выпадающего списка
        В список заносятся названия песен
        setOnItemSelectedListener - данная функция используется при выборе в списке какого-либо файла
        она заносит ссылку для скачивания аудио файла в String переменную, для дальнейшего использования, так же сохраняет в переменную N позицию выбранного элемента
     */

    private void getClip(){

                switch (spinner.getSelectedItemPosition()){
                    case 0:
                        downloadLink="https://drive.google.com/uc?export=download&id=1IacKuFhSPRTxhNuHW6dh4zNv8o9w_Bu6";
                        break;
                    case 1:
                        downloadLink = "https://drive.google.com/uc?export=download&id=1BMvmwskGgh7CL_43cU7FeyA9g76cLJ-n";
                        break;
                    case 2:
                        downloadLink = "https://drive.google.com/uc?export=download&id=13CQnj_UCDYVGl4Af0Z7scdxsEqReO-hk";
                        break;
                    case 3:
                        downloadLink = "https://drive.google.com/uc?export=download&id=1Qr9OSorieeD1gYRZAyTRCSFiNny4Tudu";
                        break;
                    default:
                        downloadLink="";
                        Toast.makeText(getBaseContext(), "Вы ничего не выбрали", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(getBaseContext(), "Выбрано - " + spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

            }

}
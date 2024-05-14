package com.example.sib;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.widget.Toast;

import java.io.File;

public class HimnoBrig extends AppCompatActivity {

    private static final int  PERMISSION_STOREGE_CODE = 1000;
    private Button descHimno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_himno_brig);

        descHimno = (Button) findViewById(R.id.pdfHimno);
        descHimno.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permissions, PERMISSION_STOREGE_CODE);
                } else{
                    descargarHimno();
                }
            }
        });
    }

    public void regresar(View view){
        Intent nuevo = new Intent(this, Principal.class);
        startActivity(nuevo);
    }

    private void descargarHimno(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            network = cm.getActiveNetwork();
        }
        NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
        boolean isConnected = capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Himno-de-la-6a-Brigada-de-Selva.pdf");
        if (file.exists()){
            Toast.makeText(this, "El archivo ya se encuentra en su Dispositivo", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = FileProvider.getUriForFile(this, "com.example.myapp.fileprovider", file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, "Himno de la Sexta Brigada de Selva"));
        } else {
            if (isConnected){
                String url = "https://drive.google.com/uc?export=download&id=1oGc1GzxfbX18dJYUGAh7pdfrYVkAm4Lx";
                Toast.makeText(this, "Descargando PDF.", Toast.LENGTH_LONG).show();
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(url);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle("Himno de la Sexta Brigada de Selva");
                request.setDescription("Por favor, espere un momento...");

                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Himno-de-la-6a-Brigada-de-Selva.pdf");
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                long downloadId = downloadManager.enqueue(request);
                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                        if (id == downloadId) {
                            Uri result = downloadManager.getUriForDownloadedFile(downloadId);
                            // Si no es nulo, abrir el PDF con un visor externo o una librería
                            if (result != null) {
                                Intent viewIntent = new Intent(Intent.ACTION_VIEW);
                                viewIntent.setDataAndType(result, "application/pdf");
                                viewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                startActivity(viewIntent);
                            }
                        }
                    }
                }, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            } else{
                Toast.makeText(this, "Usted no cuenta con acceso a Internet!!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
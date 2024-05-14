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
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class HistoriaBrig extends AppCompatActivity {
    private TextView texto2022, texto2021, texto2020, texto2017, texto2009, fecha1995, texto1981, texto1941;
    private static final int  PERMISSION_STOREGE_CODE = 1000;
    private Button descHistoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_brig);

        texto2022 = (TextView) findViewById(R.id.fecha2022);
        texto2022.setText(Html.fromHtml("<b>2022:</b> La sexta Brigada de Selva en coordinación con el MINSA trabajo en forma conjunta para completar las dosis contra el COVID-19; así mismo a través del CIRD sus unidades se desplazaron a diferentes zonas de nuestro sector de responsabilidad, ante emergencia por desborde del Río Utcubamba (distrito el Milagro, caserío el Valor, sector Huaquillo, etc.), e incendios forestales (distrito de Shipasbamba)."));

        texto2021 = (TextView) findViewById(R.id.fecha2021);
        texto2021.setText(Html.fromHtml("<b>2021:</b> LA 6a BRIGADA DE SELVA participó en forma activa todo su sector de responsabilidad como primera línea de respuesta rápida ante el TERREMOTO DE 7.5 DE MAGNITUD producido en el departamento de AMAZONAS."));

        texto2020 = (TextView) findViewById(R.id.fecha2020);
        texto2020.setText(Html.fromHtml("<b>2020:</b> LA 6a BRIGADA DE SELVA participó en forma activa todo su sector de responsabilidad el estado de emergencia decretado por el estado para afrontar el COVID-19 – coordinando en el programa OPERACIÓN TAYTA SAN IGNACIO (CAJAMARCA) Y OPERACIÓN TAYTA AMAZONAS con las autoridades de la localidad."));

        texto2017 = (TextView) findViewById(R.id.fecha2017);
        texto2017.setText(Html.fromHtml("<b>2017:</b> LA 6a BRIGADA DE SELVA participó en el FENÓMENO DEL NIÑO apoyando a la región militar del norte (PIURA)."));

        texto2009 = (TextView) findViewById(R.id.fecha2009);
        texto2009.setText(Html.fromHtml("<b>2009:</b> LA 6a BRIGADA DE SELVA participó en apoyo a la PNP para reforzar y evitar un conflicto social en la provincia de BAGUA y en el distrito de IMAZA después del acontecimiento denominado el “BAGUAZO”."));

        fecha1995 = (TextView) findViewById(R.id.fecha1995);
        fecha1995.setText(Html.fromHtml("<b>1995:</b> CONFLICTO DEL ALTO CENEPA SEXTA REGIÓN MILITAR, asignándole como jurisdicción administrativa territorial el Departamento de Amazonas y las provincias de San Ignacio y Jaén del Departamento de Cajamarca."));

        texto1981 = (TextView) findViewById(R.id.fecha1981);
        texto1981.setText(Html.fromHtml("<b>1981:</b> Conflicto de la Cordillera de Cóndor en 1981 el BIS CALLAO N° 25 ubicado en la localidad de Teniente Pinglo, era la única unidad del Ejército encargada del Sector denominado Cordillera del Cóndor."));

        texto1941 = (TextView) findViewById(R.id.fecha1941);
        texto1941.setText(Html.fromHtml("<b>1941:</b> Destacamento “CHINCHIPE” estuvo al Comando de un Teniente Coronel que con una COMPAÑÍA DE FUSILEROS “CAZADORES”, establecida en SAN IGNACIO, recibió la responsabilidad de defender los Sectores Gramalotal, Namballe y Chimara con MISIÓN de IMPEDIR Y/O RECHAZAR LAS INCURSIONES ECUATORINAS."));

        descHistoria = (Button) findViewById(R.id.pdfHistoria);
        descHistoria.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permissions, PERMISSION_STOREGE_CODE);
                } else{
                    descargarHistoria();
                }
            }
        });
    }

    public void regresar(View view){
        Intent nuevo = new Intent(this, Principal.class);
        startActivity(nuevo);
    }

    private void descargarHistoria(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            network = cm.getActiveNetwork();
        }
        NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
        boolean isConnected = capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Historia-de-la-6a-Brigada-de-Selva.pdf");
        if (file.exists()){
            Toast.makeText(this, "El archivo ya se encuentra en su Dispositivo", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = FileProvider.getUriForFile(this, "com.example.myapp.fileprovider", file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, "Historia de la Sexta Brigada de Selva"));
        } else{
            if (isConnected){
                String url = "https://drive.google.com/uc?export=download&id=1oEFOrPTmpaT6C93zy4WQYlmq5l0LgiZW";
                Toast.makeText(this, "Descargando PDF.", Toast.LENGTH_LONG).show();
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(url);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle("Historia de la Sexta Brigada de Selva");
                request.setDescription("Por favor, espere un momento...");

                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Historia-de-la-6a-Brigada-de-Selva.pdf");
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
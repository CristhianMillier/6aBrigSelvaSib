package com.example.sib;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView primero = findViewById(R.id.primeroImg);
        ImageView segundo = findViewById(R.id.segundoImg);
        ImageView tercero = findViewById(R.id.terceroImg);

        String url1 = "https://docs.google.com/uc?id=1t3U_NBO7TrVz9GLkezywtt4MeamObdC5";
        new imagenInternet(primero, 1).execute(url1);

        String url2 = "https://docs.google.com/uc?id=1Ro4-c8etIme_G-AB7gJe4o8KfdeXllH9";
        new imagenInternet(segundo, 2).execute(url2);

        String url3 = "https://docs.google.com/uc?id=1Ro4-c8etIme_G-AB7gJe4o8KfdeXllH9";
        new imagenInternet(tercero, 3).execute(url3);
    }

    public void ingresar(View view){
        Intent nuevo = new Intent(this, Principal.class);
        startActivity(nuevo);
    }
    public void url(View view){
        Uri url = Uri.parse("https://www.youtube.com/watch?v=WjlH-QgnDE8&ab_channel=SEXTABRIGADADESELVA");
        Intent nuevo = new Intent(Intent.ACTION_VIEW, url);
        startActivity(nuevo);
    }

    static class imagenInternet extends AsyncTask<String, Void, Bitmap> {
        private WeakReference<ImageView> imageVie;
        private int option;

        public imagenInternet(ImageView img, int op){
            imageVie = new WeakReference<>(img);
            option = op;
        }

        protected void onPreExecute(){
            imageVie.get().setImageBitmap(null);
        }

        protected Bitmap doInBackground(String... urls){
            String ruta = urls[0];
            Bitmap bitmap = null;

            try {
                InputStream in = new URL(ruta).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e){
                Log.d("EJEMPLO APP", " Error: No ha sido posible descargar la imagen");
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap resultado){
            if (resultado == null){
                if (option == 1){
                    imageVie.get().setImageResource(R.drawable.whatsapp_image_2023_04_19_at_2_45_35_pm);
                }
                if (option == 3){
                    imageVie.get().setImageResource(R.drawable.logeo);
                }
                if (option == 2){
                    imageVie.get().setImageResource(R.drawable.comandante_general_de_la_6a_brig_sva);
                }
            } else{
                imageVie.get().setImageBitmap(resultado);
            }
        }
    }
}
package com.example.miapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private EditText editTextPhone;
    Button btnNavegador;
    Button btnMapas;
    ImageButton btnLamar;
    Button btnMail;
    private CircleImageView mcircleImagePedio;
    private final int TEL_COD = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNavegador = findViewById(R.id.btnNavegar);
        btnMapas = findViewById(R.id.btnMapas);
        btnLamar = findViewById(R.id.imageButtonPhone);
        btnMail = findViewById(R.id.btnMail);
        editTextPhone=(EditText) findViewById(R.id.editTextPhone);
        mcircleImagePedio = findViewById(R.id.circleImagePedio);

        btnLamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = editTextPhone.getText().toString();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, TEL_COD);
                }else{
                    OlderVersions(phoneNumber);
                }
            }

            private void OlderVersions(String phoneNumber) {
                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber));
                int result = checkCallingOrSelfPermission(Manifest.permission.CALL_PHONE);

                if(result == PackageManager.PERMISSION_GRANTED){
                    startActivity(intentCall);}
                else{Toast.makeText(MainActivity.this, "Acceso no Autorizado", Toast.LENGTH_LONG).show();}
            }
        });

        mcircleImagePedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EnviarSms.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case TEL_COD:
                String permisos = permissions[0];
                int result = grantResults[0];
                if (permisos.equals(Manifest.permission.CALL_PHONE)){
                    if (result == PackageManager.PERMISSION_GRANTED){
                        String phoneNumber = editTextPhone.getText().toString();
                        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) return;
                        startActivity(intentCall);

                    }
                    else{
                        Toast.makeText(MainActivity.this, "Acceso no autorizado", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }

    }

    private boolean CheckPermission(String permission){
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    public void AbrirNavegador(View vista){
            Intent intentWeb = new Intent(Intent.ACTION_VIEW);
            intentWeb.setData(Uri.parse("https://www.youtube.com/watch?v=DDWKuo3gXMQ&list=RDMMDDWKuo3gXMQ&start_radio=1"));

            if(intentWeb.resolveActivity(getPackageManager())!=null){
                startActivity(intentWeb);
            }
    }

    public void AbrirMapa(View vista){
        Intent intentMaps = new Intent(Intent.ACTION_VIEW);
        intentMaps.setData(Uri.parse("geo:-12.0547615, -77.090251"));
        startActivity(intentMaps);
    }

    public void AbrirMail(View vista){
     try{
        Intent intentMail = new Intent(Intent.ACTION_SEND);
        intentMail.setData(Uri.parse("mailto"));
        intentMail.setType("plain/text");
        intentMail.putExtra(Intent.EXTRA_EMAIL,"antonio_289_43@hotmail.com");
        intentMail.putExtra(Intent.EXTRA_SUBJECT, "PRUEBA INTENT");
        intentMail.putExtra(Intent.EXTRA_TEXT, "CONTENIDO DEL TEXTO");
        startActivity(intentMail);
    } catch (Exception ex){
         Log.e("intenImplicito","prueba enviar email", ex);
     }

    }
}

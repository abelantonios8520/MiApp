package com.example.miapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnviarSms extends AppCompatActivity {
    EditText etMensaje, etcel;
    Button btnEnviar, btnW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_sms);

        etMensaje = findViewById(R.id.editTextPersonName);
        etcel = findViewById(R.id.editTextPersonName2);
        btnEnviar = findViewById(R.id.btnSms);
        btnW = findViewById(R.id.btnWhatsApp);

        if (ActivityCompat.checkSelfPermission(EnviarSms.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(EnviarSms.this, new String[]{Manifest.permission.SEND_SMS},1);
        }

        btnEnviar.setOnClickListener((v)-> {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(etcel.getText().toString(), null, etMensaje.getText().toString(), null, null);

            Toast.makeText(EnviarSms.this, "Mensaje Enviado", Toast.LENGTH_LONG).show();
        });

        btnW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(etcel.getText().toString().isEmpty()) {
                 Intent sendIntent = new Intent();
                 sendIntent.setAction(Intent.ACTION_SEND);
                 sendIntent.putExtra(Intent.EXTRA_TEXT, etMensaje.getText().toString());
                 sendIntent.setType("text/plain");
                 sendIntent.setPackage("com.whatsapp");
                 startActivity(sendIntent);

             }else{
                 Intent sendIntent = new Intent();
                 sendIntent.setAction(Intent.ACTION_VIEW);
                 String uri = "whatsapp://send?phone="+etcel.getText().toString()+"&text="+etMensaje.getText().toString();
                 sendIntent.setData(Uri.parse(uri));
                 startActivity(sendIntent);
             }
            }
        });
    }
}
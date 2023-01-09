package com.dennis.sceneformanim;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    //SE DECLARA BOTONES DE MENU PRINCIPAL
    Button bdomesticos, bsalvajes,bvocales,bfrutas,bnumeros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // SE IMPORTA BOTONES DEL LAYOUT
        bdomesticos = (Button)findViewById(R.id.domesticos);
        bsalvajes = (Button)findViewById(R.id.asalvaje);
        bvocales = (Button)findViewById(R.id.ivocales);
        bfrutas = (Button)findViewById(R.id.iFrutas);
        bnumeros=(Button)findViewById(R.id.inumeros);

        // SE DECLARA LOS INTENT
        Intent intentDo = new Intent(this, dActivity.class);
        Intent intentSa = new Intent(this, SalvajesA.class);
        Intent intentVo = new Intent(this, VocalesA.class);
        Intent intentFr = new Intent(this, FrutasA.class);
        Intent intentNu = new Intent(this, NumerosA.class);


        //COMIENZA A ESCUCHAR
        bdomesticos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentDo);
            }
        });
        bsalvajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentSa);
            }
        });

        bvocales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentVo);
            }
        });
        bfrutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentFr);
            }
        });
        bnumeros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentNu);
            }
        });
    }
    public void onFragmentInteraction(Uri uri){

    }

}
package com.dennis.sceneformanim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.core.Session;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.animation.ModelAnimator;
import com.google.ar.sceneform.rendering.AnimationData;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.ArrayList;
import java.util.List;
//*************************************************************************
/*import com.google.ar.core.Pose;
import com.google.ar.core.Session;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;*/
//*************************************************************************

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
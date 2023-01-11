package com.dennis.sceneformanim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

public class FrutasA extends AppCompatActivity {
    //Variable
    private ArFragment arFragment;
    private AnchorNode anchorNode;
    private ModelAnimator animator;
    private int nextAnimation;
    private FloatingActionButton btn_anim;
    private ModelRenderable tigre, punto, manzana, uva, melon, pera, pina, sandia, banana;
    private TransformableNode transformableNode;

    private int clickNo = 0;
    private int Status1 = 0;
    private String information = "";
    private String choose = "";
    private Boolean empyAnimation = false;
    private  Boolean pushButton = false;

    Handler handler = new Handler();

    //DECLARAR AUDIOS
    MediaPlayer  audioi, audiomanzana, audiopera, audiobanana, audiouva, audiosandia, audiomelon, audiopina;

    //*************************************************************
    private List<AnchorNode> anchorNodeList = new ArrayList<>();
    private AnchorNode currentSelectedAnchorNode = null;
    //*************************************************************
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            pushButton=false;
            audioi.stop();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frutas);
        // DECLARA BOTONES DE CAMBIO DE OBJETO
        ImageButton Pera = (ImageButton) findViewById(R.id.id_Pera);
        ImageButton Manzana = (ImageButton) findViewById(R.id.id_Manzana);
        ImageButton Banana = (ImageButton) findViewById(R.id.id_Banana);
        ImageButton Uva = (ImageButton) findViewById(R.id.id_Uva);
        ImageButton Sandia = (ImageButton) findViewById(R.id.id_Sandia);
        ImageButton Melon = (ImageButton) findViewById(R.id.id_Melon);
        ImageButton Pina = (ImageButton) findViewById(R.id.id_Piña);
        ImageButton informacion = (ImageButton) findViewById(R.id.id_informacion);

        //INICIALIZA AUDIOS

        audiomanzana = MediaPlayer.create(this,R.raw.famanzana);
        audiopera = MediaPlayer.create(this,R.raw.fapera);
        audiobanana = MediaPlayer.create(this,R.raw.fabanana);
        audiouva = MediaPlayer.create(this,R.raw.fauva);
        audiosandia = MediaPlayer.create(this,R.raw.fasandia);
        audiomelon = MediaPlayer.create(this,R.raw.famelon);
        audiopina = MediaPlayer.create(this,R.raw.fapina);
        audioi = MediaPlayer.create(this,R.raw.naintro);

        //REPRODUCIR AUDIO DE INTRO
        audioi.start();
        choose = "audioi";

        setupModel();// INICIALIZA LOS OBJETOS

        ///////////////////////////////////////////////////////////////////////////
        ///////////////////////  CREATE ANCHOR, TAP AND FRAME    ////////////////////////////////
        //////////////////////////////////////////////////////////////////////////

        arFragment = (ArFragment)getSupportFragmentManager()
                .findFragmentById(R.id.sceneform_fragment);

        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                if(tigre ==null)
                    return;
                //CREA LA ANCLA
                clickNo++;
                Anchor anchor = hitResult.createAnchor();

                if(clickNo==1) {
                    if (anchorNode == null) //SI LA ANIMACIÓN NO SE COLOCA EN EL PLANO
                    {
                        anchorNode = new AnchorNode(anchor);
                        anchorNode.setParent(arFragment.getArSceneView().getScene());
                        transformableNode = new TransformableNode(arFragment.getTransformationSystem());
                        transformableNode.setParent(anchorNode);
                        transformableNode.setRenderable(punto);
                        currentSelectedAnchorNode=anchorNode;
                    }
                }

                if(clickNo==2) {
                    if (currentSelectedAnchorNode != null) {
                        //Get the current Pose and transform it then set a new anchor at the new pose
                        Session session = arFragment.getArSceneView().getSession();
                        Anchor currentAnchor = currentSelectedAnchorNode.getAnchor();
                        Pose oldPose = currentAnchor.getPose();
                        Pose newPose = oldPose.compose(Pose.makeTranslation(0,0.05f,0));
                        currentSelectedAnchorNode = moveRenderable(currentSelectedAnchorNode, newPose);
                    }
                }

            }
        });

        //AGREGA LA ACTUALIZACIÓN DEL MARCO PARA CONTROLAR EL ESTADO DEL BOTON
        arFragment.getArSceneView().getScene()
                .addOnUpdateListener(new Scene.OnUpdateListener(){
                    public void onUpdate(FrameTime frameTime){
                        if (anchorNode == null)
                        {
                            if (btn_anim.isEnabled())
                            {
                                btn_anim.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                                btn_anim.setEnabled(false);
                            }
                        }
                        else
                        {
                            if (!btn_anim.isEnabled())
                            {
                                btn_anim.setBackgroundTintList(ContextCompat.getColorStateList(FrutasA.this,R.color.colorAccent));
                                btn_anim.setEnabled(true);
                            }
                        }
                    }
                });

        ///////////////////////////////////////////////////////////////////////////
        ///////////////////////      BOTONES       ////////////////////////////////
        //////////////////////////////////////////////////////////////////////////

        //DECLARA PRIMER BOTON

        btn_anim = (FloatingActionButton)findViewById(R.id.btn_anim);
        btn_anim.setEnabled(false);
        btn_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAnimation();
            }
        });

        // BOTON DE INFORMACIÓN
        informacion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (choose == "pera"){
                    //stopSound(choose);
                    audiopera.start();
                }else if (choose == "manzana"){
                    //stopSound(choose);
                    audiomanzana.start();
                }else if (choose == "banana"){
                    //stopSound(choose);
                    audiobanana.start();
                }else if (choose == "uva"){
                    //stopSound(choose);
                    audiouva.start();
                }else if (choose == "sandia"){
                    //stopSound(choose);
                    audiosandia.start();
                }else if (choose == "melon"){
                    //stopSound(choose);
                    audiomelon.start();
                }else if (choose == "pina"){
                    //stopSound(choose);
                    audiopina.start();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(FrutasA.this);
                builder.setIcon(R.drawable.info).
                        setMessage(information).
                        setTitle("Información:");
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

        //ESCUCHA BOTON SI PERA ES PULSADO
        Pera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status1 = 1;
                if (currentSelectedAnchorNode != null) {

                    Session session = arFragment.getArSceneView().getSession();
                    Anchor currentAnchor = currentSelectedAnchorNode.getAnchor();
                    Pose oldPose = currentAnchor.getPose();
                    Pose newPose = oldPose.compose(Pose.makeTranslation(0,0.05f,0));
                    currentSelectedAnchorNode = moveRenderable(currentSelectedAnchorNode, newPose);

                }
            }
        });
        //ESCUCHA BOTON SI MANZANA ES PULSADO
        Manzana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status1 = 2;
                if (currentSelectedAnchorNode != null) {

                    Session session = arFragment.getArSceneView().getSession();
                    Anchor currentAnchor = currentSelectedAnchorNode.getAnchor();
                    Pose oldPose = currentAnchor.getPose();
                    Pose newPose = oldPose.compose(Pose.makeTranslation(0,0.05f,0));
                    currentSelectedAnchorNode = moveRenderable(currentSelectedAnchorNode, newPose);

                }
            }
        });
        //ESCUCHA BOTON SI BANANA ES PULSADO
        Banana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status1 = 3;
                if (currentSelectedAnchorNode != null) {

                    Session session = arFragment.getArSceneView().getSession();
                    Anchor currentAnchor = currentSelectedAnchorNode.getAnchor();
                    Pose oldPose = currentAnchor.getPose();
                    Pose newPose = oldPose.compose(Pose.makeTranslation(0,0.05f,0));
                    currentSelectedAnchorNode = moveRenderable(currentSelectedAnchorNode, newPose);

                }
            }
        });
        //ESCUCHA BOTON SI UVA ES PULSADO
        Uva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status1 = 4;
                if (currentSelectedAnchorNode != null) {

                    Session session = arFragment.getArSceneView().getSession();
                    Anchor currentAnchor = currentSelectedAnchorNode.getAnchor();
                    Pose oldPose = currentAnchor.getPose();
                    Pose newPose = oldPose.compose(Pose.makeTranslation(0,0.05f,0));
                    currentSelectedAnchorNode = moveRenderable(currentSelectedAnchorNode, newPose);

                }
            }
        });
        //ESCUCHA BOTON SI MELON ES PULSADO
        Melon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status1 = 6;
                if (currentSelectedAnchorNode != null) {

                    Session session = arFragment.getArSceneView().getSession();
                    Anchor currentAnchor = currentSelectedAnchorNode.getAnchor();
                    Pose oldPose = currentAnchor.getPose();
                    Pose newPose = oldPose.compose(Pose.makeTranslation(0,0.05f,0));
                    currentSelectedAnchorNode = moveRenderable(currentSelectedAnchorNode, newPose);

                }
            }
        });
        //ESCUCHA BOTON SI PIÑA ES PULSADO
        Pina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status1 = 7;
                if (currentSelectedAnchorNode != null) {

                    Session session = arFragment.getArSceneView().getSession();
                    Anchor currentAnchor = currentSelectedAnchorNode.getAnchor();
                    Pose oldPose = currentAnchor.getPose();
                    Pose newPose = oldPose.compose(Pose.makeTranslation(0,0.05f,0));
                    currentSelectedAnchorNode = moveRenderable(currentSelectedAnchorNode, newPose);

                }
            }
        });
        //ESCUCHA BOTON SI SANDIA ES PULSADO
        Sandia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status1 = 5;
                if (currentSelectedAnchorNode != null) {

                    Session session = arFragment.getArSceneView().getSession();
                    Anchor currentAnchor = currentSelectedAnchorNode.getAnchor();
                    Pose oldPose = currentAnchor.getPose();
                    Pose newPose = oldPose.compose(Pose.makeTranslation(0,0.05f,0));
                    currentSelectedAnchorNode = moveRenderable(currentSelectedAnchorNode, newPose);

                }
            }
        });
        //ESCUCHA BOTON SI SANDIA ES PULSADO
        Banana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status1 = 3;
                if (currentSelectedAnchorNode != null) {

                    Session session = arFragment.getArSceneView().getSession();
                    Anchor currentAnchor = currentSelectedAnchorNode.getAnchor();
                    Pose oldPose = currentAnchor.getPose();
                    Pose newPose = oldPose.compose(Pose.makeTranslation(0,0.05f,0));
                    currentSelectedAnchorNode = moveRenderable(currentSelectedAnchorNode, newPose);

                }
            }
        });
        ejecutarTarea();

    }

    /////////////////////////////////////////////////////////////////////////////
    ////////////////////////////       FUNCIONES        /////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    private void validationBug(){
        if (empyAnimation == false){
            playAnimation();
            empyAnimation = true;
        }else {
            animator.cancel();
            playAnimation();
        }
    }
    public void ejecutarTarea() {
        handler.postDelayed(new Runnable() {
            public void run() {
                if (pushButton == true){
                    validationBug();
                }
                handler.postDelayed(this, 5000);
            }
        }, 5000);

    }
    private  void playAnimation(){
        try {


            if (animator == null || !animator.isRunning()) {
                if (choose == "pera") {
                    AnimationData data = pera.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % pera.getAnimationDataCount();
                    animator = new ModelAnimator(data, pera);
                    animator.start();
                } else if (choose == "manzana") {
                    AnimationData data = manzana.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % manzana.getAnimationDataCount();
                    animator = new ModelAnimator(data, manzana);
                    animator.start();
                } else if (choose == "uva") {
                    AnimationData data = uva.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % uva.getAnimationDataCount();
                    animator = new ModelAnimator(data, uva);
                    animator.start();
                } else if (choose == "melon") {
                    AnimationData data = melon.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % melon.getAnimationDataCount();
                    animator = new ModelAnimator(data, melon);
                    animator.start();
                } else if (choose == "pina") {
                    AnimationData data = pina.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % pina.getAnimationDataCount();
                    animator = new ModelAnimator(data, pina);
                    animator.start();
                } else if (choose == "sandia") {
                    AnimationData data = sandia.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % sandia.getAnimationDataCount();
                    animator = new ModelAnimator(data, sandia);
                    animator.start();
                } else if (choose == "banana") {
                    AnimationData data = banana.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % banana.getAnimationDataCount();
                    animator = new ModelAnimator(data, banana);
                    animator.start();
                }

            }
        }catch (Exception e){

        }
    }

    private void stopSound(String sound){
        if (sound == "pera"){
            audiopera.pause();
        }else if (sound == "manzana"){
            audiomanzana.pause();
        }else if (sound == "banana"){
            audiobanana.pause();
        }else if (sound == "uva"){
            audiouva.pause();
        }else if (sound == "sandia"){
            audiosandia.pause();
        }else if (sound == "melon"){
            audiomelon.pause();
        }else if (sound == "pina"){
            audiopina.pause();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////// INICIALIZA ANIMACIONES //////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    private void setupModel() {

        //CARGA TIGRE
        ModelRenderable.builder()
                .setSource(this, R.raw.tigre31)
                .build()
                .thenAccept(renderable -> tigre = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });

        //CARGA PUNTO
        ModelRenderable.builder()
                .setSource(this, R.raw.point13)
                .build()
                .thenAccept(renderable -> punto = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA MANZANA
        ModelRenderable.builder()
                .setSource(this, R.raw.manzana71)
                .build()
                .thenAccept(renderable -> manzana = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA PERA
        ModelRenderable.builder()
                .setSource(this, R.raw.pera15)
                .build()
                .thenAccept(renderable -> pera = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA UVA
        ModelRenderable.builder()
                .setSource(this, R.raw.uvas15)
                .build()
                .thenAccept(renderable -> uva = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA MELON
        ModelRenderable.builder()
                .setSource(this, R.raw.melon13)
                .build()
                .thenAccept(renderable -> melon = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA PIÑA
        ModelRenderable.builder()
                .setSource(this, R.raw.pina5)
                .build()
                .thenAccept(renderable -> pina = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA SANDIA
        ModelRenderable.builder()
                .setSource(this, R.raw.sandia2)
                .build()
                .thenAccept(renderable -> sandia = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA SANDIA
        ModelRenderable.builder()
                .setSource(this, R.raw.banana1)
                .build()
                .thenAccept(renderable -> banana = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
    }

    private AnchorNode moveRenderable(AnchorNode markAnchorNodeToMove, Pose newPoseToMoveTo) {
        //Move a renderable to a new pose
        if (markAnchorNodeToMove != null) {
            arFragment.getArSceneView().getScene().removeChild(markAnchorNodeToMove);
            anchorNodeList.remove(markAnchorNodeToMove);
        } else {
            return null;
        }
        Frame frame = arFragment.getArSceneView().getArFrame();
        Session session = arFragment.getArSceneView().getSession();
        Anchor markAnchor = session.createAnchor(newPoseToMoveTo.extractTranslation());
        AnchorNode newMarkAnchorNode = new AnchorNode(markAnchor);
        TransformableNode andy = new TransformableNode(arFragment.getTransformationSystem());
        andy.setParent(newMarkAnchorNode);

        // AQUI RENDERIZA LOS OBJETOS PARA MOSTRAR EN LA PANTALLA

        switch(Status1)
        {
            case 1:
                pushButton = true;
                stopSound(choose);
                choose = "pera";
                andy.setRenderable(pera);
                //audiopera.start();
                information = "La pera tiene forma como una guitarra, es rica y da mucha energía.";
                validationBug();
                break;

            case 2:
                pushButton = true;
                stopSound(choose);
                choose = "manzana";
                andy.setRenderable(manzana);
                //audiomanzana.start();
                information = "La manzana es roja, amarilla, verde, es dulces , una fruta muy rica para hacernos fuertes.";
                validationBug();
                break;
            case 3:
                pushButton = true;
                stopSound(choose);
                choose = "banana";
                andy.setRenderable(banana);
                //audiobanana.start();
                information = "La banana es de color amarillo por fuera pero blanco por dentro, es muy dulce y muy rico en vitaminas.";
                validationBug();
                break;
            case 4:
                pushButton = true;
                stopSound(choose);
                choose = "uva";
                andy.setRenderable(uva);
                //audiouva.start();
                information = "La uva tiene forma de pequeños círculos, hay una semillita en el centro, tiene colores verde y morado, es dulce y muy rica.";
                validationBug();
                break;
            case 5:
                pushButton = true;
                stopSound(choose);
                choose = "sandia";
                andy.setRenderable(sandia);
                //audiosandia.start();
                information = "La sandía es una fruta verde por fuera y roja por dentro, tiene semillitas las cuales no hay que comer, es muy jugosa, rica y dulce.";
                validationBug();
                break;
            case 6:
                pushButton = true;
                stopSound(choose);
                choose = "melon";
                andy.setRenderable(melon);
                //audiomelon.start();
                information = "El melón es una fruta muy dulce, pero para probarla, hay que pelar, hay de varios colores, tiene semillita, pero no se pueden comer.";
                validationBug();
                break;
            case 7:
                pushButton = true;
                stopSound(choose);
                choose = "pina";
                andy.setRenderable(pina);
                //audiopina.start();
                information = "La piña es una fruta que tiene una forma muy particular, es grande y por dentro es muy dulces y huele muy rico, sirve también para jugos.";
                validationBug();
                break;
            default:
                break;
        }


        newMarkAnchorNode.setParent(arFragment.getArSceneView().getScene());
        anchorNodeList.add(newMarkAnchorNode);
        return newMarkAnchorNode;



    }
}
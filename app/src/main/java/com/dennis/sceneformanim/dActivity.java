package com.dennis.sceneformanim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
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

public class dActivity extends AppCompatActivity {
    //Variable
    private ArFragment arFragment;
    private AnchorNode anchorNode;
    private ModelAnimator animator;
    private int nextAnimation;
    private FloatingActionButton btn_anim;
    private ModelRenderable animationCrab, horse01, punto, vaca, perro, gallina, pato;
    private TransformableNode transformableNode;

    private int clickNo = 0;
    private int Status1 = 0;
    private String information = "";
    private String choose = "";

    //DECLARAR AUDIOS
    MediaPlayer audioDog, audioCat, audioRabbit, audioChicken, audioHorse,audioBird, audioDuck, audioCow, audioi, audiogDog, audiogCat, audiogRabbit, audiogChicken, audiogHorse,audiogBird, audiogDuck, audiogCow;

    //*************************************************************
    private List<AnchorNode> anchorNodeList = new ArrayList<>();
    private AnchorNode currentSelectedAnchorNode = null;
    //*************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dactivity);

        // DECLARA BOTONES DE CAMBIO DE OBJETO
        ImageButton catshow = (ImageButton) findViewById(R.id.id_gato1);
        ImageButton birdshow = (ImageButton) findViewById(R.id.id_pajaro1);
        ImageButton chickenshow= (ImageButton) findViewById(R.id.id_gallina1);
        ImageButton cowshow=(ImageButton) findViewById(R.id.id_vaca1);
        ImageButton dogshow=(ImageButton) findViewById(R.id.id_perro1);
        ImageButton duckshow=(ImageButton) findViewById(R.id.id_pato1);
        ImageButton horseshow=(ImageButton) findViewById(R.id.id_caballo1);
        ImageButton rabbitshow=(ImageButton) findViewById(R.id.id_conejo1);
        ImageButton informacion = (ImageButton) findViewById(R.id.id_informacion);

        //INICIALIZA AUDIOS
        audioDog = MediaPlayer.create(this,R.raw.daperro);
        audioCat = MediaPlayer.create(this,R.raw.dagato);
        audioRabbit = MediaPlayer.create(this,R.raw.daconejo);
        audioChicken = MediaPlayer.create(this,R.raw.dagallina);
        audioHorse = MediaPlayer.create(this,R.raw.dacaballo);
        audioBird = MediaPlayer.create(this,R.raw.dapajaro);
        audioDuck = MediaPlayer.create(this,R.raw.dapato);
        audioCow = MediaPlayer.create(this,R.raw.davaca);
        audioi = MediaPlayer.create(this,R.raw.naintro);
        //INICIALIZA RUGIDOS
        audiogDog = MediaPlayer.create(this,R.raw.sgperro);
        audiogCat = MediaPlayer.create(this,R.raw.sggato);
        audiogRabbit = MediaPlayer.create(this,R.raw.sgconejo);
        audiogChicken = MediaPlayer.create(this,R.raw.sggallina);
        audiogHorse = MediaPlayer.create(this,R.raw.sgcaballo);
        audiogBird = MediaPlayer.create(this,R.raw.sgpajaro);
        audiogDuck = MediaPlayer.create(this,R.raw.sgpato);
        audiogCow = MediaPlayer.create(this,R.raw.sgvaca);
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
                if(animationCrab ==null)
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
                                btn_anim.setBackgroundTintList(ContextCompat.getColorStateList(dActivity.this,R.color.colorAccent));
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
                if(animator == null || !animator.isRunning())
                {
                    if (choose == "vaca"){
                        AnimationData data = vaca.getAnimationData(nextAnimation);
                        nextAnimation = (nextAnimation+1)%vaca.getAnimationDataCount();
                        animator = new ModelAnimator(data,vaca);
                        animator.start();
                    }else if (choose == "caballo"){
                    AnimationData data = horse01.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation+1)%horse01.getAnimationDataCount();
                    animator = new ModelAnimator(data,horse01);
                    animator.start();
                    }else if (choose == "perro"){
                        AnimationData data = perro.getAnimationData(nextAnimation);
                        nextAnimation = (nextAnimation+1)%perro.getAnimationDataCount();
                        animator = new ModelAnimator(data,perro);
                        animator.start();
                    }else if (choose == "pato"){
                        AnimationData data = pato.getAnimationData(nextAnimation);
                        nextAnimation = (nextAnimation+1)%pato.getAnimationDataCount();
                        animator = new ModelAnimator(data,pato);
                        animator.start();
                    }

                }
            }
        });

        // BOTON DE INFORMACIÓN
        informacion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (choose == "gato"){
                    stopSound(choose);
                    audioCat.start();
                    choose = "ggato";
                }else if (choose == "pajaro"){
                    stopSound(choose);
                    audioBird.start();
                    choose = "gpajaro";
                }else if (choose == "gallina"){
                    stopSound(choose);
                    audioChicken.start();
                    choose = "ggallina";
                }else if (choose == "vaca"){
                    stopSound(choose);
                    audioCow.start();
                    choose = "gvaca";
                }else if (choose == "perro"){
                    stopSound(choose);
                    audioDog.start();
                    choose = "gperro";
                }else if (choose == "pato"){
                    stopSound(choose);
                    audioDuck.start();
                    choose = "gpato";
                }else if (choose == "caballo"){
                    stopSound(choose);
                    audioHorse.start();
                    choose = "gcaballo";
                }else if (choose == "conejo"){
                    stopSound(choose);
                    audioRabbit.start();
                    choose = "gconejo";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(dActivity.this);
                builder.setIcon(R.drawable.info).
                        setMessage(information).
                        setTitle("Información:");
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

        //ESCUCHA BOTON SI CABALLO ES PULSADO
        horseshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status1 = 8;
                if (currentSelectedAnchorNode != null) {

                    Session session = arFragment.getArSceneView().getSession();
                    Anchor currentAnchor = currentSelectedAnchorNode.getAnchor();
                    Pose oldPose = currentAnchor.getPose();
                    Pose newPose = oldPose.compose(Pose.makeTranslation(0,0.05f,0));
                    currentSelectedAnchorNode = moveRenderable(currentSelectedAnchorNode, newPose);

                }
            }
        });
        //ESCUCHA BOTON SI VACA ES PULSADO
        cowshow.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI PERRO ES PULSADO
        dogshow.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI PATO ES PULSADO
        duckshow.setOnClickListener(new View.OnClickListener() {
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

    }

    /////////////////////////////////////////////////////////////////////////////
    ////////////////////////////       FUNCIONES        /////////////////////////
    /////////////////////////////////////////////////////////////////////////////

    private void stopSound(String sound){
        if (sound == "gato"){
            audiogCat.pause();
        }else if (sound == "pajaro"){
            audiogBird.pause();
        }else if (sound == "gallina"){
            audiogChicken.pause();
        }else if (sound == "vaca"){
            audiogCow.pause();
        }else if (sound == "perro"){
            audiogDog.pause();
        }else if (sound == "pato"){
            audiogDuck.pause();
        }else if (sound == "caballo"){
            audiogHorse.pause();
        }else if (sound == "conejo"){
            audiogRabbit.pause();
        }else if (sound == "ggato"){
            audioCat.pause();
        }else if (sound == "gpajaro"){
            audioBird.pause();
        }else if (sound == "ggallina"){
            audioChicken.pause();
        }else if (sound == "gvaca"){
            audioCow.pause();
        }else if (sound == "gperro"){
            audioDog.pause();
        }else if (sound == "gpato"){
            audioDuck.pause();
        }else if (sound == "gcaballo"){
            audioHorse.pause();
        }else if (sound == "gconejo"){
            audioRabbit.pause();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////// INICIALIZA ANIMACIONES //////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    private void setupModel() {
        //CARGA CABALLO
        ModelRenderable.builder()
                .setSource(this, R.raw.horseflx18)
                .build()
                .thenAccept(renderable -> horse01 = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });

        //CARGA TIGRE
        ModelRenderable.builder()
                .setSource(this, R.raw.tigre21)
                .build()
                .thenAccept(renderable -> animationCrab = renderable)
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

        //CARGA VACA
        ModelRenderable.builder()
                .setSource(this, R.raw.vaca7)
                .build()
                .thenAccept(renderable -> vaca = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA PERRO
        ModelRenderable.builder()
                .setSource(this, R.raw.dalmata14)
                .build()
                .thenAccept(renderable -> perro = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA PATO
        ModelRenderable.builder()
                .setSource(this, R.raw.pato5)
                .build()
                .thenAccept(renderable -> pato = renderable)
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
                stopSound(choose);
                choose = "gato";
                //andy.setRenderable(animationCrab);
                audiogCat.start();
                information = "Mamífero de contextura pequeña, de abundante pelaje y muy suave, son muy cariñoso con los humanos.";
                break;
            case 6:
                stopSound(choose);
                choose = "pajaro";
                //andy.setRenderable(horse01);
                audiogBird.start();
                information = "Las aves son seres extraordinarios y fascinantes: muchas de ellas poseen un plumaje colorido, producen sonidos extraordinarios o pueden volar.";
                break;
            case 4:
                stopSound(choose);
                choose = "gallina";
                //andy.setRenderable(chicken1);
                audiogChicken.start();
                information = "La gallina es denominado un ave conocida por su cacareo, pone huevos, y está cubierta de plumas de diversos colores";
                break;
            case 7:
                stopSound(choose);
                choose = "vaca";
                andy.setRenderable(vaca);
                audiogCow.start();
                information = "La vaca es un animal mamífero, se alimenta del pasto, hierbas, tallos, hojas, semillas y raíces.";
                break;
            case 2:
                stopSound(choose);
                choose = "perro";
                andy.setRenderable(perro);
                audiogDog.start();
                information = "El perro doméstico es un mamífero carnívoro, Su tamaño, forma y pelaje varían en función de la raza de perro, ven bien, usan mayormente su oído y su olfato, sentidos que tienen muy desarrollados y que son muy prácticos para el humano.";
                break;
            case 5:
                stopSound(choose);
                choose = "pato";
                andy.setRenderable(pato);
                audiogDuck.start();
                information = "El pato es un ave, vive cerca del agua y nadan.";
                break;
            case 8:
                stopSound(choose);
                choose = "caballo";
                andy.setRenderable(horse01);
                audiogHorse.start();
                information = "Un Caballo es un animal cuadrúpedo perteneciente a la especie de los mamíferos, se caracteriza por su gran tamaño, son animales que galopan y relinchan";
                break;
            case 3:
                stopSound(choose);
                choose = "conejo";
                //andy.setRenderable(rabbit1);
                audiogRabbit.start();
                information = "Son animales que tienen muy buena relación con los humanos, ya que son muy amistosos y agradables.";
                break;
            default:
                break;
        }


        newMarkAnchorNode.setParent(arFragment.getArSceneView().getScene());
        anchorNodeList.add(newMarkAnchorNode);
        return newMarkAnchorNode;



    }
}
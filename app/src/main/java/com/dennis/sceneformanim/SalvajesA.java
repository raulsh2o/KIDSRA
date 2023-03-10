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

public class SalvajesA extends AppCompatActivity {
    //Variable
    private ArFragment arFragment;
    private AnchorNode anchorNode;
    private ModelAnimator animator;
    private int nextAnimation;
    private FloatingActionButton btn_anim;
    private ModelRenderable tigre, punto, cocodrilo, elefante, jirafa, mono, hipopotamo, leon, serpiente;
    private TransformableNode transformableNode;

    private int clickNo = 0;
    private int Status1 = 0;
    private String information = "";
    private String choose = "";
    private Boolean empyAnimation = false;
    private  Boolean pushButton = false;

    Handler handler = new Handler();

    //DECLARAR AUDIOS
    MediaPlayer audioMonkey, audioLion, audioTiger, audioElephant, audioSerpent,audioJirafa, audioHipopo, audioCocodrile, audioi, audiogMonkey, audiogLion, audiogTiger, audiogElephant, audiogSerpent,audiogJirafa, audiogHipopo, audiogCocodrile;

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
        setContentView(R.layout.activity_salvajes);
        // DECLARA BOTONES DE CAMBIO DE OBJETO
        ImageButton mono1show = (ImageButton) findViewById(R.id.id_Mono1);
        ImageButton leon1show = (ImageButton) findViewById(R.id.id_leon1);
        ImageButton tigre1show = (ImageButton) findViewById(R.id.id_tigre1);
        ImageButton elefante1show = (ImageButton) findViewById(R.id.id_elefante1);
        ImageButton serpiente1show = (ImageButton) findViewById(R.id.id_serpiente1);
        ImageButton jirafa1show = (ImageButton) findViewById(R.id.id_jirafa1);
        ImageButton hipopotamo1show = (ImageButton) findViewById(R.id.id_hipopotamo1);
        ImageButton cocodrilo1show = (ImageButton) findViewById(R.id.id_cocodrilo1);
        ImageButton informacion = (ImageButton) findViewById(R.id.id_informacion);

        //INICIALIZA AUDIOS
        audioMonkey = MediaPlayer.create(this,R.raw.samono);
        audioLion = MediaPlayer.create(this,R.raw.saleon);
        audioTiger = MediaPlayer.create(this,R.raw.satigre);
        audioElephant = MediaPlayer.create(this,R.raw.saelefante);
        audioSerpent = MediaPlayer.create(this,R.raw.saserpiente);
        audioJirafa = MediaPlayer.create(this,R.raw.sajirafa);
        audioHipopo = MediaPlayer.create(this,R.raw.sahipopotamo);
        audioCocodrile = MediaPlayer.create(this,R.raw.sacocodrilo);
        //INICIALIZA RUGIDOS
        audiogMonkey = MediaPlayer.create(this,R.raw.sgmono);
        audiogLion = MediaPlayer.create(this,R.raw.sgleon);
        audiogTiger = MediaPlayer.create(this,R.raw.sgtigre);
        audiogElephant = MediaPlayer.create(this,R.raw.sgelefante);
        audiogSerpent = MediaPlayer.create(this,R.raw.sgserpiente);
        audiogJirafa = MediaPlayer.create(this,R.raw.sgjirafa);
        audiogHipopo = MediaPlayer.create(this,R.raw.sghipopotamo);
        audiogCocodrile = MediaPlayer.create(this,R.raw.sgcocodrilo);
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
                    if (anchorNode == null) //SI LA ANIMACI??N NO SE COLOCA EN EL PLANO
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

        //AGREGA LA ACTUALIZACI??N DEL MARCO PARA CONTROLAR EL ESTADO DEL BOTON
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
                                btn_anim.setBackgroundTintList(ContextCompat.getColorStateList(SalvajesA.this,R.color.colorAccent));
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

        // BOTON DE INFORMACI??N
        informacion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (choose == "mono"){
                    stopSound(choose);
                    audioMonkey.start();
                    choose = "gmono";
                }else if (choose == "leon"){
                    stopSound(choose);
                    audioLion.start();
                    choose = "gleon";
                }else if (choose == "tigre"){
                    stopSound(choose);
                    audioTiger.start();
                    choose = "gtigre";
                }else if (choose == "elefante"){
                    stopSound(choose);
                    audioElephant.start();
                    choose = "gelefante";
                }else if (choose == "serpiente"){
                    stopSound(choose);
                    audioSerpent.start();
                    choose = "gserpiente";
                }else if (choose == "jirafa"){
                    stopSound(choose);
                    audioJirafa.start();
                    choose = "gjirafa";
                }else if (choose == "hipopotamo"){
                    stopSound(choose);
                    audioHipopo.start();
                    choose = "ghipopotamo";
                }else if (choose == "cocodrilo"){
                    stopSound(choose);
                    audioCocodrile.start();
                    choose = "gcocodrilo";
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(SalvajesA.this);
                builder.setIcon(R.drawable.info).
                        setMessage(information).
                        setTitle("Informaci??n:");
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

        //ESCUCHA BOTON SI COCODRILO ES PULSADO
        cocodrilo1show.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI ELEFANTE ES PULSADO
        elefante1show.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI JIRAFA ES PULSADO
        jirafa1show.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI TIGRE ES PULSADO
        tigre1show.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI MONO ES PULSADO
        mono1show.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI HIPOPOTAMO ES PULSADO
        hipopotamo1show.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI LEON ES PULSADO
        leon1show.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI SERPIENTE ES PULSADO
        serpiente1show.setOnClickListener(new View.OnClickListener() {
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
    private void playAnimation(){
        try {

            if (animator == null || !animator.isRunning()) {
                if (choose == "tigre") {
                    audiogTiger.start();
                    AnimationData data = tigre.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % tigre.getAnimationDataCount();
                    animator = new ModelAnimator(data, tigre);
                    animator.start();
                } else if (choose == "cocodrilo") {
                    audiogCocodrile.start();
                    AnimationData data = cocodrilo.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % cocodrilo.getAnimationDataCount();
                    animator = new ModelAnimator(data, cocodrilo);
                    animator.start();
                } else if (choose == "elefante") {
                    audiogElephant.start();
                    AnimationData data = elefante.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % elefante.getAnimationDataCount();
                    animator = new ModelAnimator(data, elefante);
                    animator.start();
                } else if (choose == "jirafa") {
                    audiogJirafa.start();
                    AnimationData data = jirafa.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % jirafa.getAnimationDataCount();
                    animator = new ModelAnimator(data, jirafa);
                    animator.start();
                } else if (choose == "mono") {
                    audiogMonkey.start();
                    AnimationData data = mono.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % mono.getAnimationDataCount();
                    animator = new ModelAnimator(data, mono);
                    animator.start();
                } else if (choose == "hipopotamo") {
                    audiogHipopo.start();
                    AnimationData data = hipopotamo.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % hipopotamo.getAnimationDataCount();
                    animator = new ModelAnimator(data, hipopotamo);
                    animator.start();
                } else if (choose == "leon") {
                    audiogLion.start();
                    AnimationData data = leon.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % leon.getAnimationDataCount();
                    animator = new ModelAnimator(data, leon);
                    animator.start();
                } else if (choose == "serpiente") {
                    audiogSerpent.start();
                    AnimationData data = serpiente.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % serpiente.getAnimationDataCount();
                    animator = new ModelAnimator(data, serpiente);
                    animator.start();
                }

            }
        }catch (Exception e){

        }
    }

    private void stopSound(String sound){
        if (sound == "mono"){
            audiogMonkey.pause();
            audiogMonkey.seekTo(0);
        }else if (sound == "leon"){
            audiogLion.pause();
            audiogLion.seekTo(0);
        }else if (sound == "tigre"){
            audiogTiger.pause();
            audiogTiger.seekTo(0);
        }else if (sound == "elefante"){
            audiogElephant.pause();
            audiogElephant.seekTo(0);
        }else if (sound == "serpiente"){
            audiogSerpent.pause();
            audiogSerpent.seekTo(0);
        }else if (sound == "jirafa"){
            audiogJirafa.pause();
            audiogJirafa.seekTo(0);
        }else if (sound == "hipopotamo"){
            audiogHipopo.pause();
            audiogHipopo.seekTo(0);
        }else if (sound == "cocodrilo"){
            audiogCocodrile.pause();
            audiogCocodrile.seekTo(0);
        }else if (sound == "gmono"){
            audioMonkey.pause();
            audioMonkey.seekTo(0);
        }else if (sound == "gleon"){
            audioLion.pause();
            audioLion.seekTo(0);
        }else if (sound == "gtigre"){
            audioTiger.pause();
            audioTiger.seekTo(0);
        }else if (sound == "gelefante"){
            audioElephant.pause();
            audioElephant.seekTo(0);
        }else if (sound == "gserpiente"){
            audioSerpent.pause();
            audioSerpent.seekTo(0);
        }else if (sound == "gjirafa"){
            audioJirafa.pause();
            audioJirafa.seekTo(0);
        }else if (sound == "ghipopotamo"){
            audioHipopo.pause();
            audioHipopo.seekTo(0);
        }else if (sound == "gcocodrilo"){
            audioCocodrile.pause();
            audioCocodrile.seekTo(0);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////// INICIALIZA ANIMACIONES //////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    private void setupModel() {

        //CARGA TIGRE
        ModelRenderable.builder()
                .setSource(this, R.raw.tigre63)
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

        //CARGA COCODRILO
        ModelRenderable.builder()
                .setSource(this, R.raw.cocodrilo5)
                .build()
                .thenAccept(renderable -> cocodrilo = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });

        //CARGA ELEFANTE
        ModelRenderable.builder()
                .setSource(this, R.raw.elefantefbx40)
                .build()
                .thenAccept(renderable -> elefante = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA JIRAFA
        ModelRenderable.builder()
                .setSource(this, R.raw.girafafbx17)
                .build()
                .thenAccept(renderable -> jirafa = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA MONO
        ModelRenderable.builder()
                .setSource(this, R.raw.mono)
                .build()
                .thenAccept(renderable -> mono = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA HIPOPOTAMO
        ModelRenderable.builder()
                .setSource(this, R.raw.hipo17)
                .build()
                .thenAccept(renderable -> hipopotamo = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA LEON
        ModelRenderable.builder()
                .setSource(this, R.raw.leon6)
                .build()
                .thenAccept(renderable -> leon = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA SERPIENTE
        ModelRenderable.builder()
                .setSource(this, R.raw.serpiente3)
                .build()
                .thenAccept(renderable -> serpiente = renderable)
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
                choose = "mono";
                andy.setRenderable(mono);
                //audiogMonkey.start();
                information = "El mono es proveniente de la familia de los primates, usas sus extremidades para cazar, comer o hacer otras acciones diferentes.";
                validationBug();
                break;
            case 2:
                pushButton = true;
                stopSound(choose);
                choose = "leon";
                andy.setRenderable(leon);
                //audiogLion.start();
                information = "El le??n es el rey de la selva, es salvaje, fuerte, grande y tiene dientes muy grandes.";
                validationBug();
                break;
            case 3:
                pushButton = true;
                stopSound(choose);
                choose = "tigre";
                andy.setRenderable(tigre);
                //audiogTiger.start();
                information = "El tigre es un animal grande, corren muy r??pido, son solitarios y cazadores.";
                validationBug();
                break;
            case 4:
                pushButton = true;
                stopSound(choose);
                choose = "elefante";
                andy.setRenderable(elefante);
                //audiogElephant.start();
                information = "El elefante es el animal terrestre m??s grande, tiene orejas grandes y su trompa muy larga, tienen ojos peque??os.";
                validationBug();
                break;
            case 5:
                pushButton = true;
                stopSound(choose);
                choose = "serpiente";
                andy.setRenderable(serpiente);
                //audiogSerpent.start();
                information = "La serpiente es un animal que se arrastra por el suelo, no tiene patas, vota veneno por su boca, su cuerpo es muy largo.";
                validationBug();
                break;
            case 6:
                pushButton = true;
                stopSound(choose);
                choose = "jirafa";
                andy.setRenderable(jirafa);
                //audiogJirafa.start();
                information = "Las jirafas son animales de cuello largo, son de color amarillo con manchas negras y tiene dos cuernos peque??os.";
                validationBug();
                break;

            case 7:
                pushButton = true;
                stopSound(choose);
                choose = "hipopotamo";
                andy.setRenderable(hipopotamo);
                //audiogHipopo.start();
                information = "El hipop??tamo es un animal de boca enorme con grandes dientes, son grandes y pesados, son muy agresivos y de patas cortas.";
                validationBug();
                break;
            case 8:
                pushButton = true;
                stopSound(choose);
                choose = "cocodrilo";
                andy.setRenderable(cocodrilo);
                //audiogCocodrile.start();
                information = "El cocodrilo   es un animal con 4 patas, se arrastra por el suelo, con una boca enorme y dientes muy grandes, tiene una cola muy larga, y viven en los pantanos, lagos o r??os.";
                validationBug();
                break;
            /*case 20:
                newMarkAnchorNode.setRenderable(jirafa01);
            break;*/

            default:
                break;
        }


        newMarkAnchorNode.setParent(arFragment.getArSceneView().getScene());
        anchorNodeList.add(newMarkAnchorNode);
        return newMarkAnchorNode;



    }
}
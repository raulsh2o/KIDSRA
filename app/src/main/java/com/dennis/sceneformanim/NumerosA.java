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

public class NumerosA extends AppCompatActivity {
    //Variable
    private ArFragment arFragment;
    private AnchorNode anchorNode;
    private ModelAnimator animator;
    private int nextAnimation;
    private FloatingActionButton btn_anim;
    private ModelRenderable tigre, punto, cero, uno, dos, tres, cuatro, cinco, seis, siete, ocho, nueve, diez;
    private TransformableNode transformableNode;

    private int clickNo = 0;
    private int Status1 = 0;
    private String information = "";
    private String choose = "";
    private Boolean empyAnimation = false;
    private  Boolean pushButton = false;

    Handler handler = new Handler();

    //DECLARAR AUDIOS
    MediaPlayer  audioi, audio0, audio1, audio2, audio3, audio4,audio5,audio6,audio7,audio8,audio9,audio10;

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
        setContentView(R.layout.activity_numeros);
        // DECLARA BOTONES DE CAMBIO DE OBJETO
        ImageButton Ncero = (ImageButton) findViewById(R.id.id_cero);
        ImageButton Nuno = (ImageButton) findViewById(R.id.id_uno);
        ImageButton Ndos = (ImageButton) findViewById(R.id.id_dos);
        ImageButton Ntres = (ImageButton) findViewById(R.id.id_tres);
        ImageButton Ncuatro = (ImageButton) findViewById(R.id.id_cuatro);
        ImageButton Ncinco = (ImageButton) findViewById(R.id.id_cinco);
        ImageButton Nseis = (ImageButton) findViewById(R.id.id_seis);
        ImageButton Nsiete = (ImageButton) findViewById(R.id.id_siete);
        ImageButton Nocho = (ImageButton) findViewById(R.id.id_ocho);
        ImageButton Nnueve = (ImageButton) findViewById(R.id.id_nueve);
        ImageButton Ndiez = (ImageButton) findViewById(R.id.id_diez);
        ImageButton informacion = (ImageButton) findViewById(R.id.id_informacion);

        //INICIALIZA AUDIOS

        audio0 = MediaPlayer.create(this,R.raw.nacero);
        audio1 = MediaPlayer.create(this,R.raw.nauno);
        audio2 = MediaPlayer.create(this,R.raw.nados);
        audio3 = MediaPlayer.create(this,R.raw.natres);
        audio4 = MediaPlayer.create(this,R.raw.nacuatro);
        audio5 = MediaPlayer.create(this,R.raw.nacinco);
        audio6 = MediaPlayer.create(this,R.raw.naseis);
        audio7 = MediaPlayer.create(this,R.raw.nasiete);
        audio8 = MediaPlayer.create(this,R.raw.naocho);
        audio9 = MediaPlayer.create(this,R.raw.nanueve);
        audio10 = MediaPlayer.create(this,R.raw.nadiez);
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
                                btn_anim.setBackgroundTintList(ContextCompat.getColorStateList(NumerosA.this,R.color.colorAccent));
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

                AlertDialog.Builder builder = new AlertDialog.Builder(NumerosA.this);
                builder.setIcon(R.drawable.info).
                        setMessage(information).
                        setTitle("Informaci??n:");
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

        //ESCUCHA BOTON SI CERO ES PULSADO
        Ncero.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI UNO ES PULSADO
        Nuno.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI DOS ES PULSADO
        Ndos.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI TRES ES PULSADO
        Ntres.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI CUATRO ES PULSADO
        Ncuatro.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI CINCO ES PULSADO
        Ncinco.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI OCHO ES PULSADO
        Nocho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status1 = 9;
                if (currentSelectedAnchorNode != null) {

                    Session session = arFragment.getArSceneView().getSession();
                    Anchor currentAnchor = currentSelectedAnchorNode.getAnchor();
                    Pose oldPose = currentAnchor.getPose();
                    Pose newPose = oldPose.compose(Pose.makeTranslation(0,0.05f,0));
                    currentSelectedAnchorNode = moveRenderable(currentSelectedAnchorNode, newPose);

                }
            }
        });
        //ESCUCHA BOTON SI NUEVE ES PULSADO
        Nnueve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status1 = 10;
                if (currentSelectedAnchorNode != null) {

                    Session session = arFragment.getArSceneView().getSession();
                    Anchor currentAnchor = currentSelectedAnchorNode.getAnchor();
                    Pose oldPose = currentAnchor.getPose();
                    Pose newPose = oldPose.compose(Pose.makeTranslation(0,0.05f,0));
                    currentSelectedAnchorNode = moveRenderable(currentSelectedAnchorNode, newPose);

                }
            }
        });
        //ESCUCHA BOTON SI DIEZ ES PULSADO
        Ndiez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status1 = 11;
                if (currentSelectedAnchorNode != null) {

                    Session session = arFragment.getArSceneView().getSession();
                    Anchor currentAnchor = currentSelectedAnchorNode.getAnchor();
                    Pose oldPose = currentAnchor.getPose();
                    Pose newPose = oldPose.compose(Pose.makeTranslation(0,0.05f,0));
                    currentSelectedAnchorNode = moveRenderable(currentSelectedAnchorNode, newPose);

                }
            }
        });
        //ESCUCHA BOTON SI SIES ES PULSADO
        Nseis.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI SIETE ES PULSADO
        Nsiete.setOnClickListener(new View.OnClickListener() {
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
                if (choose == "cero") {
                    audio0.start();
                    AnimationData data = cero.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % cero.getAnimationDataCount();
                    animator = new ModelAnimator(data, cero);
                    animator.start();
                } else if (choose == "uno") {
                    audio1.start();
                    AnimationData data = uno.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % uno.getAnimationDataCount();
                    animator = new ModelAnimator(data, uno);
                    animator.start();
                } else if (choose == "dos") {
                    audio2.start();
                    AnimationData data = dos.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % dos.getAnimationDataCount();
                    animator = new ModelAnimator(data, dos);
                    animator.start();
                } else if (choose == "tres") {
                    audio3.start();
                    AnimationData data = tres.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % tres.getAnimationDataCount();
                    animator = new ModelAnimator(data, tres);
                    animator.start();
                } else if (choose == "cuatro") {
                    audio4.start();
                    AnimationData data = cuatro.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % cuatro.getAnimationDataCount();
                    animator = new ModelAnimator(data, cuatro);
                    animator.start();
                } else if (choose == "cinco") {
                    audio5.start();
                    AnimationData data = cinco.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % cinco.getAnimationDataCount();
                    animator = new ModelAnimator(data, cinco);
                    animator.start();
                } else if (choose == "seis") {
                    audio6.start();
                    AnimationData data = seis.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % seis.getAnimationDataCount();
                    animator = new ModelAnimator(data, seis);
                    animator.start();
                } else if (choose == "siete") {
                    audio7.start();
                    AnimationData data = siete.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % siete.getAnimationDataCount();
                    animator = new ModelAnimator(data, siete);
                    animator.start();
                } else if (choose == "ocho") {
                    audio8.start();
                    AnimationData data = ocho.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % ocho.getAnimationDataCount();
                    animator = new ModelAnimator(data, ocho);
                    animator.start();
                } else if (choose == "nueve") {
                    audio9.start();
                    AnimationData data = nueve.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % nueve.getAnimationDataCount();
                    animator = new ModelAnimator(data, nueve);
                    animator.start();
                } else if (choose == "diez") {
                    audio10.start();
                    AnimationData data = diez.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % diez.getAnimationDataCount();
                    animator = new ModelAnimator(data, diez);
                    animator.start();
                }

            }
        }catch (Exception e){

        }
    }

    private void stopSound(String sound){
        if (sound == "cero"){
            audio0.pause();
            audio0.seekTo(0);
        }else if (sound == "uno"){
            audio1.pause();
            audio1.seekTo(0);
        }else if (sound == "dos"){
            audio2.pause();
            audio2.seekTo(0);
        }else if (sound == "tres"){
            audio3.pause();
            audio3.seekTo(0);
        }else if (sound == "cuatro"){
            audio4.pause();
            audio4.seekTo(0);
        }else if (sound == "cinco"){
            audio5.pause();
            audio5.seekTo(0);
        }else if (sound == "seis"){
            audio6.pause();
            audio6.seekTo(0);
        }else if (sound == "siete"){
            audio7.pause();
            audio7.seekTo(0);
        }else if (sound == "ocho"){
            audio8.pause();
            audio8.seekTo(0);
        }else if (sound == "nueve"){
            audio9.pause();
            audio9.seekTo(0);
        }else if (sound == "diez"){
            audio10.pause();
            audio10.seekTo(0);
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

        //CARGA CERO
        ModelRenderable.builder()
                .setSource(this, R.raw.manocero7)
                .build()
                .thenAccept(renderable -> cero = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });

        //CARGA UNO
        ModelRenderable.builder()
                .setSource(this, R.raw.manouno5)
                .build()
                .thenAccept(renderable -> uno = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });

        //CARGA DOS
        ModelRenderable.builder()
                .setSource(this, R.raw.manodos5)
                .build()
                .thenAccept(renderable -> dos = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });

        //CARGA TRES
        ModelRenderable.builder()
                .setSource(this, R.raw.manotres3)
                .build()
                .thenAccept(renderable -> tres = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });

        //CARGA CUATRO
        ModelRenderable.builder()
                .setSource(this, R.raw.manocuatro1)
                .build()
                .thenAccept(renderable -> cuatro = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA CINCO
        ModelRenderable.builder()
                .setSource(this, R.raw.manocinco)
                .build()
                .thenAccept(renderable -> cinco = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA SEIS
        ModelRenderable.builder()
                .setSource(this, R.raw.manoseis16)
                .build()
                .thenAccept(renderable -> seis = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA SIETE
        ModelRenderable.builder()
                .setSource(this, R.raw.manosiete17)
                .build()
                .thenAccept(renderable -> siete = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA OCHO
        ModelRenderable.builder()
                .setSource(this, R.raw.manoocho8)
                .build()
                .thenAccept(renderable -> ocho = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA NUEVE
        ModelRenderable.builder()
                .setSource(this, R.raw.manonueve9)
                .build()
                .thenAccept(renderable -> nueve = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA DIEZ
        ModelRenderable.builder()
                .setSource(this, R.raw.manodiez10)
                .build()
                .thenAccept(renderable -> diez = renderable)
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
        andy.getScaleController().setMinScale(0.09f);
        andy.getScaleController().setMaxScale(0.1f);

        // AQUI RENDERIZA LOS OBJETOS PARA MOSTRAR EN LA PANTALLA

        switch(Status1)
        {
            case 1:
                pushButton = true;
                stopSound(choose);
                choose = "cero";
                andy.setRenderable(cero);
                //audio0.start();
                information = "El n??mero cero es la rueda moscovita para pasarlo bien.";
                validationBug();
                break;

            case 2:
                pushButton = true;
                stopSound(choose);
                choose = "uno";
                andy.setRenderable(uno);
                //audio1.start();
                information = "El n??mero uno es un soldado haciendo la instrucci??n.";
                validationBug();
                break;
            case 3:
                pushButton = true;
                stopSound(choose);
                choose = "dos";
                andy.setRenderable(dos);
                //audio2.start();
                information = "El n??mero dos es un patito que est?? tomando el sol.";
                validationBug();
                break;
            case 4:
                pushButton = true;
                stopSound(choose);
                choose = "tres";
                andy.setRenderable(tres);
                //audio3.start();
                information = "El n??mero tres es una serpiente que baila sin parar.";
                validationBug();
                break;
            case 5:
                pushButton = true;
                stopSound(choose);
                choose = "cuatro";
                andy.setRenderable(cuatro);
                //audio4.start();
                information = "El n??mero cuatro es una sillita que invita a descansar.";
                validationBug();
                break;
            case 6:
                pushButton = true;
                stopSound(choose);
                choose = "cinco";
                andy.setRenderable(cinco);
                //audio5.start();
                information = "El n??mero cinco tiene orejas, parece un conejito.";
                validationBug();
                break;

            case 7:
                pushButton = true;
                stopSound(choose);
                choose = "seis";
                andy.setRenderable(seis);
                //audio6.start();
                information = "El n??mero seis es una pera redonda y con rabito.";
                validationBug();
                break;
            case 8:
                pushButton = true;
                stopSound(choose);
                choose = "siete";
                andy.setRenderable(siete);
                //audio7.start();
                information = "El n??mero siete es un sereno con gorra y con bast??n.";
                validationBug();
                break;
            case 9:
                pushButton = true;
                stopSound(choose);
                choose = "ocho";
                andy.setRenderable(ocho);
                //audio8.start();
                information = "El n??mero ocho son las gafas que lleva don Ram??n.";
                validationBug();
                break;
            case 10:
                pushButton = true;
                stopSound(choose);
                choose = "nueve";
                andy.setRenderable(nueve);
                //audio9.start();
                information = "El n??mero nueve es un globito atado a un cordel.";
                validationBug();
                break;

            case 11:
                pushButton = true;
                stopSound(choose);
                choose = "diez";
                andy.setRenderable(diez);
                //audio10.start();
                information = "El n??mero diez es un soldado que lleva un gran mel??n.";
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
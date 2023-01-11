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

public class VocalesA extends AppCompatActivity {
    //Variable
    private ArFragment arFragment;
    private AnchorNode anchorNode;
    private ModelAnimator animator;
    private int nextAnimation;
    private FloatingActionButton btn_anim;
    private ModelRenderable tigre, punto, a,  e, i, o, u;
    private TransformableNode transformableNode;

    private int clickNo = 0;
    private int Status1 = 0;
    private String information = "";
    private String choose = "";
    private Boolean empyAnimation = false;
    private  Boolean pushButton = false;

    //PARA CREAR DELAYS
    Handler handler = new Handler();

    //DECLARAR AUDIOS
    MediaPlayer audioa, audioe, audioi, audioo, audiou, audiointro, audioga, audioge, audiogi, audiogo, audiogu;

    //*************************************************************
    private List<AnchorNode> anchorNodeList = new ArrayList<>();
    private AnchorNode currentSelectedAnchorNode = null;
    //*************************************************************
    @Override
    //FUNCION PARA CUANDO PULSE TECLA ATRAS MATE TODOS LOS PROCESOS
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            pushButton=false;
            audiointro.stop();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocales);
        // DECLARA BOTONES DE CAMBIO DE OBJETO
        ImageButton Vocala = (ImageButton) findViewById(R.id.id_vocala);
        ImageButton Vocale = (ImageButton) findViewById(R.id.id_vocale);
        ImageButton Vocali = (ImageButton) findViewById(R.id.id_vocali);
        ImageButton Vocalo = (ImageButton) findViewById(R.id.id_vocalo);
        ImageButton Vocalu = (ImageButton) findViewById(R.id.id_vocalu);
        ImageButton informacion = (ImageButton) findViewById(R.id.id_informacion);

        //INICIALIZA AUDIOS
        audioa = MediaPlayer.create(this,R.raw.vaudioa);
        audioe = MediaPlayer.create(this,R.raw.vaudioe);
        audioi = MediaPlayer.create(this,R.raw.vaudioi);
        audioo = MediaPlayer.create(this,R.raw.vaudioo);
        audiou = MediaPlayer.create(this,R.raw.vaudiou);

        //SONIDOS DE LAS VOCALES
        audioga = MediaPlayer.create(this,R.raw.a);
        audioge = MediaPlayer.create(this,R.raw.e);
        audiogi = MediaPlayer.create(this,R.raw.i);
        audiogo = MediaPlayer.create(this,R.raw.o);
        audiogu = MediaPlayer.create(this,R.raw.u);

        audiointro = MediaPlayer.create(this,R.raw.vocalsintro);

        //REPRODUCIR AUDIO DE INTRO
        audiointro.start();
        choose = "audiointro";

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
                                btn_anim.setBackgroundTintList(ContextCompat.getColorStateList(VocalesA.this,R.color.colorAccent));
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
                if (choose == "a"){
                    stopSound(choose);
                    audioa.start();
                    choose = "ga";
                }else if (choose == "e"){
                    stopSound(choose);
                    audioe.start();
                    choose = "ge";
                }else if (choose == "i"){
                    stopSound(choose);
                    audioi.start();
                    choose = "gi";
                }else if (choose == "o"){
                    stopSound(choose);
                    audioo.start();
                    choose = "go";
                }else if (choose == "u"){
                    stopSound(choose);
                    audiou.start();
                    choose = "gu";
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(VocalesA.this);
                builder.setIcon(R.drawable.info).
                        setMessage(information).
                        setTitle("Información:");
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

        //ESCUCHA BOTON SI A ES PULSADO
        Vocala.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI E ES PULSADO
        Vocale.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI I ES PULSADO
        Vocali.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI 0 ES PULSADO
        Vocalo.setOnClickListener(new View.OnClickListener() {
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
        //ESCUCHA BOTON SI U ES PULSADO
        Vocalu.setOnClickListener(new View.OnClickListener() {
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

    private void stopSound(String sound){
        if (sound == "a"){
            audioga.pause();
            audioga.seekTo(0);
        }else if (sound == "e"){
            audioge.pause();
            audioge.seekTo(0);
        }else if (sound == "i"){
            audiogi.pause();
            audiogi.seekTo(0);
        }else if (sound == "o"){
            audiogo.pause();
            audiogo.seekTo(0);
        }else if (sound == "u"){
            audiogu.pause();
            audiogu.seekTo(0);
        }else if (sound == "ga"){
            audioa.pause();
            audioa.seekTo(0);
        }else if (sound == "ge"){
            audioe.pause();
            audioe.seekTo(0);
        }else if (sound == "gi"){
            audioi.pause();
            audioi.seekTo(0);
        }else if (sound == "go"){
            audioo.pause();
            audioo.seekTo(0);
        }else if (sound == "gu"){
            audiou.pause();
            audiou.seekTo(0);
        }
    }

    private  void playAnimation(){
        try {

            if (animator == null || !animator.isRunning()) {
                if (choose == "e") {
                    audioge.start();
                    AnimationData data = e.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % e.getAnimationDataCount();
                    animator = new ModelAnimator(data, e);
                    animator.start();
                } else if (choose == "i") {
                    audiogi.start();
                    AnimationData data = i.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % i.getAnimationDataCount();
                    animator = new ModelAnimator(data, i);
                    animator.start();
                } else if (choose == "o") {
                    audiogo.start();
                    AnimationData data = o.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % o.getAnimationDataCount();
                    animator = new ModelAnimator(data, o);
                    animator.start();
                } else if (choose == "u") {
                    audiogu.start();
                    AnimationData data = u.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % u.getAnimationDataCount();
                    animator = new ModelAnimator(data, u);
                    animator.start();
                }else if (choose == "a") {
                    audioga.start();
                    AnimationData data = a.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation + 1) % a.getAnimationDataCount();
                    animator = new ModelAnimator(data, a);
                    animator.start();
                }

            }
        }catch (Exception e){

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

        //CARGA E
        ModelRenderable.builder()
                .setSource(this, R.raw.vocale)
                .build()
                .thenAccept(renderable -> e = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA I
        ModelRenderable.builder()
                .setSource(this, R.raw.vocali5)
                .build()
                .thenAccept(renderable -> i = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA O
        ModelRenderable.builder()
                .setSource(this, R.raw.vocalo4)
                .build()
                .thenAccept(renderable -> o = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA U
        ModelRenderable.builder()
                .setSource(this, R.raw.vocalu2)
                .build()
                .thenAccept(renderable -> u = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
        //CARGA A
        ModelRenderable.builder()
                .setSource(this, R.raw.a5)
                .build()
                .thenAccept(renderable -> a = renderable)
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
                choose = "a";
                andy.setRenderable(a);
                information = "A de avión.";
                audioga.start();
                validationBug();
                break;

            case 2:
                pushButton = true;
                stopSound(choose);
                choose = "e";
                andy.setRenderable(e);
                audioge.start();
                information = "E de escalera.";
                validationBug();
                break;
            case 3:
                pushButton = true;
                stopSound(choose);
                choose = "i";
                andy.setRenderable(i);
                audiogi.start();
                information = "I de iglesia.";
                validationBug();
                break;
            case 4:
                pushButton = true;
                stopSound(choose);
                choose = "o";
                andy.setRenderable(o);
                audiogo.start();
                information = "O de oso.";
                validationBug();
                break;
            case 5:
                pushButton = true;
                stopSound(choose);
                choose = "u";
                andy.setRenderable(u);
                audiogu.start();
                information = "U de uva.";
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
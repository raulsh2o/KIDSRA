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

public class NumerosA extends AppCompatActivity {
    //Variable
    private ArFragment arFragment;
    private AnchorNode anchorNode;
    private ModelAnimator animator;
    private int nextAnimation;
    private FloatingActionButton btn_anim;
    private ModelRenderable tigre, punto, cocodrilo, elefante, jirafa, mono, hipopotamo, leon, serpiente, cero, uno, dos, tres, cuatro;
    private TransformableNode transformableNode;

    private int clickNo = 0;
    private int Status1 = 0;
    private String information = "";
    private String choose = "";

    //DECLARAR AUDIOS
    MediaPlayer audioMonkey, audioLion, audioTiger, audioElephant, audioSerpent,audioJirafa, audioHipopo, audioCocodrile, audioi, audiogMonkey, audiogLion, audiogTiger, audiogElephant, audiogSerpent,audiogJirafa, audiogHipopo, audiogCocodrile, audio0, audio1, audio2, audio3, audio4,audio5,audio6,audio7,audio8,audio9,audio10;

    //*************************************************************
    private List<AnchorNode> anchorNodeList = new ArrayList<>();
    private AnchorNode currentSelectedAnchorNode = null;
    //*************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numeros);
        // DECLARA BOTONES DE CAMBIO DE OBJETO
        Button Ncero = (Button) findViewById(R.id.id_cero);
        Button Nuno = (Button) findViewById(R.id.id_uno);
        Button Ndos = (Button) findViewById(R.id.id_dos);
        Button Ntres = (Button) findViewById(R.id.id_tres);
        Button Ncuatro = (Button) findViewById(R.id.id_cuatro);
        Button Ncinco = (Button) findViewById(R.id.id_cinco);
        Button Nseis = (Button) findViewById(R.id.id_seis);
        Button Nsiete = (Button) findViewById(R.id.id_siete);
        Button Nocho = (Button) findViewById(R.id.id_ocho);
        Button Nnueve = (Button) findViewById(R.id.id_nueve);
        Button Ndiez = (Button) findViewById(R.id.id_diez);
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
                if(animator == null || !animator.isRunning())
                {
                    if (choose == "cero"){
                        AnimationData data = cero.getAnimationData(nextAnimation);
                        nextAnimation = (nextAnimation+1)%cero.getAnimationDataCount();
                        animator = new ModelAnimator(data,cero);
                        animator.start();
                    }
                    else if (choose == "uno"){
                        AnimationData data = uno.getAnimationData(nextAnimation);
                        nextAnimation = (nextAnimation+1)%uno.getAnimationDataCount();
                        animator = new ModelAnimator(data,uno);
                        animator.start();
                    }
                    else if (choose == "dos"){
                        AnimationData data = dos.getAnimationData(nextAnimation);
                        nextAnimation = (nextAnimation+1)%dos.getAnimationDataCount();
                        animator = new ModelAnimator(data,dos);
                        animator.start();
                    }
                    else if (choose == "tres"){
                        AnimationData data = tres.getAnimationData(nextAnimation);
                        nextAnimation = (nextAnimation+1)%tres.getAnimationDataCount();
                        animator = new ModelAnimator(data,tres);
                        animator.start();
                    }
                    else if (choose == "cuatro"){
                        AnimationData data = cuatro.getAnimationData(nextAnimation);
                        nextAnimation = (nextAnimation+1)%cuatro.getAnimationDataCount();
                        animator = new ModelAnimator(data,cuatro);
                        animator.start();
                    }

                }
            }
        });

        // BOTON DE INFORMACIÓN
        informacion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                AlertDialog.Builder builder = new AlertDialog.Builder(NumerosA.this);
                builder.setIcon(R.drawable.info).
                        setMessage(information).
                        setTitle("Información:");
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

        //ESCUCHA BOTON SI MONO ES PULSADO
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
        //ESCUCHA BOTON SI TIGRE ES PULSADO
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
        //ESCUCHA BOTON SI TIGRE ES PULSADO
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
        //ESCUCHA BOTON SI ELEFANTE ES PULSADO
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
        //ESCUCHA BOTON SI JIRAFA ES PULSADO
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
        //ESCUCHA BOTON SI JIRAFA ES PULSADO
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
        //ESCUCHA BOTON SI COCODRILO ES PULSADO
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



    }

    /////////////////////////////////////////////////////////////////////////////
    ////////////////////////////       FUNCIONES        /////////////////////////
    /////////////////////////////////////////////////////////////////////////////

    private void stopSound(String sound){
        if (sound == "cero"){
            audio0.pause();
        }else if (sound == "uno"){
            audio1.pause();
        }else if (sound == "dos"){
            audio2.pause();
        }else if (sound == "tres"){
            audio3.pause();
        }else if (sound == "cuatro"){
            audio4.pause();
        }else if (sound == "cinco"){
            audio5.pause();
        }else if (sound == "seis"){
            audio6.pause();
        }else if (sound == "siete"){
            audio7.pause();
        }else if (sound == "ocho"){
            audio8.pause();
        }else if (sound == "nueve"){
            audio9.pause();
        }else if (sound == "diez"){
            audio10.pause();
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
                choose = "cero";
                andy.setRenderable(cero);
                audio0.start();
                information = "El número cero es la rueda moscovita para pasarlo bien.";
                break;

            case 2:
                stopSound(choose);
                choose = "uno";
                andy.setRenderable(uno);
                audio1.start();
                information = "El número uno es un soldado haciendo la instrucción.";
                break;
            case 3:
                stopSound(choose);
                choose = "dos";
                andy.setRenderable(dos);
                audio2.start();
                information = "El número dos es un patito que está tomando el sol.";
                break;
            case 4:
                stopSound(choose);
                choose = "tres";
                andy.setRenderable(tres);
                audio3.start();
                information = "El número tres es una serpiente que baila sin parar.";
                break;
            case 5:
                stopSound(choose);
                choose = "cuatro";
                andy.setRenderable(cuatro);
                audio4.start();
                information = "El número cuatro es una sillita que invita a descansar.";
                break;
            case 6:
                stopSound(choose);
                choose = "cinco";
                //andy.setRenderable(cinco);
                audio5.start();
                information = "El número cinco tiene orejas, parece un conejito.";
                break;

            case 7:
                stopSound(choose);
                choose = "seis";
                //andy.setRenderable(seis);
                audio6.start();
                information = "El número seis es una pera redonda y con rabito.";
                break;
            case 8:
                stopSound(choose);
                choose = "siete";
                //andy.setRenderable(siete);
                audio7.start();
                information = "El número siete es un sereno con gorra y con bastón.";
                break;
            case 9:
                stopSound(choose);
                choose = "ocho";
                //andy.setRenderable(ocho);
                audio8.start();
                information = "El número ocho son las gafas que lleva don Ramón.";
                break;
            case 10:
                stopSound(choose);
                choose = "nueve";
                //andy.setRenderable(nueve);
                audio9.start();
                information = "El número nueve es un globito atado a un cordel.";
                break;

            case 11:
                stopSound(choose);
                choose = "diez";
                //andy.setRenderable(diez);
                audio10.start();
                information = "El número diez es un soldado que lleva un gran melón.";
                break;

            default:
                break;
        }


        newMarkAnchorNode.setParent(arFragment.getArSceneView().getScene());
        anchorNodeList.add(newMarkAnchorNode);
        return newMarkAnchorNode;



    }
}
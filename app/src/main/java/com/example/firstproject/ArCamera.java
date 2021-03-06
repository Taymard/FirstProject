package com.example.firstproject;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

public class ArCamera extends AppCompatActivity {

    private ArFragment arFragment;
    private String ASSET_3D = "pikachu.gltf";

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void OnCreated(Bundle savedInstancdState)
    {
        super.onCreate(savedInstancdState);
        setContentView(R.layout.activity_camera);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {
            placeModel(hitResult.createAnchor());
        }));
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeModel(Anchor anchor) {
        ModelRenderable
                .builder()
                .setSource(
                        this,
                        RenderableSource
                                .builder()
                                .setSource(this, Uri.parse(ASSET_3D),RenderableSource.SourceType.GLTF2)
                                .setScale(0.75f)
                                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                                .build()
                )
                .setRegistryId(ASSET_3D)
                .build()
                .thenAccept(modelRenderable -> addNodeToScene(modelRenderable,anchor))
                .exceptionally(throwable -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(throwable.getMessage()).show();
                    return null;
                });
    }

     private void addNodeToScene(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }
}

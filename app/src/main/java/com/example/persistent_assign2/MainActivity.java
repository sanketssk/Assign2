package com.example.persistent_assign2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    Button button;
    
    ImageView imageView;
    private static final int pic_id = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.camera);
        imageView=findViewById(R.id.imageView);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera_intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                activityResultLaunch.launch(camera_intent);
                startActivityForResult(camera_intent,pic_id);
            }

        });



    }

    private void saveGaller(){
        BitmapDrawable bitmapDrawable=(BitmapDrawable)imageView.getDrawable();
        Bitmap bitmap=bitmapDrawable.getBitmap();

        FileOutputStream outputStream=null;
//        File file= Environment.getExternalStorageDirectory();
        File file= new File(getApplicationContext().getFilesDir().getPath());

        File dir=new File(file.getAbsolutePath()+"/Persistent");
        dir.mkdir();

        String filename=String.format("%d.png",System.currentTimeMillis());
        File outFile=new File(dir,filename);
        try{
            outputStream=new FileOutputStream(outFile);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        try{
            outputStream.flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            outputStream.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    protected void onActivityResult(int requestCode,int resultCode,Intent data){


        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == pic_id) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);

        }

    }


}
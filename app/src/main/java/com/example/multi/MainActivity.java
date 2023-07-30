package com.example.multi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static int STORAGE_PERMISSION_CODE=1;
    private static int LOCATION_PERMISSION_CODE=2;
    private Button btn_select;
    private ImageButton prox;
    private ImageView visu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_select=(Button)findViewById(R.id.Selct);
        prox=findViewById(R.id.prox);
        visu=(ImageView)findViewById(R.id.imag);
        permissãoLocalização();
        permissãoArmazenamento();
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        });
    }
    public void proximo(View view){
     setContentView(R.layout.activity_main2);
    }
    @Override
    protected void
    onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
           // int collumnIndex1= c.getColumnIndex(filePath[1]);
            String picturePath = c.getString(columnIndex);
           // String picturePath1=c.getString(collumnIndex1);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
           // Bitmap thumbnail1=(BitmapFactory.decodeFile(picturePath));
            visu.setImageBitmap(thumbnail);
            //visu.setImageBitmap(thumbnail1);
        }
    }
    private void permissãoArmazenamento(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        }
    }
    private void permissãoLocalização(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},LOCATION_PERMISSION_CODE);
        }
    }
}
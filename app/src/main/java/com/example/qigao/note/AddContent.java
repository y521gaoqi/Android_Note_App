package com.example.qigao.note;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;
import java.util.Date;

public class AddContent extends AppCompatActivity implements View.OnClickListener {

    private String val;
    private Button savebtn,deletebtn;
    private EditText ettext;
    private ImageView c_img;
    private VideoView v_video;
    private NotesDB notesDB;
    private SQLiteDatabase dbWriter;
    private File phoneFile,videoFile;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcontent);
        val=getIntent().getStringExtra("flag");

        savebtn=(Button)findViewById(R.id.save);
        deletebtn=(Button)findViewById(R.id.delete);
        ettext=(EditText)findViewById(R.id.ettext);
        c_img=(ImageView)findViewById(R.id.c_img);
        v_video=(VideoView)findViewById(R.id.c_video);
        savebtn.setOnClickListener(this);
        deletebtn.setOnClickListener(this);
        notesDB=new NotesDB(this);
        dbWriter=notesDB.getWritableDatabase();
        initView();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void initView(){
        if(val.equals("1")){
            c_img.setVisibility(View.GONE);
            v_video.setVisibility(View.GONE);
        }
        if(val.equals("2")){
            c_img.setVisibility(View.VISIBLE);
            v_video.setVisibility(View.GONE);
            Intent iimg=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            phoneFile=new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/"+getTime()+".jpg");
            iimg.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(phoneFile));
            startActivityForResult(iimg,1);
        }
        if(val.equals("3")){
            c_img.setVisibility(View.GONE);
            v_video.setVisibility(View.VISIBLE);
            Intent video=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            videoFile=new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/"+getTime()+".mp4");
            video.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videoFile));
            startActivityForResult(video,2);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.save:
                addDB();
                finish();
                break;
            case R.id.delete:
                finish();
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public  void addDB(){
        ContentValues cv=new ContentValues();
        cv.put(NotesDB.CONTENT,ettext.getText().toString());
        cv.put(NotesDB.TIME,getTime());
        cv.put(NotesDB.PATH,phoneFile+"");
        cv.put(NotesDB.VIDEO,videoFile+"");

        dbWriter.insert(NotesDB.TABLE_NAME,null,cv);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getTime(){
        SimpleDateFormat format=new SimpleDateFormat("mm/dd/yyyy HH:mm:ss");
        Date date= new Date();
        String str=format.format(date);
        return str;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            Bitmap bitmap= BitmapFactory.decodeFile(phoneFile.getAbsolutePath());
            c_img.setImageBitmap(bitmap);

        }
        if (requestCode==2){
            v_video.setVideoURI(Uri.fromFile(videoFile));
            v_video.start();
        }
    }
}

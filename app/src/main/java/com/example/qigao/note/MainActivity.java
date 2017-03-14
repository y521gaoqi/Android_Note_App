package com.example.qigao.note;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.*;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*private NotesDB notesDB;
    private SQLiteDatabase dbWriter;*/

    private Button textbtn, imgbtn, videobtn;
    private ListView lv;
    private Intent i;
    private MyAdapter adapter;
    private NotesDB notesDB;
    private SQLiteDatabase dbReader;
    Cursor cursor;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        /*notesDB = new NotesDB(this);
        dbWriter = notesDB.getWritableDatabase();
        addDB();*/
    }

    public void initView() {
        lv = (ListView) findViewById(R.id.list);
        textbtn = (Button) findViewById(R.id.text);
        imgbtn = (Button) findViewById(R.id.img);
        videobtn = (Button) findViewById(R.id.video);
        textbtn.setOnClickListener(this);
        imgbtn.setOnClickListener(this);
        videobtn.setOnClickListener(this);
//当需要创建或打开一个数据库并获得数据库对象时，首先根据指定的文件名创建一个辅助对象，
// 然后调用该对象的getWritableDatabase 或 getReadableDatabase方法
// 获得SQLiteDatabase 对象。
        //调用getReadableDatabase 方法返回的并不总是只读数据库对象，一般来说该方法和getWriteableDatabase 方法的返回情况相同，
        // 只有在数据库仅开放只读权限或磁盘已满时才会返回一个只读的数据库对象。
        notesDB=new NotesDB(this);
        dbReader= notesDB.getReadableDatabase();
        //setOnItemClickListener applies for ListView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                Intent i=new Intent(MainActivity.this,SelectAct.class);
                i.putExtra(NotesDB.ID,cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));
                i.putExtra(NotesDB.CONTENT,cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT)));
                i.putExtra(NotesDB.TABLE_NAME,cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));
                i.putExtra(NotesDB.PATH,cursor.getString(cursor.getColumnIndex(NotesDB.PATH)));
                i.putExtra(NotesDB.VIDEO,cursor.getString(cursor.getColumnIndex(NotesDB.VIDEO)));
                startActivity(i);
            }
        });

    }





    @Override
    public void onClick(View v) {
        i = new Intent(this,AddContent.class);

        switch (v.getId()) {
            case R.id.text:
                i.putExtra("flag","1");
                startActivity(i);
                break;
            case R.id.img:
                i.putExtra("flag","2");
                startActivity(i);
                break;
            case R.id.video:
                i.putExtra("flag","3");
                startActivity(i);
                break;

        }

    }
    public void selectDB(){
        cursor=dbReader.query(NotesDB.TABLE_NAME,null,null,null,null,null,null);
        adapter=new MyAdapter(this,cursor);
        lv.setAdapter(adapter);

    }
    @Override
    protected void onResume(){
        super.onResume();
        selectDB();
    }
}



    /*@RequiresApi(api = Build.VERSION_CODES.N)
    public void addDB() {
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.CONTENT, "Hello");
        cv.put(NotesDB.TIME, getTime());
        dbWriter.insert(NotesDB.TABLE_NAME, null, cv);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
        Date curDate = new Date();
        String str = format.format(curDate);
        return str;
    }*/


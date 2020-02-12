package com.hit_src.iot_terminal.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hit_src.iot_terminal.object.Sensor;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static DatabaseOpenHelper databaseOpenHelper=new DatabaseOpenHelper(null,"iot",null,0);

    public Database(){

    }
    public List<Sensor> getSensorList(){
        SQLiteDatabase db=databaseOpenHelper.getReadableDatabase();
        Cursor cursor=db.query("Sensor", new String[]{"sensor_id", "sensor_type"},null,null,null,null,null);
        List<Sensor> res= new ArrayList<>();
        while(cursor.moveToNext()){
            res.add(new Sensor(cursor.getInt(0),cursor.getInt(1)));
        }
        cursor.close();
        return res;
    }
}
package com.etoak.db.Service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.etoak.db.bean.Person;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xm on 2015/1/8.
 */
public class PersonService {

    private DBOpenHelper dbOpenHelper;

    public PersonService(Context context) {
        this.dbOpenHelper = new DBOpenHelper(context);
    }

    //添加记录
    public void save(Person person){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("insert into person(name,phone) values(?,?)",new Object[]{person.getName(),person.getPhone()});
    }

    //删除记录
    public void delete(Integer id){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("delete from person where person_id=?",new Object[]{id});
    }

    //更新数据
    public void update(Person person){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("update person set name=? , phone = ? where person_id = ?",new Object[]{person.getName(),person.getPhone(),person.getPerson_id()});
    }
    //根据id获取
    public Person find(Integer id){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
         Cursor cursor =  db.rawQuery("select * from person where person_id = ?", new String[]{id.toString()});
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            Person person = new Person(id,name,phone);
            return person;
        }
        cursor.close();
        return  null ;
    }

    //分页获取记录
    public List<Person> getScrollData(int offset ,int maxResult){
        List<Person> persons = new ArrayList<Person>();
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select * from person order by person_id asc limit ?,?",
                new String[]{String.valueOf(offset),String.valueOf(maxResult)});
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("person_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            Person person = new Person(id,name,phone);
            persons.add(person);
        }

        cursor.close();
        return persons;

    }

    public Cursor getScrollDataCursor(int offset ,int maxResult){
        List<Person> persons = new ArrayList<Person>();
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select person_id as _id, name, phone  from person order by person_id asc limit ?,?",
                new String[]{String.valueOf(offset),String.valueOf(maxResult)});

       return cursor;

    }

    //获取记录条数
    public Long getCount(){
         SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
         Cursor cursor = db.rawQuery("select count(*) from person",null);
         cursor.moveToFirst();
         Long count = cursor.getLong(0);
        cursor.close();

        return count;
    }
}

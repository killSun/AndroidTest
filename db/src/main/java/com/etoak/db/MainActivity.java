package com.etoak.db;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.etoak.db.Service.PersonService;
import com.etoak.db.adapter.PersonAdapter;
import com.etoak.db.bean.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    private ListView personListView;
    private PersonService personService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        personListView = (ListView) this.findViewById(R.id.personListView);
        personService = new PersonService(this);
        personListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                Map<String,String> map = (Map<String, String>) listView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_SHORT);
                Toast.makeText(getApplicationContext(),map.get("person_id"),Toast.LENGTH_SHORT);

  /*              Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                int _id = cursor.getInt(cursor.getColumnIndex("_id"));

                Toast.makeText(getApplicationContext(),_id+"",Toast.LENGTH_SHORT).show();*/
/*
                Person person = (Person) listView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), person.getPerson_id() + "", Toast.LENGTH_LONG).show();
*/

            }
        });
        show1();
       // show2();
        //show3();


    }

    private void show3(){
        List<Person> persons = personService.getScrollData(0,20);
        BaseAdapter adapter = new PersonAdapter(persons,R.layout.person,this);
        personListView.setAdapter(adapter);

    }

    private void show2() {

        Cursor cursor = personService.getScrollDataCursor(0, 20);
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getApplicationContext(),
                                                                            R.layout.person,
                                                                            cursor,
                                                                            new String[]{"_id","name","phone"},
                                                                            new int[]{R.id.person_id,R.id.person_name,R.id.person_phone},
                                                                            SimpleAdapter.IGNORE_ITEM_VIEW_TYPE);
        personListView.setAdapter(simpleCursorAdapter);

    }

    private void show1() {
        List<Map<String, String>> data = new ArrayList<>();
        List<Person> persons = personService.getScrollData(0, 100);


        for (Person person : persons) {
            Map<String, String> map = new HashMap<>();
            Log.i("Person", person.toString());
            map.put("person_id", person.getPerson_id() + "");
            map.put("name", person.getName());
            map.put("phone", person.getPhone());
            data.add(map);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.person, new String[]{"person_id", "name", "phone"}, new int[]{R.id.person_id, R.id.person_name, R.id.person_phone});
        personListView.setAdapter(simpleAdapter);
    }


}

package com.etoak.pulltest;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.etoak.pulltest.bean.Person;
import com.etoak.pulltest.service.PersonService;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new TestThread();
        thread.start();

    }

    class TestThread extends Thread{

        @Override
        public void run() {
            List<Person> persons = null;
            try {
                persons = PersonService.getPersonFromXml(new FileInputStream(new File("/storage/sdcard0/person.xml")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            StringBuffer sb = new StringBuffer();
            for (Person person : persons){
                sb.append("Person : \n");
                sb.append(person.toString());
            }
            Log.i("pull",sb.toString());
        }
    }

}

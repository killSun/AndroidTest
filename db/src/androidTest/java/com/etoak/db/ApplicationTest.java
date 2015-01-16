package com.etoak.db;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.etoak.db.Service.DBOpenHelper;
import com.etoak.db.Service.PersonService;
import com.etoak.db.bean.Person;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void test(){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(getContext());
        dbOpenHelper.getWritableDatabase();
        dbOpenHelper.getWritableDatabase();
    }

    public void test2(){
        PersonService personService = new PersonService(getContext());
      /*  for(int i = 1;i<=20;i++){
            Person person = new Person("zhangsan"+i,(1388888888+i)+"");
            personService.save(person);
        }*/
        for (int i = 2; i < 21; i++) {
            personService.delete(i);
        }


      /*  Person person = personService.find(1);
        Log.i("Person",person.toString());

        person.setName("zhangxiaoxiao");
        personService.update(person);

        person = personService.find(1);
        Log.i("Person",person.toString());*/

        /*List<Person> persons = personService.getScrollData(0,5);
        for(Person person:persons){
            Log.i("Person",person.toString());
        }*/

       // personService.delete(1);

        Log.i("Person",personService.getCount()+"");

    }


}

package com.etoak.pulltest;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.etoak.pulltest.bean.Person;
import com.etoak.pulltest.service.PersonService;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    private static  final String TAG = "PullTest";

    //访问ContentProvider测试方法
    public void test() throws Exception {
        //1.获取能和ContentProvider匹配的uri
        Uri uri = Uri.parse("content://com.etoak.providers.personProvider/person");
        //2.使用contentResolver中的方法调用内容提供者中的方法
        ContentResolver contentResolver = this.getContext().getContentResolver();
        ContentValues values = new ContentValues();
        values.put("name","laoluo");
        values.put("phone","110");
        contentResolver.insert(uri,values);

    }

    //访问ContentProvider测试删除方法
    public void testDelete() throws Exception {
        //1.获取能和ContentProvider匹配的uri
        Uri uri = Uri.parse("content://com.etoak.providers.personProvider/person/21");
        //2.使用contentResolver中的方法调用内容提供者中的方法
        ContentResolver contentResolver = this.getContext().getContentResolver();
        contentResolver.delete(uri,null,null);
    }
}

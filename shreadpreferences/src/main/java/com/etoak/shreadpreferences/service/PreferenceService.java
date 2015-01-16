package com.etoak.shreadpreferences.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xm on 2015/1/8.
 */
public class PreferenceService {
    private Context context;

    public PreferenceService(Context context) {
        this.context = context;
    }

    /*
    *@param name 姓名
    * @param age 年龄
     */
    public void savePreference(String name, int age) {
        //1.获取ShreadPreferences对象 , 参数为 文件名,模式
        SharedPreferences pre = context.getSharedPreferences("etoak",Context.MODE_PRIVATE);
        //2.获取编辑器对象
        SharedPreferences.Editor editor  = pre.edit();
        //3.写入数据
        editor.putString("name",name);
        editor.putInt("age",age);
        //4.将内存中的数据写入到文件中
        editor.commit();
    }

    //
    public Map<String,String> getPreferemces(){
        Map<String,String> params = new HashMap<String,String>();
        SharedPreferences perferences = context.getSharedPreferences("etoak",context.MODE_PRIVATE);

        params.put("name",perferences.getString("name",null));
        params.put("age",perferences.getInt("age",0)+"");
        return params;
    }
}

package com.etoak.pulltest.service;

import android.util.Xml;


import com.etoak.pulltest.bean.Person;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xm on 2015/1/7.
 */
public class PersonService {

    public static List<Person> getPersonFromXml(InputStream xml) throws Exception{

        //1.得到解析器对象
        XmlPullParser pullParser = Xml.newPullParser();

        //2.设置解析内容
        pullParser.setInput(xml,"UTF-8");
        //解析文本内容
        int event = pullParser.getEventType();

        List<Person> Persons =null;
        Person Person = null;

        while (event != XmlPullParser.END_DOCUMENT){

            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    Persons = new ArrayList<Person>();
                    break;
                case XmlPullParser.START_TAG:
                    if ("Person".equals(pullParser.getName())){
                        Person = new Person();
                        Person.setId(Integer.parseInt(pullParser.getAttributeValue(0)));
                    }
                    if ("name".equals(pullParser.getName())){
                        Person.setName(pullParser.nextText());
                    }
                    if ("age".equals(pullParser.getName())){
                        Person.setAge(Integer.parseInt(pullParser.nextText()));
                    }

                    break;
                case XmlPullParser.END_TAG:
                    if ("Person".equals(pullParser.getName())){
                        Persons.add(Person);
                    }
                    break;

            }
        }




        return Persons;
    }


    /*
    * @param persons 要保存的person集合
    * @param out    输出流
    * */
    public void save(List<Person> persons,OutputStream out) throws Exception{

       //1获取XML序列化对象
        XmlSerializer serializer = Xml.newSerializer();
       //2.设置输出流
        serializer.setOutput(out,"UTF-8");
        //3.XML头内容
        serializer.startDocument("UTF-8",true);
        //4.元素开始标签
         serializer.startTag(null,"Persons");
        //根据数据向XML文档中插入内容
        for(Person person:persons){
            serializer.startTag(null,"person");
                serializer.attribute(null,"id",person.getId()+"");

                serializer.startTag(null,"name");
                serializer.text(person.getName());
                serializer.endTag(null,"name");

                serializer.startTag(null,"age");
                serializer.text(person.getAge()+"");
                serializer.endTag(null,"age");

            serializer.endTag(null,"person");
        }


        //设置元素结束标签
        serializer.endTag(null,"Persons");
        out.flush();
        out.close();
        return;
    }

}

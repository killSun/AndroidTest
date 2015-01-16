package com.etoak.db.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etoak.db.R;
import com.etoak.db.bean.Person;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by xm on 2015/1/9.
 */
public class PersonAdapter extends BaseAdapter {
    private List<Person> persons;
    private int resouce;
    //Android的布局填充器,可以通过XML生成界面
    private LayoutInflater inflater;

    public PersonAdapter(List<Person> persons ,int resouce,Context context) {
        this.persons = persons;
        this.resouce = resouce;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //得到记录总数
    @Override
    public int getCount() {
        return persons.size();
    }


    @Override
    public Object getItem(int position) {
        return persons.get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    //获取条目界面
    /*
    * @param position 当前帮定的数据在集合中的索引值
    *
    * */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView idTextView = null;
        TextView nameTextView = null;
        TextView phoneTextView = null;

        //convertView 缓存过的界面,当其等于null时说明是第一页
        if(convertView == null){
            //生成条目界面对象
           convertView = inflater.inflate(resouce,null);

           //如果是第一屏个给缓冲类赋值
            ViewCach viewCach = new ViewCach();
            viewCach.idTextView = (TextView) convertView.findViewById(R.id.person_id);
            viewCach.nameTextView = (TextView)convertView.findViewById(R.id.person_name);
            viewCach.phoneTextView = (TextView)convertView.findViewById(R.id.person_phone);
            //将视图缓冲对象放如ConvertView;
            convertView.setTag(viewCach);

        }else {
            //如果不是第一屏, 在convertView对象中获取缓存的视图对象
            ViewCach viewCach = (ViewCach) convertView.getTag();
            idTextView = viewCach.idTextView;
            nameTextView = viewCach.nameTextView;
            phoneTextView = viewCach.phoneTextView;
        }
        /*
          绑定数据
         */
        //获取textView

        //赋值
        Person person = persons.get(position);
        idTextView.setText(person.getPerson_id()+"");
        nameTextView.setText(person.getName());
        phoneTextView.setText(person.getPhone());



        return convertView;
    }

    //同过内部实现缓冲View对象 提高性能
    public final class ViewCach{
        public TextView idTextView;
        public TextView nameTextView;
        public TextView phoneTextView;
    }
}

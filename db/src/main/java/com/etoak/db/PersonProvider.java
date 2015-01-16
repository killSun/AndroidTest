package com.etoak.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.etoak.db.Service.DBOpenHelper;

import org.apache.http.client.utils.CloneUtils;

/**
 * Created by xm on 2015/1/9.
 */
public class PersonProvider extends ContentProvider {

    //主机名:
    private static final String AUTHORITIES = "com.etoak.providers.personProvider";

    private DBOpenHelper dbOpenHelper;
    //如果跟输入进的uri不匹配返回的数字,不匹配码
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    //定义添加匹配码
    private static final int INSERT_CODE = 1;

    //删除匹配码
    private static final int DELETE_CODE = 2;
    private static final int DELETE_ALL_CODE = INSERT_CODE;

    static {
        //主机名,路径,匹配成功返回的代码
        MATCHER.addURI(AUTHORITIES, "person", INSERT_CODE);
        MATCHER.addURI(AUTHORITIES, "person/#", DELETE_CODE);
    }

    //由系统调在这个ContentProvider实例被创建时调用
    //常用于数据的初始化
    @Override
    public boolean onCreate() {
        //实例化这个对象后创建dbOpenHelper对象
        dbOpenHelper = new DBOpenHelper(this.getContext());
        return true;
    }

    //供外部应用从ContentProvider中查询数据
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    //返回要操作数据的内容的类型
    @Override
    public String getType(Uri uri) {
        return null;
    }

    // 允许外部应用通过ContentProvider往Person表中添加数据
                /*
                * @param uri 需要操作的uri
                * @param ContentValues:存放各个字段的值
                * @return 新添加值代表的uri
                * */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //得到数据库实例
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        //判断uri是否合法,
        switch (MATCHER.match(uri)) {
            case INSERT_CODE:
                Long rowid = db.insert("person", "name", values); //主键值
                //手动拼接
                //Uri insertUri = Uri.parse("content://"+AUTHORITIES+"/person/"+rowid);
                //工具类
                Uri insertUri = ContentUris.withAppendedId(uri, rowid);
                return insertUri;

            default:
                //如果传入的uri不合法
                throw new IllegalArgumentException("this is unknown uri :" + uri);

        }
    }

    /*
    * @param selection where 后面的条件部分
    * @param selectionArgs 条件部分的参数
    * */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //得到数据库实例
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int cont = 0;
         switch (MATCHER.match(uri)){
             //根据条件删除
            case DELETE_ALL_CODE:
                cont = db.delete("person",selection,selectionArgs);
                break;
            //根据主键删除
             case DELETE_CODE:
                 long rowid = ContentUris.parseId(uri);
                 //组装id条件
                 String where = "person_id = " + rowid;
                 //判断有没有其他的条件
                 if(null != selection && !"".equals(selection)){
                     where += " and "+selection;
                 }
                 cont = db.delete("person",where,selectionArgs);
                 break;
             default:
                 //如果输入的uri不合法抛出异常
                 throw new IllegalArgumentException("this is unknown uri :" + uri);
        }
        return cont;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}

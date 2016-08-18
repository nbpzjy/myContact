package com.nbp.zjycontactpro.db.bean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjygzc on 16/8/13.
 */
public class DatabaseUtils {

    public final ContactSqliteOpenHelper sqliteOpenHelper;

    public DatabaseUtils (Context context){

        //为啥需要初始化呢,需要在本数据库上实现操作
        this.sqliteOpenHelper = new ContactSqliteOpenHelper(context);
    }

    //添加数据的操作
    public long addContact (Contact contact){

        //新建一个db对象，获取可写writable数据库.
        SQLiteDatabase db = sqliteOpenHelper.getWritableDatabase();
        //通过contentvalues对象，来做数据库添加
        ContentValues values = new ContentValues();
        values.put(ContactTableContract.ContactTableEntry.NAME_ENTRY,contact.name);
        values.put(ContactTableContract.ContactTableEntry.PHONE_NUMBER_ENTRY,contact.phoneNumber);
        values.put(ContactTableContract.ContactTableEntry.EMAIL_ID_ENTRY,contact.emailID);
        values.put(ContactTableContract.ContactTableEntry.QQ_NUMBER_ENTRY,contact.qqNumber);


        return db.insert(ContactTableContract.ContactTableEntry.TBNAME,
                "",values);


    }

    //查询数据的操作
    public List<Contact> searchContact (int count){
        //新建一个db对象，获取可读getReadableDatabase数据库.
        SQLiteDatabase db = sqliteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(ContactTableContract.ContactTableEntry.TBNAME,//表名
                new String[]{"*"},//获取说有字段
                "1==1 ",//过滤器
                new String[]{},
                "", //”“内没有空格
                "", //""内没有空格
                " " + ContactTableContract.ContactTableEntry.NAME_ENTRY+" asc" //由小到大asc，有大到小desc
        );

        //创建一个list
        List<Contact> list = new ArrayList<>();
        int i = 0;
        while (cursor.moveToNext()){

            if (i == count)
                break;
            int id = cursor.getInt(cursor.getColumnIndex(ContactTableContract.ContactTableEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactTableContract.ContactTableEntry.NAME_ENTRY));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactTableContract.ContactTableEntry.PHONE_NUMBER_ENTRY));
            String emailID = cursor.getString(cursor.getColumnIndex(ContactTableContract.ContactTableEntry.EMAIL_ID_ENTRY));
            String qqNumber = cursor.getString(cursor.getColumnIndex(ContactTableContract.ContactTableEntry.QQ_NUMBER_ENTRY));


            //以上面查询的数据建立一个contact对象
            Contact contact = new Contact(id,name,phoneNumber,emailID,qqNumber);
            list.add(contact); //把contact对象添加到list
            i++;

        }

        return list;//返回list


    }

    public long deleteContact(Contact contact){

        SQLiteDatabase db = sqliteOpenHelper.getWritableDatabase();
        return db.delete(ContactTableContract.ContactTableEntry.TBNAME,
                ContactTableContract.ContactTableEntry._ID+"=?",
                new String[]{""+contact.id}

        );




    }

    public long updateContact(Contact contact){

        //新建一个db对象，获取可写writable数据库.
        SQLiteDatabase db = sqliteOpenHelper.getWritableDatabase();
        //通过contentvalues对象，来做数据库修改
        ContentValues values = new ContentValues();

        values.put(ContactTableContract.ContactTableEntry.NAME_ENTRY,contact.name);
        values.put(ContactTableContract.ContactTableEntry.PHONE_NUMBER_ENTRY,contact.phoneNumber);
        values.put(ContactTableContract.ContactTableEntry.EMAIL_ID_ENTRY,contact.emailID);
        values.put(ContactTableContract.ContactTableEntry.QQ_NUMBER_ENTRY,contact.qqNumber);


        return db.update(ContactTableContract.ContactTableEntry.TBNAME,
                values,
                ContactTableContract.ContactTableEntry._ID+"=? ",
                new String[]{""+contact.id});



    }
}

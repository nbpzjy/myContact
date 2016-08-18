package com.nbp.zjycontactpro.db.bean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nbp.zjycontactpro.db.bean.Contact;
import com.nbp.zjycontactpro.db.bean.ContactTableContract;

/**
 * Created by zjygzc on 16/8/13.
 */
public class ContactSqliteOpenHelper extends SQLiteOpenHelper {

    //数据库名字
    public static String DBNAME = "zjy.nbp.20160812.db";

    //创建表SQL语句
    //只有表的主键可以自动增加，INTEGER PRIMARY KEY AUTOINCREMENT not null
    public String createdb = " "+
            "create table "+ ContactTableContract.ContactTableEntry.TBNAME+" (" +
            ContactTableContract.ContactTableEntry._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT not null, " +
            ContactTableContract.ContactTableEntry.NAME_ENTRY+" varchar(100) not null, " +
            ContactTableContract.ContactTableEntry.PHONE_NUMBER_ENTRY + " varchar(11) not null, " +
            ContactTableContract.ContactTableEntry.EMAIL_ID_ENTRY+" varchar(100), " +
            ContactTableContract.ContactTableEntry.QQ_NUMBER_ENTRY+" varchar(12)" +
            " )";
    //最后那里不能有“，”号

    //构造方法，初始化数据库
    public ContactSqliteOpenHelper(Context context) {

        /* Context上下文
        * name数据库名称
        * factor跟cursor游标有关的
        * version版本，升级标志，用于升级方法
        * */


        super(context, DBNAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据库
        sqLiteDatabase.execSQL(createdb);

        //接下来需要增加修改查看删除等等数据库的操作
    }

    String upgradeSql = "ALTER TABLE "+ContactTableContract.ContactTableEntry.TBNAME+
            " add COLUMN decription";

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /*升级数据库，用SQLite语句来做数据库升级 */
        db.execSQL(upgradeSql);

    }
}

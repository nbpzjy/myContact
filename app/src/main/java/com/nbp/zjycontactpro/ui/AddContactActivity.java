package com.nbp.zjycontactpro.ui;

import android.content.Intent;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nbp.zjycontactpro.db.bean.Contact;
import com.nbp.zjycontactpro.R;
import com.nbp.zjycontactpro.db.bean.DatabaseUtils;

public class AddContactActivity extends AppCompatActivity {

    EditText etAddName,etAddPhoneNumber,etAddEmailID,etAddQQ;
    Contact mContact = null;
    TextView textViewAddTopMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.add_contact_title);
        setContentView(R.layout.activity_add_contact);

        etAddName = (EditText) findViewById(R.id.et_add_name);
        etAddPhoneNumber = (EditText) findViewById(R.id.et_add_phonenumber);
        etAddEmailID = (EditText) findViewById(R.id.et_add_emailid);
        etAddQQ = (EditText) findViewById(R.id.et_add_qqnumber);
        textViewAddTopMessage = (TextView) findViewById(R.id.tv_add_top_message);

//        Intent intent = getIntent();
        if (getIntent().getSerializableExtra(MainActivity.CONTACT) != null){
//            setTitle(R.string.edit_contact_title);
            Intent intent = getIntent();
            mContact = (Contact)intent.getSerializableExtra(MainActivity.CONTACT);
            textViewAddTopMessage.setText(R.string.edit_contact_top_message);
            etAddName.setText(mContact.name);
            etAddPhoneNumber.setText(mContact.phoneNumber);
            etAddEmailID.setText(mContact.emailID);
            etAddQQ.setText(mContact.qqNumber);

        }

    }

    //设置右上角的确认按钮
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_contact, menu);//这里是调用menu文件夹中的main.xml，在登陆界面label右上角的三角里显示其他功能


        return true;
    }

    //设置点击事件的监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //在这里实现小钩钩的点击监听事件
        switch (item.getItemId()){
            case R.id.menu_save:
//                clickEvent();
                saveContact(null);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

//    public void clickEvent() {
//        Toast.makeText(this,"小勾勾被点击了啊哈！",Toast.LENGTH_SHORT).show();
//    }





    public void saveContact(View view){

        String name = etAddName.getText().toString();
        String phoneNumber = etAddPhoneNumber.getText().toString();
        String emailID = etAddEmailID.getText().toString();
        String qqNumber = etAddQQ.getText().toString();

        if (TextUtils.isEmpty(name.trim())||
                TextUtils.isEmpty(phoneNumber.trim())
                ){
            Toast.makeText(this,"Ops....好像有几个必填的忘记填了",Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseUtils utils = new DatabaseUtils(this);

        //这里来判断id为空的时候创建，而不为空的时候给除id以外的其他成员赋值
        if (mContact == null){

        //新建一个对象存储一个联系人的所有信息
        mContact = new Contact(name,phoneNumber,emailID,qqNumber);

        }else {

            //这里主要是为了能给在Contact不为空的时候，只给name，phone，emailid，qq等赋值，而不给id重新创建，否则就变成添加了
            mContact.name = name;
            mContact.phoneNumber = phoneNumber;
            mContact.emailID = emailID;
            mContact.qqNumber = qqNumber;

        }

        if (mContact.id == -1) {

        /* 接下来添加到数据库*/
            if (utils.addContact(mContact) == -1) {
                Toast.makeText(this, "不好意思，添加失败了！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "牛逼，添加成功了！", Toast.LENGTH_SHORT).show();
                finish();
            }
        }else {


            if (utils.updateContact(mContact) == -1) {
                Toast.makeText(this, "不好意思，保存失败了！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "真牛逼，修改成功了！", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}

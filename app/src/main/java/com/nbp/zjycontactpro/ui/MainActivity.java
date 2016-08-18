package com.nbp.zjycontactpro.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nbp.zjycontactpro.R;
import com.nbp.zjycontactpro.db.bean.Contact;
import com.nbp.zjycontactpro.db.bean.DatabaseUtils;
import com.nbp.zjycontactpro.ui.AddContactActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    MyAdapter myAdapter;
    public static final String CONTACT = "联系人";
    DatabaseUtils utils = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        /* 需要适配器*/

        //设置listview单击点击事件
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Contact contact = (Contact)myAdapter.getItem(position);
//               mt("我被点到了哎！我是： "+contact.toString());
//               mt(contact.toString());
               Intent intent = new Intent(MainActivity.this,AddContactActivity.class);
               intent.putExtra(CONTACT, contact);
               startActivity(intent);
           }
       });


        //长按事件监听
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final Contact contact = (Contact) myAdapter.getItem(position);
                mt("哇塞，我被长按了一回哎哈哈哈！"+contact.toString());


                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示：")
                        .setMessage("确定要删除 "+contact.name+ "么？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deletFromDb(contact);

                                    }
                                })
                        .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;

                    }
                }).show();

                //deletFromDb(contact);
                return true;
            }


        });



        //添加按钮的事件
//        Button btn_add = (Button) findViewById(R.id.btn_main_add);
//        btn_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //mt("添加");
//                addContact(view);
//            }
//        });

        //查询按钮的事件
//        Button btn_search = (Button) findViewById(R.id.btn_main_search);
//        btn_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //mt("查询");
//                searchContact();
//            }
//        });

        searchContact(null);

    }

    //删除数据方法
    private void deletFromDb(Contact contact) {
        if (utils == null)
        utils = new DatabaseUtils(this);
        long l = utils.deleteContact(contact);
        if (l == 1) {
            myAdapter.removeContact(contact);
            mt(contact.name+ " 被成功删除了！");
        }
        else if (l == 0){
            mt(contact.name+ " 删除失败啦！");
        }


    }

    //调用onreasume来更新listview的显示数据
    @Override
    protected void onResume() {
        super.onResume();
        searchContact(null);

    }

    //设置右上角的确认按钮
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_to_add, menu);//这里是调用menu文件夹中的main.xml，在登陆界面label右上角的三角里显示其他功能


        return true;
    }



    //设置点击事件的监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        //在这里实现添加的点击监听事件
        switch (item.getItemId()){
            case R.id.goto_add:
                addContact();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    //添加listviewitem点击事件



    //封装Toast
    private void mt(String mess) {

        Toast.makeText(this,mess,Toast.LENGTH_SHORT).show();
    }

    public void addContact (){
       // Toast.makeText(this,"你点击了添加Contact",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,AddContactActivity.class);
        startActivity(intent);
    }

    public void searchContact(View view) {
        //Toast.makeText(this,"你点击了searchContact",Toast.LENGTH_SHORT).show();
        DatabaseUtils utils = new DatabaseUtils(this);
        final List<Contact> contacts = utils.searchContact(-1);//判断时是i=count，如果这里设置为-1，那么DataUtils中的count为-1，永远不等于i，所以返回所有值

        //for (int i = 0; i < contacts.size(); i++){
            //Log.d("------xxxx------",contacts.get(i).toString());

        if (myAdapter == null){

            myAdapter = new MyAdapter(this,contacts);
            listView.setAdapter(myAdapter);

        }else {
            myAdapter.setData(contacts);
            myAdapter.notifyDataSetChanged();
        }
    }

    class MyAdapter extends BaseAdapter{


        private final Context context;
        private List<Contact> data;
        LayoutInflater inlfater;

        public MyAdapter(Context context, List<Contact> data) {

            this.context = context;
            this.data = data;
            inlfater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertview, ViewGroup viewGroup) {
            //返回视图

            Contact contact = (Contact) getItem(i);

            ViewHolder holder = null;
            if (convertview == null){
                convertview = inlfater.inflate(R.layout.item_layout, viewGroup, false);
                holder = new ViewHolder(convertview);
            }else {

                holder = (ViewHolder) convertview.getTag();
            }
            //修改视图内容
            holder.itemTvName.setText(contact.name);
            holder.itemTvPhoneNumber.setText(contact.phoneNumber);
            holder.itemTvEmailID.setText("EmailID: "+contact.emailID);
            holder.itemTvQQNumber.setText("QQ: " + contact.qqNumber);


            convertview.setTag(holder);
            return convertview;
        }

        public void setData(List<Contact> contacts){

            this.data = contacts;
        }

        //在adapter中操作listview
        public void removeContact(Contact contact) {
            for (int i = 0; i <data.size() ; i++) {
                if (data.get(i).id == contact.id){
                    data.remove(i);
                    notifyDataSetChanged();
                    break;
                }

            }

        }

        public class ViewHolder {
            public TextView itemTvName,itemTvPhoneNumber,itemTvEmailID,itemTvQQNumber;


            public ViewHolder(View view){ //这里多了一个public void就会出错哦

                itemTvName = (TextView) view.findViewById(R.id.item_tv_name);
                itemTvPhoneNumber = (TextView) view.findViewById(R.id.item_tv_phone_number);
                itemTvEmailID = (TextView) view.findViewById(R.id.item_tv_email_id);
                itemTvQQNumber = (TextView) view.findViewById(R.id.item_tv_qq_number);

            }

        }
    }
}

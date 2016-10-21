package mail.zhou.du.mail;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhou on 16/7/7.
 * 联系人管理
 */
public class ContactsActivity extends AppCompatActivity{
    private String TAG = "ContactsActivity";
    private Context mContext = this;
    ListView mListView;
    List<Contact> mContactList = new ArrayList<>();
    ContactListAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        findView();
        initToolbar();
        initData();
    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void findView(){
        mListView = (ListView) findViewById(R.id.list);
        adapter = new ContactListAdapter(this, mContactList);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e(TAG,"setOnItemClickListener:"+i);
                CheckedTextView checkedTextView = (CheckedTextView)view;
                checkedTextView.setChecked(!checkedTextView.isChecked());
                ContactsManager.get().updateContact(mContactList.get(i).getId(),checkedTextView.isChecked());
                initData();
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("确认删除？");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ContactsManager.get().deleteContact(mContactList.get(position).getId());
                        initData();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.setCancelable(true);
                builder.show();
                return true;
            }
        });
    }

    private void initData(){
        mContactList.clear();
        mContactList.addAll(ContactsManager.get().getAllContacts());
        adapter.notifyDataSetChanged();
    }

    public void addNewContact(final View view){
        final EditText editText = new EditText(this);
        editText.setHint("someone@lifang.com");
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("添加联系人邮箱");
        builder.setView(editText, 10, 10, 10, 10);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String emailAddress = editText.getText().toString().trim();
                if (TextUtils.isEmpty(emailAddress) == false && emailAddress.matches(Patterns.EMAIL_ADDRESS.pattern())){
                    ContactsManager.get().insertContact(emailAddress);
                    initData();
                }else {
                    Snackbar.make(view,"邮箱数据不合法",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

}

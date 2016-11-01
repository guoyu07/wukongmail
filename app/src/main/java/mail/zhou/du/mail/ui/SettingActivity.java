package mail.zhou.du.mail.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import mail.zhou.du.mail.util.ContactsManager;
import mail.zhou.du.mail.R;
import mail.zhou.du.mail.util.UserManager;

/**
 * Created by zhou on 16/7/6.
 */
public class SettingActivity extends AppCompatActivity {
    private Context mContext = this;
    TextView usernameTv, passwordTv ,takeOverTv , phoneNumberTv , nickNameTv , contactTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findView();
        initData();
        initToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    private void findView() {
        usernameTv = (TextView) findViewById(R.id.tv_username);
        passwordTv = (TextView) findViewById(R.id.tv_password);
        takeOverTv = (TextView) findViewById(R.id.tv_takeover_people);
        phoneNumberTv = (TextView) findViewById(R.id.tv_phonenumber);
        nickNameTv = (TextView) findViewById(R.id.tv_nickname);
        contactTv = (TextView) findViewById(R.id.tv_contacts);
    }

    private void initData() {
        usernameTv.setText(UserManager.get(this).getUserName());
        passwordTv.setText(UserManager.get(this).getPassword());
        takeOverTv.setText(UserManager.get(this).getTakeOver());
        phoneNumberTv.setText(UserManager.get(this).getPhoneNumber());
        nickNameTv.setText(UserManager.get(this).getNickName());
        contactTv.setText("已选择"+ ContactsManager.get().getCheckedContacts().size()+"个");
    }

    public void changeUsername(View view) {
        final EditText editText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("设置邮箱");
        builder.setView(editText, 10, 10, 10, 10);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UserManager.get(mContext).setUserName(editText.getText().toString().trim());
                initData();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    public void changePassword(View view) {
        final EditText editText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("设置密码");
        builder.setView(editText, 10, 10, 10, 10);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UserManager.get(mContext).setPassword(editText.getText().toString().trim());
                initData();
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    /**
     * 工作交接人
     */
    public void changeTakeOverPeople(View view) {
        //tv_takeover_people
        final EditText editText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("设置工作交接人");
        builder.setView(editText, 10, 10, 10, 10);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UserManager.get(mContext).setTakeOver(editText.getText().toString().trim());
                initData();
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    //tv_phonenumber
    public void changePhoneNumber(View view) {
        final EditText editText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("设置联系电话");
        builder.setView(editText, 10, 10, 10, 10);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UserManager.get(mContext).setPhoneNumber(editText.getText().toString().trim());
                initData();
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    public void changeNickName(View view) {
        //tv_nickname
        final EditText editText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("设置姓名");
        builder.setView(editText, 10, 10, 10, 10);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UserManager.get(mContext).setNickName(editText.getText().toString().trim());
                initData();
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    public void changeContacts(View view){
        startActivity(new Intent(this,ContactsActivity.class));
    }


}

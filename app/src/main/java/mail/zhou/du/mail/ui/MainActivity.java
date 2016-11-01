package mail.zhou.du.mail.ui;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import mail.zhou.du.mail.util.ContactsManager;
import mail.zhou.du.mail.util.MailSender;
import mail.zhou.du.mail.R;
import mail.zhou.du.mail.util.UserManager;

public class MainActivity extends AppCompatActivity {
    private Context mContext = this;
    public static final String TAG = "MainActivity";

    EditText dayEditText, hourEditText;
    TextView startDateTV , startTimeTV , endDateTV , endTimeTV;
    RadioGroup typeRadioGroup;

    public Calendar startCalendar = Calendar.getInstance();
    public Calendar endCalendar = Calendar.getInstance();

    public @StringRes int typeName = R.string.type_tiaoxiu;

    public static final String KEY_TYPE_NAME = "##KEY_TYPE_NAME##";
    public static final String KEY_DATE = "##KEY_DATE##";
    public static final String KEY_START_DATE = "##KEY_START_DATE##";
    public static final String KEY_END_DATE = "##KEY_END_DATE##";
    public static final String KEY_LEAVE = "##KEY_LEAVE##";
    public static final String KEY_NICKNAME = "##KEY_NICKNAME##";
    public static final String KEY_PHONE_NUMBER = "##KEY_PHONE_NUMBER##";
    public static final String KEY_TAKEORVER = "##KEY_TAKEORVER##";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
    }

    private void findView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dayEditText = (EditText) findViewById(R.id.ed_day);
        hourEditText = (EditText) findViewById(R.id.ed_hour);
        startDateTV = (TextView) findViewById(R.id.tv_start_date);
        startTimeTV = (TextView) findViewById(R.id.tv_start_time);
        endDateTV = (TextView) findViewById(R.id.tv_end_date);
        endTimeTV = (TextView) findViewById(R.id.tv_end_time);
        typeRadioGroup = (RadioGroup) findViewById(R.id.radio_group_type);
        typeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                if (checkId == R.id.radio_qingjia)
                    typeName = R.string.type_qingjia;
                else
                    typeName = R.string.type_tiaoxiu;
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext).setTitle("确认发送邮件吗?").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendMail();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }

    private void initView(){
        startDateTV.setText(startCalendar.get(Calendar.YEAR) + "年" + (startCalendar.get(Calendar.MONTH) + 1) + "月" + startCalendar.get(Calendar.DAY_OF_MONTH) + "日");
        startTimeTV.setText(startCalendar.get(Calendar.HOUR_OF_DAY) + "时" + startCalendar.get(Calendar.MINUTE) + "分");
        endDateTV.setText(endCalendar.get(Calendar.YEAR) + "年" + (endCalendar.get(Calendar.MONTH) + 1) + "月" + endCalendar.get(Calendar.DAY_OF_MONTH) + "日");
        endTimeTV.setText(endCalendar.get(Calendar.HOUR_OF_DAY) + "时" + endCalendar.get(Calendar.MINUTE) + "分");
        String username = UserManager.get(mContext).getUserName();
        if (TextUtils.isEmpty(username)){
            setTitle("当前未登录");
        }else {
            setTitle(username);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingActivity.class));
            return true;
        }else if(id == R.id.action_about){
            startActivity(new Intent(this,AboutActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void chooseStartDate(View view) {
        showDateDialog(R.id.tv_start_date);
    }

    public void chooseEndDate(View view) {
        showDateDialog(R.id.tv_end_date);
    }

    public void chooseStartTime(View view) {
        showTimeDialog(R.id.tv_start_time);
    }

    public void chooseEndTime(View view) {
        showTimeDialog(R.id.tv_end_time);
    }

    public void showDateDialog(@IdRes final int viewId) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int data) {
                Log.e(TAG, "日期选择 ：" + year + "--" + month + "--" + data);
                TextView textView = (TextView) findViewById(viewId);
                textView.setText(year + "年" + (month + 1) + "月" + data + " 日");
                if (R.id.tv_start_date == viewId) {
                    startCalendar.set(Calendar.YEAR, year);
                    startCalendar.set(Calendar.MONTH, month);
                    startCalendar.set(Calendar.DAY_OF_MONTH, data);
                } else {
                    endCalendar.set(Calendar.YEAR, year);
                    endCalendar.set(Calendar.MONTH, month);
                    endCalendar.set(Calendar.DAY_OF_MONTH, data);
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void showTimeDialog(@IdRes final int viewId) {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Log.e(TAG, "时间选择 ：" + hour + "--" + minute);
                TextView textView = (TextView) findViewById(viewId);
                textView.setText(hour + "时" + minute + "分");
                if (R.id.tv_start_time == viewId) {
                    startCalendar.set(Calendar.HOUR_OF_DAY, hour);
                    startCalendar.set(Calendar.MINUTE, minute);
                } else {
                    endCalendar.set(Calendar.HOUR_OF_DAY, hour);
                    endCalendar.set(Calendar.MINUTE, minute);
                }
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

    }



    private void sendMail() {

        String[] sendAddress = ContactsManager.get().getSendMailAddress();
        if (sendAddress == null || sendAddress.length == 0){
            Snackbar.make(findViewById(R.id.fab), "没有收件人，请到去联系人页面管理", Snackbar.LENGTH_LONG).setAction("GO!", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(mContext,ContactsActivity.class));
                }
            }).show();
            return;
        }

        String username = UserManager.get(mContext).getUserName();
        String password = UserManager.get(mContext).getPassword();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            startActivity(new Intent(mContext,SettingActivity.class));
            return;
        }

        if (endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis() <= 0) {
            Snackbar.make(findViewById(R.id.fab), "开始时间或结束时间不合法", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (dayEditText.getText().toString().trim().length() == 0)
            dayEditText.setText("0");
        if (hourEditText.getText().toString().trim().length() == 0)
            hourEditText.setText("0");
        int day = Integer.valueOf(dayEditText.getText().toString().trim());
        int hour = Integer.valueOf(hourEditText.getText().toString().trim());
        if (day < 0 || hour < 0) {
            Snackbar.make(findViewById(R.id.fab), "请假时间不合法", Snackbar.LENGTH_SHORT).show();
            return;
        }
        String html = getString(R.string.html);
        html = html.replace(KEY_DATE, getCurrentDate());
        html = html.replace(KEY_START_DATE, getFormatDateTime(startCalendar));
        html = html.replace(KEY_END_DATE, getFormatDateTime(endCalendar));
        html = html.replace(KEY_LEAVE, getFormatLeaveDateTime());
        html = html.replace(KEY_NICKNAME,UserManager.get(mContext).getNickName());
        html = html.replace(KEY_PHONE_NUMBER,UserManager.get(mContext).getPhoneNumber());
        html = html.replace(KEY_TAKEORVER,UserManager.get(mContext).getTakeOver());
        html = html.replace(KEY_TYPE_NAME,getString(typeName));
        new Thread(new SendMailRunnable(getString(typeName), html)).start();
    }

    class SendMailRunnable implements Runnable {
        String subject = null;
        String text = null;

        public SendMailRunnable(String subject, String text) {
            this.subject = subject;
            this.text = text;
        }

        @Override
        public void run() {
            try {
                new MailSender()
                        .useMailPropertiesLiFang()
                        .setCredentials(UserManager.get(mContext).getUserName(), UserManager.get(mContext).getPassword())
                        .setSubject(subject)
                        .setMailText(text)
//                        .setToAddresses(new String[]{"369178391@qq.com"})
                        .setToAddresses(ContactsManager.get().getSendMailAddress())
                        .setFromName(UserManager.get(mContext).getNickName())
                        .send();
                Snackbar.make(findViewById(R.id.fab), "邮件已发送", Snackbar.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Snackbar.make(findViewById(R.id.fab), "邮件发送失败", Snackbar.LENGTH_SHORT).show();
            }

        }
    }


    /**
     * 得到当前日期
     */
    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日";
    }

    /**
     * 得到格式化时间
     */
    private String getFormatDateTime(Calendar calendar) {
        return calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日" + calendar.get(Calendar.HOUR_OF_DAY) + "时" + calendar.get(Calendar.MINUTE) + "分";
    }

    /**
     * 请假时间的格式化数据
     */
    private String getFormatLeaveDateTime() {
        String day = dayEditText.getText().toString().trim();
        String hour = hourEditText.getText().toString().trim();
        return day + "天" + hour + "小时";
    }


}

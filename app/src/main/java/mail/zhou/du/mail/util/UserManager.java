package mail.zhou.du.mail.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhou on 16/7/6.
 */
public class UserManager {
    private static UserManager instance;
    private Context mContext;
    SharedPreferences mSharedPreferences;

    private String SP_NAME = "data_sp";

    private String KEY_USERNAME = "username";
    private String KEY_PASSWORD = "password";
    private String KEY_NICKNAME = "nickname";
    private String KEY_TAKEOVER = "takeover";
    private String KEY_PHONE_NUMBER = "phone_number";

    private UserManager(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static UserManager get(Context mContext) {
        if (instance == null)
            instance = new UserManager(mContext);
        return instance;
    }


    public void setUserName(String username) {
        mSharedPreferences.edit().putString(KEY_USERNAME, username).commit();
    }

    public String getUserName() {
        return mSharedPreferences.getString(KEY_USERNAME, "");
    }

    public void setPassword(String password) {
        mSharedPreferences.edit().putString(KEY_PASSWORD, password).commit();
    }

    public String getPassword() {
        return mSharedPreferences.getString(KEY_PASSWORD, "");
    }

    public void setNickName(String nickName){
        mSharedPreferences.edit().putString(KEY_NICKNAME, nickName).commit();
    }
    public String getNickName(){
        return mSharedPreferences.getString(KEY_NICKNAME,"");
    }

    public void setTakeOver(String takeOver){
        mSharedPreferences.edit().putString(KEY_TAKEOVER, takeOver).commit();
    }
    public String getTakeOver(){
        return mSharedPreferences.getString(KEY_TAKEOVER,"");
    }

    public void setPhoneNumber(String phoneNumber){
        mSharedPreferences.edit().putString(KEY_PHONE_NUMBER, phoneNumber).commit();
    }
    public String getPhoneNumber(){
        return mSharedPreferences.getString(KEY_PHONE_NUMBER,"");
    }


}

package mail.zhou.du.mail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

/**
 * Created by zhou on 16/7/6.
 */
public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.mipmap.ic_launcher)
                .setDescription("悟空找房请假调休客户端 \n 它帮你发调休单,请假单。have a try！！！")
                .addItem(new Element().setTitle("Version "+BuildConfig.VERSION_NAME) )
                .addGroup("Connect with us")
                .addEmail("zhouguobao@lifang.com")
                .addWebsite("http://www.cnblogs.com/zhou-guobao/")
//                .addFacebook("#")
//                .addTwitter("#")
//                .addYoutube("#")
//                .addPlayStore("#")
                .addGitHub("zhouzidan")
//                .addInstagram("#")
                .create();
        setContentView(aboutPage);
    }


}

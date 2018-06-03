package tg.geek228.supfile.Activities;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import tg.geek228.supfile.R;

public class UserProfileActivity extends AppCompatActivity {

    private SharedPreferences pref;

    private TextView textView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_profile_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Mon compte");
        setSupportActionBar(toolbar);

        pref = getApplicationContext().getSharedPreferences("USERSETTING", 0);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        textView = (TextView) findViewById(R.id.user_name);

        textView.setText(pref.getString("username",null));

        textView.setTextSize(30);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextColor(getApplicationContext().getColor(R.color.white));
        }


    }
}

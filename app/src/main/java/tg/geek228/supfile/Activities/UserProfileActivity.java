package tg.geek228.supfile.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tg.geek228.supfile.Activities.Login.LoginActity;
import tg.geek228.supfile.Core.Networking.Manager.NetworkManager;
import tg.geek228.supfile.R;

public class UserProfileActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView textView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_profile_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Mon compte");
        setSupportActionBar(toolbar);

        pref = getApplicationContext().getSharedPreferences("USERSETTING", 0);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.user_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.disconnect) {

            NetworkManager networkManager = new NetworkManager();

            networkManager.getService().logout().enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    SharedPreferences.Editor editor = pref.edit();

                    editor.clear();

                    editor.commit();

                    LoginManager.getInstance().logOut();

                    mGoogleSignInClient.signOut();

                    startActivity(new Intent(UserProfileActivity.this, LoginActity.class));
                    System.out.println("user is loged out ... ");
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }

        return super.onOptionsItemSelected(item);
    }
}

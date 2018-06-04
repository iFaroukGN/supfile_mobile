package tg.geek228.supfile;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tg.geek228.supfile.Activities.UserProfileActivity;
import tg.geek228.supfile.Adapter.ViewPagerAdapter;
import tg.geek228.supfile.Core.Networking.Manager.NetworkManager;
import tg.geek228.supfile.Model.User;

public class MainActivity extends AppCompatActivity {

    public ViewPager viewPager;
    public ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null

        /*NetworkManager networkManager = new NetworkManager();

        Call<List<User>> request = networkManager.getService().users();
        request.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                System.out.println(" result response... "+response);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

                System.out.println(" result message ... "+t.getMessage());
            }
        });


        User u = new User("tata", "tata@supfile.com", "", "" ,"tata");

        Call<User> testPost = networkManager.getService().registrationWithEmail(u);
        testPost.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                    System.out.println(" post result response... " + response.raw());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                System.out.println(" post result error... " + t.getMessage());
            }
        });


        User u2 = new User("test@supfile.com","test");
        Call<User> login = networkManager.getService().login(u2);
        login.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                System.out.println(" login result response... " + response.body().getToken());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(" login result response... " + t.getMessage());
                }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.userIcon) {

            startActivity(new Intent(MainActivity.this,UserProfileActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}

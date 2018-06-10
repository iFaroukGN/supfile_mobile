package tg.geek228.supfile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tg.geek228.supfile.Activities.UserProfileActivity;
import tg.geek228.supfile.Adapter.CustomAdapter;
import tg.geek228.supfile.Core.Networking.Manager.NetworkManager;
import tg.geek228.supfile.Core.Networking.Service.RessourceService;
import tg.geek228.supfile.Decorator.GridSpacingItemDecoration;
import tg.geek228.supfile.Model.Ressource;
import tg.geek228.supfile.Model.User;
import tg.geek228.supfile.Utils.FileUtils;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_WRITE_STORAGE = 10;
    private static final int RC_PHOTO_PICKER = 1000;
    public ViewPager viewPager;
    private RecyclerView recyclerView;
    FloatingActionButton addFile;
    private Uri imgUri;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pref = getApplicationContext().getSharedPreferences("USERSETTING", 0);
        addFile = (FloatingActionButton) findViewById(R.id.file_add);
        this.clickOnAddFile();

        FloatingActionButton addFolder = (FloatingActionButton) findViewById(R.id.folder_add);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null

        NetworkManager networkManager = new NetworkManager();


        GridLayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 2);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);

        CustomAdapter adapter = new CustomAdapter(this, this.generateData());

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(this, 2, dpToPx(2), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
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



    public ArrayList<Ressource> generateData(){


        ArrayList<Ressource> ressourceArrayList = new ArrayList<>();


        Ressource r1 = new Ressource(1,"test file ","path");
        Ressource r2 = new Ressource(2,"test file 2 ","path");
        Ressource r3 = new Ressource(3,"test file 3","path");
        Ressource r4 = new Ressource(4,"test file 4","path");
        Ressource r5 = new Ressource(5,"test file 5","path");
        Ressource r6 = new Ressource(6,"test file 6","path");

        ressourceArrayList.add(r1);
        ressourceArrayList.add(r2);
        //ressourceArrayList.add(r3);
        //ressourceArrayList.add(r4);
        //ressourceArrayList.add(r5);
//        ressourceArrayList.add(r6);

        return ressourceArrayList;
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    private void  selectFile(){

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            System.out.println("if la version de l'api est: " + android.os.Build.VERSION.SDK_INT);
            addFile.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
                }
            });

        } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            System.out.println("else if la version de l'api est: " + android.os.Build.VERSION.SDK_INT);
            checkPermissionIsGrantOrNot();
        }  else {
            System.out.println("else la version de l'api est: " + android.os.Build.VERSION.SDK_INT);
            addFile.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
                }
            });

        }
    }

    public void checkPermissionIsGrantOrNot() {
        if ((ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) &&
                ((ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this).setTitle(R.string.askPermTitle).setMessage(R.string.RWPermission).show();
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_WRITE_STORAGE);
                }
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            addFile.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    addFile.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("*/*");
                            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                            startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
                        }
                    });

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    // Permission Denied
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    new AlertDialog.Builder(this).setTitle(R.string.askPermTitle).setMessage(R.string.RWPermissionDenied).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public void clickOnAddFile(){
        this.selectFile();
        addFile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            imgUri = data.getData();


            File file = FileUtils.getFile(this, imgUri);
            //RequestBody fbody = RequestBody.create(MediaType.parse("*/*"), file);
            //MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("*/*"), file+".png"));

            NetworkManager networkManager = new NetworkManager();


            // create RequestBody instance from file
            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse(getContentResolver().getType(imgUri)),
                            file
                    );


            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);


            // add another part within the multipart request
            String userID = pref.getString("userID",null);
            RequestBody description =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, userID);

            Call<String> ressource = networkManager.getRetrofit().create(RessourceService.class).sendFile("Bearer "+pref.getString("accessToken",null),requestFile,userID);

            System.out.println("response token ...  "+"Bearer "+pref.getString("accessToken",null));

            ressource.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    System.out.println("response ... "+response);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    System.out.println("response error  ... "+t.getMessage());
                }
            });
        }
    }
}


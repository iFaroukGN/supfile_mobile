package tg.geek228.supfile.Activities.Login;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tg.geek228.supfile.Core.Networking.Manager.NetworkManager;
import tg.geek228.supfile.MainActivity;
import tg.geek228.supfile.Model.User;
import tg.geek228.supfile.R;

/**
 * Created by farouk on 21/04/2018.
 */

public class LoginActity extends AppCompatActivity implements View.OnClickListener{

    private static final int RC_SIGN_IN = 1000;
    private static final String TAG = "LOGINACTIVITY";
    public CallbackManager callbackManager;
    public LoginButton loginButton;
    public Button fbLogin;
    public AccessToken accessToken;
    public String stringAcessToken;
    public Button googleSignInBtn;
    public Button logInBtn;
    public EditText emailEdt;
    public EditText passwordEdt;
    private static final String EMAIL = "email";
    private NetworkManager networkManager;
    private SharedPreferences pref;


    GoogleSignInClient mGoogleSignInClient;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);

        stringAcessToken = null;

        pref = getApplicationContext().getSharedPreferences("USERSETTING", 0);

        networkManager = new NetworkManager();

        emailEdt = (EditText) findViewById(R.id.emailEdt);
        passwordEdt = (EditText) findViewById(R.id.passwordEdt);

        fbLogin = (Button) findViewById(R.id.facebookBtn);

        logInBtn = (Button) findViewById(R.id.logInBtn);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final User user = new User(emailEdt.getText().toString(), passwordEdt.getText().toString());

                Call<User> login = networkManager.getService().login(user);
                login.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if(response.code() == 200){
                            startActivity(new Intent(LoginActity.this,MainActivity.class));
                            user.getToken();

                            SharedPreferences.Editor editor = pref.edit();
                            stringAcessToken = user.getToken();

                            editor.putString("accessToken",stringAcessToken);
                            editor.putString("username",(emailEdt.getText().toString()).split("@")[0]);
                            editor.putString("email", emailEdt.getText().toString());
                            editor.commit();
                        }

                        System.out.println(" login result response... " + response.body().getToken());

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        System.out.println(" login result response... " + t.getMessage());
                    }
                });

            }
        });

        googleSignInBtn = (Button) findViewById(R.id.googleBtn);

        callbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInBtn.setOnClickListener(this);

        loginButton = (LoginButton) findViewById(R.id.login_button);

        fbLogin.setOnClickListener(this);

        loginButton.setReadPermissions(Arrays.asList("public_profile"));
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                startActivity(new Intent(LoginActity.this,MainActivity.class));

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());
                                accessToken = loginResult.getAccessToken();
                                stringAcessToken = accessToken.toString();
                                // Application code
                                try {
                                    String email = object.getString("email");
                                    String name = object.getString("name");

                                    SharedPreferences.Editor editor = pref.edit();

                                    editor.putString("accessToken",stringAcessToken);
                                    editor.putString("username",name);
                                    editor.putString("email",email);

                                    Toast.makeText(getApplicationContext(), " "+email, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(getApplicationContext(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                int statusCode = result.getStatus().getStatusCode();


        }else {
            callbackManager.onActivityResult(requestCode, resultCode,  data);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == fbLogin) {
            loginButton.performClick();
        }
        else if (view == googleSignInBtn) {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        stringAcessToken = pref.getString("accessToken", null);

        if(stringAcessToken != null){

            System.out.println(TAG+" ... connect with facebook");

            startActivity(new Intent(LoginActity.this,MainActivity.class));

        }else {

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();

                startActivity(new Intent(LoginActity.this,MainActivity.class));

                System.out.println(TAG+" ... "+personName);
            }

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);


            SharedPreferences.Editor editor = pref.edit();

            editor.putString("accessToken",account.getIdToken().toString());
            editor.putString("username",account.getDisplayName());
            editor.putString("email",account.getEmail());

            editor.commit();

            System.out.println(TAG+" ... "+account.getDisplayName());

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code = " + e.getStatusCode());

        }
    }
}

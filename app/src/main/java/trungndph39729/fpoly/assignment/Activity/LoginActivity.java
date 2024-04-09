package trungndph39729.fpoly.assignment.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import trungndph39729.fpoly.assignment.Domain.Response;
import trungndph39729.fpoly.assignment.Domain.User;
import trungndph39729.fpoly.assignment.R;
import trungndph39729.fpoly.assignment.Service.HttpRequest;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText ed_username,ed_password;
    private TextView tv_forgot;

    private CircularProgressButton cpb_login;
    private HttpRequest httpRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        changeStatusBarColor();
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        cpb_login= findViewById(R.id.cirLoginButton);
        ed_username = findViewById(R.id.editTextUsername);
        ed_password = findViewById(R.id.editTextPassword);
        tv_forgot= findViewById(R.id.tv_forgot);

        httpRequest = new HttpRequest();
        tv_forgot.setOnClickListener(view -> {
            String email = ed_username.getText().toString().trim();
            if(email.isEmpty()){
                Toast.makeText(LoginActivity.this, "Email must not be empty" + email, Toast.LENGTH_SHORT).show();
                return;
            }


        });

        cpb_login.setOnClickListener(view -> {
            User user = new User();
            String _username = ed_username.getText().toString();
            String _password = ed_password.getText().toString();
            if (_username.isEmpty() || _password.isEmpty()){
                Toast.makeText(this, "No be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            user.setUsername(_username);
            user.setPassword(_password);
            httpRequest.callAPI().login(user).enqueue(responseUser);



        });


    }
    Callback<Response<User>> responseUser = new Callback<Response<User>>() {
        @Override
        public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    Toast.makeText(LoginActivity.this, "Login Successfully !", Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = getSharedPreferences("INFO", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", response.body().getToken());
                    editor.putString("refreshToken", response.body().getRefreshToken());
                    editor.putString("id", response.body().getData().get_id());
                    editor.apply();

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));


                } else {
                    Toast.makeText(LoginActivity.this, "Wrong username or password  !", Toast.LENGTH_SHORT).show();

                }
            }
        }

        @Override
        public void onFailure(Call<Response<User>> call, Throwable t) {
            Toast.makeText(LoginActivity.this, "Wrong username or password  !", Toast.LENGTH_SHORT).show();

            Log.d(">>>> Login","on Failure: "+ t.getMessage());
        }
    };


    public void onLoginClick(View View){
        startActivity(new Intent(this, ResignActivity.class));

    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.white));
        }
    }

}
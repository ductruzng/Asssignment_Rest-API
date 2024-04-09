package trungndph39729.fpoly.assignment.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import trungndph39729.fpoly.assignment.Domain.Response;
import trungndph39729.fpoly.assignment.Domain.User;
import trungndph39729.fpoly.assignment.R;
import trungndph39729.fpoly.assignment.Service.HttpRequest;

public class ResignActivity extends AppCompatActivity {

    private TextInputEditText ed_email, ed_password, ed_name, ed_username;

    private CircularProgressButton cpb_register;
    private HttpRequest httpRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resign);
        changeStatusBarColor();

        cpb_register = findViewById(R.id.cirRegisterButton);
        ed_email = findViewById(R.id.editTextEmail);
        ed_name = findViewById(R.id.editTextName);
        ed_username = findViewById(R.id.editTextUsername);
        ed_password = findViewById(R.id.editTextPassword);

        httpRequest = new HttpRequest();


        cpb_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = ed_email.getText().toString().trim();
                String password = ed_password.getText().toString().trim();
                String username = ed_username.getText().toString().trim();


                if (email.isEmpty() || !isValidEmail(email)) {
                    ed_email.setError("Please enter a valid email address");
                    return;
                }

                if (password.isEmpty() || password.length()<6) {
                    ed_password.setError("Password must be at least 8 characters long, containing uppercase, lowercase, numbers, and special characters");
                    return;
                }

                if (username.isEmpty() || !isValidUsername(username)) {
                    ed_username.setError("Username must be between 4 and 20 characters long, containing only letters and numbers");
                    return;
                }

                User user = new User();
                user.setEmail(email);
                user.setPassword(password);
                user.setUsername(username);
                httpRequest.callAPI().register(user).enqueue(responseUser);

            }
        });


    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }


    private boolean isValidUsername(String username) {
        // Kiểm tra độ dài, chỉ cho phép ký tự chữ và số
        String usernamePattern = "^[a-zA-Z0-9]{4,20}$";
        return username.matches(usernamePattern);
    }

    Callback<Response<User>> responseUser = new Callback<Response<User>>() {
        @Override
        public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    Toast.makeText(ResignActivity.this, "Register successfully !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<User>> call, Throwable t) {
            Log.d(">>>> GetList", "onFailure: " + t.getMessage());
        }
    };

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.green));
        }
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));

    }
}
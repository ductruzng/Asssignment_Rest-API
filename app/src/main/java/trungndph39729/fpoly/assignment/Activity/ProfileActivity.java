package trungndph39729.fpoly.assignment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import retrofit2.Call;
import retrofit2.Callback;
import trungndph39729.fpoly.assignment.Domain.Response;
import trungndph39729.fpoly.assignment.Domain.User;
import trungndph39729.fpoly.assignment.R;
import trungndph39729.fpoly.assignment.Service.HttpRequest;
import trungndph39729.fpoly.assignment.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    HttpRequest httpRequest ;
    private SharedPreferences sharedPreferences;
    private String id;
    private ActivityProfileBinding binding;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        changeStatusBarColor();

        httpRequest = new HttpRequest();
        sharedPreferences = getSharedPreferences("INFO", MODE_PRIVATE);
        id = sharedPreferences.getString("id","");
        httpRequest.callAPI().getUser(id).enqueue(responseUser);


        binding.backBtn.setOnClickListener(view -> finish());
        binding.settingBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditInfoActivity.class);
            intent.putExtra("userKey",user);
            startActivity(intent);
        });
        binding.orderBtn.setOnClickListener(view -> startActivity(new Intent(this,OrderActivity.class)));
    }

    Callback<Response<User>> responseUser = new Callback<Response<User>>() {
        @Override
        public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    user = response.body().getData();
                    RequestOptions requestOptions = new RequestOptions()
                            .circleCrop();
                    Glide.with(getApplicationContext())
                            .load(response.body().getData().getAvatar())
                            .apply(requestOptions)
                            .into(binding.avatar);
                    binding.nameTxt.setText(response.body().getData().getName());
                    binding.emailTxt.setText(response.body().getData().getEmail());
                }
            }
        }

        @Override
        public void onFailure(Call<Response<User>> call, Throwable t) {
            Log.d(">>>> GetUser", "onFailure: " + t.getMessage());
        }
    };

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.white));
        }
    }
}
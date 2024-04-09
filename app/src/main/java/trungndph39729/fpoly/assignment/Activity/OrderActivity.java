package trungndph39729.fpoly.assignment.Activity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import trungndph39729.fpoly.assignment.Adapter.OrderAdapter;
import trungndph39729.fpoly.assignment.Domain.Order;
import trungndph39729.fpoly.assignment.Domain.Response;
import trungndph39729.fpoly.assignment.R;
import trungndph39729.fpoly.assignment.Service.HttpRequest;
import trungndph39729.fpoly.assignment.databinding.ActivityOrderBinding;

public class OrderActivity extends AppCompatActivity {
    private HttpRequest httpRequest;
    private ActivityOrderBinding binding;

    private SharedPreferences sharedPreferences;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        changeStatusBarColor();

        httpRequest = new HttpRequest();
        binding.backBtn.setOnClickListener(view -> finish());

        sharedPreferences = getSharedPreferences("INFO", MODE_PRIVATE);
        userId = sharedPreferences.getString("id","");
        httpRequest.callAPI().getOrderById(userId).enqueue(new Callback<Response<ArrayList<Order>>>() {
            @Override
            public void onResponse(Call<Response<ArrayList<Order>>> call, retrofit2.Response<Response<ArrayList<Order>>> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus() == 200){
                        ArrayList<Order> orders = response.body().getData();
                        binding.recyclerViewOrder.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                        binding.recyclerViewOrder.setAdapter(new OrderAdapter(orders,getApplicationContext()));
                    }
                }
            }

            @Override
            public void onFailure(Call<Response<ArrayList<Order>>> call, Throwable t) {
                Log.d(">>>> getOrders", "onFailure: "+t.getMessage());
            }
        });
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
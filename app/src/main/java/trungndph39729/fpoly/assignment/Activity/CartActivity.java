package trungndph39729.fpoly.assignment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import retrofit2.Call;
import retrofit2.Callback;
import trungndph39729.fpoly.assignment.Adapter.CartAdapter;
import trungndph39729.fpoly.assignment.Domain.Order;
import trungndph39729.fpoly.assignment.Domain.Response;
import trungndph39729.fpoly.assignment.Domain.User;
import trungndph39729.fpoly.assignment.Helper.ManagmentCart;
import trungndph39729.fpoly.assignment.R;
import trungndph39729.fpoly.assignment.Service.HttpRequest;
import trungndph39729.fpoly.assignment.databinding.ActivityCartBinding;

public class CartActivity extends BaseActivity {
    ActivityCartBinding binding;
    private double tax;
    private ManagmentCart managmentCart;
    HttpRequest httpRequest;
    private SharedPreferences sharedPreferences;
    private String id;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);
        httpRequest = new HttpRequest();

        calculatorCart();
        setVariable();
        initCartList();

        httpRequest = new HttpRequest();
        sharedPreferences = getSharedPreferences("INFO", MODE_PRIVATE);
        id = sharedPreferences.getString("id","");
        httpRequest.callAPI().getUser(id).enqueue(responseUser);


        binding.checkOutBtn.setOnClickListener(view -> {
            Order order = new Order();
            order.setUser(user);
            order.setProducts(managmentCart.getListCart());
            order.setTotalPrice(calculatorCart());
            httpRequest.callAPI().addOrder(order).enqueue(new Callback<Response<Order>>() {
                @Override
                public void onResponse(Call<Response<Order>> call, retrofit2.Response<Response<Order>> response) {
                    if(response.isSuccessful()){
                        if (response.body().getStatus() == 200){
                            managmentCart.clearCart();
                            startActivity(new Intent(CartActivity.this, CompleteActivity.class));
                            finish();


                        }
                    }
                }

                @Override
                public void onFailure(Call<Response<Order>> call, Throwable t) {
                    Log.d(">>>>>> Add Order", " onFailure: " +t.getMessage());
                }
            });
        });


    }

    Callback<Response<User>> responseUser = new Callback<Response<User>>() {
        @Override
        public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    user = response.body().getData();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<User>> call, Throwable t) {
            Log.d(">>>> GetUser", "onFailure: " + t.getMessage());
        }
    };


    private void initCartList() {
        if(managmentCart.getListCart().isEmpty()){
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scollViewCart.setVisibility(View.GONE);
        } else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scollViewCart.setVisibility(View.VISIBLE);
        }

        binding.cartView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.cartView.setAdapter(new CartAdapter(managmentCart.getListCart(),this,()-> calculatorCart()));

    }

    private void setVariable() {

        binding.backBtn.setOnClickListener(view -> finish());
    }

    private double calculatorCart() {
        double percentTax = 0.02;
        double delivery = 10;

        tax = Math.round((managmentCart.getTotalFee() * percentTax * 100.0) / 100.0);

        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100 / 100);
        double itemTotal = Math.round(managmentCart.getTotalFee()*100/100);

        binding.totalFeeTxt.setText("$"+itemTotal);
        binding.taxTxt.setText("$"+tax);
        binding.deliveryTxt.setText("$"+delivery);
        binding.totalTxt.setText("$"+ total);
        return total;
    }
}
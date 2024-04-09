package trungndph39729.fpoly.assignment.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import trungndph39729.fpoly.assignment.Adapter.PopularAdapter;
import trungndph39729.fpoly.assignment.Domain.ItemsDomain;
import trungndph39729.fpoly.assignment.Domain.Response;
import trungndph39729.fpoly.assignment.R;
import trungndph39729.fpoly.assignment.Service.HttpRequest;
import trungndph39729.fpoly.assignment.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    HttpRequest httpRequest;
    SharedPreferences sharedPreferences;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        httpRequest = new HttpRequest();


        binding.editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String key = binding.editTextSearch.getText().toString();
                    httpRequest.callAPI()
                            .searchProduct(key)
                            .enqueue(getProductsResponse);

                    return true;
                }
                return false;
            }
        });

    }

    private void initPopular(ArrayList<ItemsDomain> items) {

        binding.recyclerViewSearch.setLayoutManager(new GridLayoutManager(this,2));
        binding.recyclerViewSearch.setAdapter(new PopularAdapter(items));
    }

        Callback<Response<ArrayList<ItemsDomain>>> getProductsResponse = new Callback<Response<ArrayList<ItemsDomain>>>() {
            @Override
            public void onResponse(Call<Response<ArrayList<ItemsDomain>>> call, retrofit2.Response<Response<ArrayList<ItemsDomain>>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        ArrayList<ItemsDomain> ds = response.body().getData();
                        initPopular(ds);

                    }
                }
            }

            @Override
            public void onFailure(Call<Response<ArrayList<ItemsDomain>>> call, Throwable t) {
                Log.d(">>>> getListProduct", "onFailure: " + t.getMessage());
            }
        };

    }
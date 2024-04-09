package trungndph39729.fpoly.assignment.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import trungndph39729.fpoly.assignment.Adapter.CategoryAdapter;
import trungndph39729.fpoly.assignment.Adapter.PopularAdapter;
import trungndph39729.fpoly.assignment.Adapter.SizeAdapter;
import trungndph39729.fpoly.assignment.Adapter.SliderAdapter;
import trungndph39729.fpoly.assignment.Domain.CategoryDomain;
import trungndph39729.fpoly.assignment.Domain.ItemsDomain;
import trungndph39729.fpoly.assignment.Domain.Response;
import trungndph39729.fpoly.assignment.Domain.SliderItems;
import trungndph39729.fpoly.assignment.R;
import trungndph39729.fpoly.assignment.Service.HttpRequest;
import trungndph39729.fpoly.assignment.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private HttpRequest httpRequest;

    private SharedPreferences sharedPreferences;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        httpRequest = new HttpRequest();
        httpRequest.callAPI().getCategories().enqueue(getCategoriesAPI);

        sharedPreferences = getSharedPreferences("INFO", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        httpRequest.callAPI().getProducts("Bearer " + token).enqueue(getProductsResponse);

        initBanner();
        bottomNavigation();
    }

    private void bottomNavigation() {
        binding.cartBtn.setOnClickListener(view -> startActivity(new Intent(this, CartActivity.class)));
        binding.profileBtn.setOnClickListener(view -> startActivity(new Intent(this, ProfileActivity.class)));
        binding.searchBtn.setOnClickListener(view -> startActivity(new Intent(this,SearchActivity.class)));
    }


    private void initPopular(ArrayList<ItemsDomain> items) {
        binding.progressBarPopular.setVisibility(View.GONE);

        binding.recyclerViewPopular.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        binding.recyclerViewPopular.setAdapter(new PopularAdapter(items));
    }

    private void initCategory(ArrayList<CategoryDomain> items) {
        binding.progressBarOfficial.setVisibility(View.GONE);

        binding.recyclerViewOfficial.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
        binding.recyclerViewOfficial.setAdapter(new CategoryAdapter(items));

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

    Callback<Response<ArrayList<CategoryDomain>>> getCategoriesAPI = new Callback<Response<ArrayList<CategoryDomain>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<CategoryDomain>>> call, retrofit2.Response<Response<ArrayList<CategoryDomain>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    ArrayList<CategoryDomain> ds = response.body().getData();
                    initCategory(ds);

                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<CategoryDomain>>> call, Throwable t) {
            Log.d("ZZZZZZZZZZ", "onFailure: " + t.getMessage());
        }
    };

    private void initBanner() {
        binding.progressBarBanner.setVisibility(View.VISIBLE);
        ArrayList<SliderItems> items = new ArrayList<>();
        items.add(new SliderItems("https://firebasestorage.googleapis.com/v0/b/assignment-android3.appspot.com/o/banner1.png?alt=media&token=e4d783d5-f6ea-43f0-90d6-9c8a26ab8324"));
        items.add(new SliderItems("https://firebasestorage.googleapis.com/v0/b/assignment-android3.appspot.com/o/banner2.png?alt=media&token=d47bd3f8-83a2-4cab-bc71-7da9d77502f7"));
        banners(items);

        binding.progressBarBanner.setVisibility(View.GONE);


    }

    private void banners(ArrayList<SliderItems> items) {
        binding.viewpagerSlider.setAdapter(new SliderAdapter(items, binding.viewpagerSlider));
        binding.viewpagerSlider.setClipToPadding(false);
        binding.viewpagerSlider.setClipChildren(false);
        binding.viewpagerSlider.setOffscreenPageLimit(3);
        ;
        binding.viewpagerSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        binding.viewpagerSlider.setPageTransformer(compositePageTransformer);
    }
}
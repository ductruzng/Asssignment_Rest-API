package trungndph39729.fpoly.assignment.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import trungndph39729.fpoly.assignment.Adapter.SizeAdapter;
import trungndph39729.fpoly.assignment.Adapter.SliderAdapter;
import trungndph39729.fpoly.assignment.Domain.ItemsDomain;
import trungndph39729.fpoly.assignment.Domain.SliderItems;
import trungndph39729.fpoly.assignment.Fragment.DescriptionFragment;
import trungndph39729.fpoly.assignment.Fragment.ReviewFragment;
import trungndph39729.fpoly.assignment.Fragment.SoldFragment;
import trungndph39729.fpoly.assignment.Helper.ManagmentCart;
import trungndph39729.fpoly.assignment.R;
import trungndph39729.fpoly.assignment.databinding.ActivityDetailBinding;

public class DetailActivity extends  BaseActivity {
    ActivityDetailBinding binding;
    private ItemsDomain object;
    private int numberOrder = 1;
private ManagmentCart managmentCart;
private Handler slideHandle = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart =  new ManagmentCart(this);


        getBundles();
        banners();
        initSize();
        setUpViewPager();
    }
    private  void  initSize(){
        ArrayList<String> list= new ArrayList<>();
        list.add("S");
        list.add("M");
        list.add("L");
        list.add("XL");
        list.add("XXL");
        binding.recyclerSize.setAdapter(new SizeAdapter(list));
        binding.recyclerSize.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

    }

    private void banners(){
        ArrayList<SliderItems> sliderItemsList = new ArrayList<>();
        for(int i = 0 ;i < object.getPicUrl().size();i++){
            sliderItemsList.add(new SliderItems(object.getPicUrl().get(i)));

        }
        binding.viewpagerSlider.setAdapter(new SliderAdapter(sliderItemsList, binding.viewpagerSlider));
        binding.viewpagerSlider.setClipToPadding(false);
        binding.viewpagerSlider.setClipChildren(false);
        binding.viewpagerSlider.setOffscreenPageLimit(3);
        ;
        binding.viewpagerSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }

    private void getBundles(){
        object = (ItemsDomain) getIntent().getSerializableExtra("object");
        binding.titleTxt.setText(object.getTitle());
        binding.priceTxt.setText("$"+object.getPrice());
        binding.ratingTxt.setText(object.getRating()+" Rating");
        binding.ratingBar.setRating((float) object.getRating());

        binding.addToCartBtn.setOnClickListener(view -> {
            object.setNumberInCart(numberOrder);
            managmentCart.insertItem(object);
        });

        binding.backBtn.setOnClickListener(view -> finish());
    }

    private void setUpViewPager(){
        ViewPagerAdapter adapter =new ViewPagerAdapter(getSupportFragmentManager());

        DescriptionFragment tab1 = new DescriptionFragment();
        SoldFragment tab3 = new SoldFragment();
        ReviewFragment tab2 = new ReviewFragment();

        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = new Bundle();

        bundle1.putString("description",object.getDescription());

        tab1.setArguments(bundle1);
        tab2.setArguments(bundle2);
        tab3.setArguments(bundle3);

        adapter.addFrag(tab1, "Description");
        adapter.addFrag(tab2, "Reviews");
        adapter.addFrag(tab3, "Sold");

        binding.viewpager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewpager);

    }

    public  class ViewPagerAdapter extends FragmentPagerAdapter{
        private  final List<Fragment> mFragmentList = new ArrayList<>();
        private final  List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFrag(Fragment fragment,String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }
    }
}
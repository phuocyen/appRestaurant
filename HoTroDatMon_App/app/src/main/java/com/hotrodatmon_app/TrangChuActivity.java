package com.hotrodatmon_app;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.hotrodatmon_app.Adapter.ViewPagerAdapter;
import com.hotrodatmon_app.MODEL.PhanQuyen;

import java.util.ArrayList;
import java.util.Stack;

public class TrangChuActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager;

    // Fragment array
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    // backStack
    Stack<Integer> pageHistory = new Stack<>();
    boolean saveToHistory = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu2);

        addControls();

        fragmentArrayList = initFragmentArray();
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, fragmentArrayList);
        viewPager.setAdapter(adapter);

        // active bottom navigation khi fragment page tuong ung duoc active
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.action_home);
                        break;
                    case 1:
                         bottomNavigationView.setSelectedItemId(R.id.action_order);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.action_menu);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.action_other);
                        break;

                }

                if (saveToHistory == true)
                    pageHistory.push(position);

                super.onPageSelected(position);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {  @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_home:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.action_order:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.action_menu:
                    try {
                        if (PhanQuyen.getTenNV().equals("Lê Minh Tiên")) {
                            viewPager.setCurrentItem(2);
                            break;
                        }
                    } catch (Exception e) {
                        Toast.makeText(TrangChuActivity.this, "Bạn không có quyền này", Toast.LENGTH_SHORT).show();
                    }
                case R.id.action_other:
                    viewPager.setCurrentItem(3);
                     break;


                // Add cases for other menu items if needed
            }
            return true;
        }
        });

        // Get the fragment index from the intent
        int fragmentIndex = getIntent().getIntExtra("fragmentIndex", 0);
        viewPager.setCurrentItem(fragmentIndex);

    }

    @Override
    public void onBackPressed() {
        if (pageHistory.size() <= 1)
            super.onBackPressed();
        else {
            saveToHistory = false;
            pageHistory.pop();
            viewPager.setCurrentItem(pageHistory.lastElement().intValue());
            saveToHistory = true;
        }
    }

    protected ArrayList<Fragment> initFragmentArray() {
        ArrayList<Fragment> arr = new ArrayList<>();
        arr.add(new HomeFragment());
        arr.add(new TableFragment());
        arr.add(new ProfitFragment());
        arr.add(new OtherFragment());
        // Add other fragments to the array if needed

        return arr;
    }

    protected void addControls() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        bottomNavigationView = findViewById(R.id.Menu);
        viewPager = findViewById(R.id.viewPager);
    }
}

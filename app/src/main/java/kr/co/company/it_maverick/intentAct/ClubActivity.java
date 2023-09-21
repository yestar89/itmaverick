package kr.co.company.it_maverick.intentAct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import kr.co.company.it_maverick.Adapter.TabPagerAdapter;
import kr.co.company.it_maverick.Club.FeedFragment;
import kr.co.company.it_maverick.R;

public class ClubActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    private TabPagerAdapter adapter;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new FeedFragment()).commit();

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.menu_club);

        viewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabs);

        adapter = new TabPagerAdapter(this);

        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("NO TIME 피드");
                    break;
                case 1:
                    tab.setText("YES! 다이어리");
                    break;
                case 2:
                    tab.setText("나만의 팁쳐");
                    break;
            }
        }).attach();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;;

                switch (item.getItemId()) {
                    case R.id.menu_home:
                        // 홈 아이템 클릭 시 HomeActivity로 화면 전환
                        intent = new Intent(ClubActivity.this, HomeActivity.class);
                        break;
                    case R.id.menu_club:
                        // 클럽 아이템 클릭 시 ClubActivity로 화면 전환
                        intent = new Intent(ClubActivity.this, ClubActivity.class);
                        break;
                    case R.id.menu_goal:
                        // 목표 아이템 클릭 시 GoalActivity로 화면 전환
                        intent = new Intent(ClubActivity.this, GoalActivity.class);
                        break;
                    case R.id.menu_watch:
                        // 워치 아이템 클릭 시 WatchActivity로 화면 전환
                        intent = new Intent(ClubActivity.this, WatchActivity.class);
                        break;
                    case R.id.menu_profile:
                        // 마이페이지 아이템 클릭 시 ProfileActivity로 화면 전환
                        intent = new Intent(ClubActivity.this, ProfileActivity.class);
                        break;
                }

                // 액티비티 전환
                if (intent != null) {
                    startActivity(intent);
                }

                return true;
            }
        });




    }
}
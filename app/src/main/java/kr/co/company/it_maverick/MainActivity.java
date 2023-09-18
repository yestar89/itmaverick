package kr.co.company.it_maverick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import kr.co.company.it_maverick.Fragment.ClubFragment;
import kr.co.company.it_maverick.Fragment.GoalFragment;
import kr.co.company.it_maverick.Fragment.HomeFragment;
import kr.co.company.it_maverick.Fragment.ProfileFragment;
import kr.co.company.it_maverick.Fragment.WatchFragment;

public class MainActivity extends AppCompatActivity {

    TextView goSign;
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    ClubFragment clubFragment;
    GoalFragment goalFragment;
    ProfileFragment profileFragment;
    WatchFragment watchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        goalFragment = new GoalFragment();
        clubFragment = new ClubFragment();
        watchFragment = new WatchFragment();
        profileFragment = new ProfileFragment();


//        goSign = findViewById(R.id.logout);

        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_frame, homeFragment).commit();

        NavigationBarView navigationBarView = findViewById(R.id.bottom_nav);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_frame, homeFragment).commit();
                        return true;
                    case R.id.menu_club:
                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_frame, clubFragment).commit();
                        return true;
                    case R.id.menu_watch:
                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_frame, watchFragment).commit();
                        return true;
                    case R.id.menu_goal:
                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_frame, goalFragment).commit();
                        return true;
                    case R.id.menu_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_frame, profileFragment).commit();
                        return true;
                }

                return false;
            }
        });

//        goSign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, Signin.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}
package kr.co.company.it_maverick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import kr.co.company.it_maverick.Fragment.ClubFragment;
import kr.co.company.it_maverick.Fragment.GoalFragment;
import kr.co.company.it_maverick.Fragment.HomeFragment;
import kr.co.company.it_maverick.Fragment.ProfileFragment;
import kr.co.company.it_maverick.Fragment.WatchFragment;
import kr.co.company.it_maverick.intentAct.ClubActivity;
import kr.co.company.it_maverick.intentAct.GoalActivity;
import kr.co.company.it_maverick.intentAct.HomeActivity;
import kr.co.company.it_maverick.intentAct.ProfileActivity;
import kr.co.company.it_maverick.intentAct.WatchActivity;

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

    bottomNavigationView = findViewById(R.id.bottom_nav);

//        goSign = findViewById(R.id.logout);
        // 처음 시작 화면은 홈화면
        Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(homeIntent);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;;

                    switch (item.getItemId()) {
                        case R.id.menu_home:
                            // 홈 아이템 클릭 시 HomeActivity로 화면 전환
                            intent = new Intent(MainActivity.this, HomeActivity.class);
                            break;
                        case R.id.menu_club:
                            // 클럽 아이템 클릭 시 ClubActivity로 화면 전환
                            intent = new Intent(MainActivity.this, ClubActivity.class);
                            break;
                        case R.id.menu_goal:
                            // 목표 아이템 클릭 시 GoalActivity로 화면 전환
                            intent = new Intent(MainActivity.this, GoalActivity.class);
                            break;
                        case R.id.menu_watch:
                            // 워치 아이템 클릭 시 WatchActivity로 화면 전환
                            intent = new Intent(MainActivity.this, WatchActivity.class);
                            break;
                        case R.id.menu_profile:
                            // 마이페이지 아이템 클릭 시 ProfileActivity로 화면 전환
                            intent = new Intent(MainActivity.this, ProfileActivity.class);
                            break;
                    }

                    // 액티비티 전환
                    if (intent != null) {
                        startActivity(intent);
                    }

                    return true;
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
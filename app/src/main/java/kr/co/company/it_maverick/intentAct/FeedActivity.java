package kr.co.company.it_maverick.intentAct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import kr.co.company.it_maverick.Adapter.PostAdapter;
import kr.co.company.it_maverick.Model.Post;
import kr.co.company.it_maverick.PostActivity;
import kr.co.company.it_maverick.R;

public class FeedActivity extends AppCompatActivity {
    private PostAdapter postAdapter;
    private ArrayList<Post> postLists;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);


        AppCompatButton goPost = findViewById(R.id.goPost);

        goPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PostActivity.class);
                startActivity(intent);
            }
        });
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;;

                switch (item.getItemId()) {
                    case R.id.menu_home:
                        // 홈 아이템 클릭 시 HomeActivity로 화면 전환
                        intent = new Intent(FeedActivity.this, HomeActivity.class);
                        break;
                    case R.id.menu_club:
                        // 클럽 아이템 클릭 시 ClubActivity로 화면 전환
                        intent = new Intent(FeedActivity.this, ClubActivity.class);
                        break;
                    case R.id.menu_goal:
                        // 목표 아이템 클릭 시 GoalActivity로 화면 전환
                        intent = new Intent(FeedActivity.this, GoalActivity.class);
                        break;
                    case R.id.menu_watch:
                        // 워치 아이템 클릭 시 WatchActivity로 화면 전환
                        intent = new Intent(FeedActivity.this, WatchActivity.class);
                        break;
                    case R.id.menu_profile:
                        // 마이페이지 아이템 클릭 시 ProfileActivity로 화면 전환
                        intent = new Intent(FeedActivity.this, ProfileActivity.class);
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
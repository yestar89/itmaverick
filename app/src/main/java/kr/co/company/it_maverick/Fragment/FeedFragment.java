package kr.co.company.it_maverick.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kr.co.company.it_maverick.Adapter.PostAdapter;
import kr.co.company.it_maverick.Model.Post;
import kr.co.company.it_maverick.PostActivity;
import kr.co.company.it_maverick.R;

public class FeedFragment extends Fragment {
    private static final String TAG = "FeedFragment";
    private PostAdapter postAdapter;
    //private List<Post> postLists;

    private ArrayList<Post> postLists;
    //private ArrayList<User> userLists;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;

    AppCompatButton goPost;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.d(TAG, "post: add 확인: ");

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);


        recyclerView.setLayoutManager(layoutManager);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
//        recyclerView.setLayoutManager(linearLayoutManager);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        postAdapter = new PostAdapter(getContext(), postLists);
        recyclerView.setAdapter(postAdapter);

        postLists = new ArrayList<>();
        //userLists = new ArrayList<>();


        goPost = view.findViewById(R.id.goPost);


        database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Posts").child("noTimeFeed")
                .child("Post1");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postLists.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    if (post != null) {
                        postLists.add(post);
                    }
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Feedfragment", String.valueOf(error.toException()));
            }
        });



        //글쓰기 버튼
        goPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PostActivity.class);
                startActivity(intent);
            }
        });

        //readPosts();

        postAdapter = new PostAdapter(getContext(), postLists);
        recyclerView.setAdapter(postAdapter);

        return view;



    }



}




//        private void readPosts() {
//            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
//
//            reference.addValueEventListener(new ValueEventListener() {
//                //데이터 변경 됐을 때 호출. 변경 사항 처리
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    postLists.clear();
//                    for (DataSnapshot datasnapshot : snapshot.getChildren()) {
//                        Post post = datasnapshot.getValue(Post.class);
//
//                        if (post != null) {
//                            postLists.add(post);
//                        }
//                    }
//                    postAdapter.notifyDataSetChanged();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });

        //;}






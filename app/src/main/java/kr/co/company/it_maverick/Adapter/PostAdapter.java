package kr.co.company.it_maverick.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import kr.co.company.it_maverick.Model.Post;
import kr.co.company.it_maverick.Model.User;
import kr.co.company.it_maverick.R;

public class  PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public Context mContext;
    //public List<Post> mPost;
    public ArrayList<Post> mPost;
    //public ArrayList<User> mUSers;

    private FirebaseUser firebaseUser;
    private FirebaseStorage firebaseStorage;


    public PostAdapter(Context mContext, ArrayList<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
        //this.mUSers = mUSers;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Post post = mPost.get(position);
        //User user = mUSers.get(position);

        holder.username.setText(post.getUsername());
        holder.category.setText(post.getCategory());
        holder.description.setText(post.getDescription());
        //Glide.with(mContext).load(user.getImageurl()).into(holder.image_profile);
        Glide.with(holder.itemView).load(post.getUserimage()).into(holder.image_profile);
        Glide.with(holder.itemView).load(post.getPostimage()).into(holder.post_image);


        if (post.getDescription().equals("")) {
            holder.description.setVisibility(View.GONE);
        } else {
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(post.getDescription());
        }
        publisherInfo(holder.image_profile, holder.username, holder.category, post.getPublisher());
        isLiked(post.getPostid(), holder.like);
        nrLikes(holder.txt_like, post.getPostid());

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.like.getTag().equals("like")) {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostid())
                            .child(firebaseUser.getUid()).setValue(true);
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostid())
                            .child(firebaseUser.getUid()).removeValue();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mPost != null ? mPost.size() : 0 );
    }







    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image_profile, post_image, like, comment, btn_outline;
        public TextView username, txt_like, description, upload_time, category;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //프사
            image_profile = itemView.findViewById(R.id.image_profile);
            //삭제/수정
            btn_outline = itemView.findViewById(R.id.btn_outline);
            //게시글 이미지
            post_image = itemView.findViewById(R.id.post_image);
            //하트 이미지
            like = itemView.findViewById(R.id.like);
            //댓글 이미지
            comment = itemView.findViewById(R.id.comment);
            //유저 이름
            username = itemView.findViewById(R.id.username);
            // 좋아요 수
            txt_like = itemView.findViewById(R.id.txt_like);
            // 게시글 설명
            description = itemView.findViewById(R.id.description);
            // 업로드 시간
            upload_time = itemView.findViewById(R.id.upload_time);
            // 운동 or 공부
            category = itemView.findViewById(R.id.category);
        }
    }

    private void isLiked (String postid, ImageView imageView) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(firebaseUser.getUid()).exists()) {
                    imageView.setImageResource(R.drawable.ic_liked);
                    imageView.setTag("liked");
                } else {
                    imageView.setImageResource(R.drawable.ic_like);
                    imageView.setTag("like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void nrLikes (TextView likes, String postid) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes")
                .child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                likes.setText(snapshot.getChildrenCount() + "likes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void publisherInfo(final ImageView image_profile, final TextView username, final TextView category,
                               final String userid) {
        if (userid != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//
//                Glide.with(mContext).load(user.getUsername()).into(image_profile);
//                username.setText(user.getUsername());
                    if (snapshot.exists()) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            Glide.with(mContext).load(user.getImageurl()).into(image_profile);
                            username.setText(user.getUsername());
                            category.setText(user.getCategory());
                        }
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}



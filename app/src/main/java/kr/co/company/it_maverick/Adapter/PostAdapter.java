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
    public ArrayList<Post> mPost;
    private DatabaseReference databaseReference;

    public PostAdapter(Context mContext, ArrayList<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
        databaseReference = FirebaseDatabase.getInstance().getReference("posts");
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.post_item, parent, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Post post = mPost.get(position);
        holder.username.setText(post.getUsername());
        holder.category.setText(post.getCategory());
        holder.description.setText(post.getDescription());
        Glide.with(holder.itemView).load(post.getUserimage()).into(holder.image_profile);
        Glide.with(holder.itemView).load(post.getPostimage()).into(holder.post_image);
    }


    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
}



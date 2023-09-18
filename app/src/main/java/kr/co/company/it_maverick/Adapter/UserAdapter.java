package kr.co.company.it_maverick.Adapter;

import android.content.Context;
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

import java.util.List;

import kr.co.company.it_maverick.Model.User;
import kr.co.company.it_maverick.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private Context mContext;
    private List<User> mUSers;

    private FirebaseUser firebaseUser;

    public UserAdapter(Context mContext, List<User> mUSers) {
        this.mContext = mContext;
        this.mUSers = mUSers;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final User user = mUSers.get(position);

        holder.username.setText(user.getUsername());
        Glide.with(mContext).load(user.getImageurl()).into(holder.image_profile);

    }





    @Override
    public int getItemCount() {
        return mUSers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView image_profile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            image_profile = itemView.findViewById(R.id.image_profile);
        }
    }


}

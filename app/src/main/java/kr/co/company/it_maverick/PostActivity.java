package kr.co.company.it_maverick;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.yalantis.ucrop.view.CropImageView;

import java.util.HashMap;

import kr.co.company.it_maverick.Fragment.ClubFragment;
import kr.co.company.it_maverick.Fragment.FeedFragment;
import kr.co.company.it_maverick.databinding.FragmentFeedBinding;

public class PostActivity extends AppCompatActivity {

    Uri imageUrl;
    String myUrl = "";
    StorageTask uploadTask;
    StorageReference storageReference;

    ImageView close, image_added;
    Button sports, study;
    TextView post;
    EditText description;
    ProgressBar progressBar;

    private static final int PICK_IMAGE = 1;

    //선택한 카테고리 저장 변수
    private String selectedCategory = "";


    private String getFileExtension (Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Picasso.with(this).load(resultUri).into(image_added);
            }
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        close = findViewById(R.id.close);
        image_added = findViewById(R.id.image_added);
        sports = findViewById(R.id.sports);
        study = findViewById(R.id.study);
        post = findViewById(R.id.post);
        description = findViewById(R.id.description);
        progressBar = findViewById(R.id.progressbar);

        storageReference = FirebaseStorage.getInstance().getReference("posts");

        

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, ClubFragment.class);
                startActivity(intent);
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sports.setBackgroundResource(R.drawable.square_background);
                study.setBackgroundResource(R.drawable.square);
                selectedCategory = "운동";
            }
        });

        study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                study.setBackgroundResource(R.drawable.square_background);
                sports.setBackgroundResource(R.drawable.square);
                selectedCategory = "공부";
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedCategory.isEmpty()) {
                    // 카테고리를 선택하지 않았을 때 예외 처리
                    Toast.makeText(PostActivity.this, "카테고리를 선택하세요.", Toast.LENGTH_SHORT).show();
                } else if (description.getText().toString().isEmpty()){
                    Toast.makeText(PostActivity.this, "글을 작성하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    uploadImage();
                }
            }
        });



        image_added.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                launcher.launch(intent);
            }
        });

    }
    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUrl = result.getData().getData();
                        Log.d("test", imageUrl.toString());

                        if (imageUrl != null) {
                            // 이미지를 표시할 ImageView
                            ImageView imageView = findViewById(R.id.image_added); // R.id.imageView는 이미지뷰의 ID에 맞게 수정하세요

                            // Glide를 사용하여 이미지를 로드하고 이미지뷰에 표시
                            Glide.with(PostActivity.this)
                                    .load(imageUrl)
                                    .into(image_added);
                        }
                    }

                    }
            });

    private void uploadImage() {
        //ProgressBar progressBar = new ProgressBar(this);
        ProgressBar progressBar = findViewById(R.id.progressBar);


        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }



        if (imageUrl != null) {
            StorageReference filerefernce = storageReference.child(System.currentTimeMillis()
            + "."+ getFileExtension(imageUrl));

            uploadTask = filerefernce.putFile(imageUrl);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filerefernce.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        myUrl = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

                        String postid = reference.push().getKey();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("postid", postid);
                        hashMap.put("Postimage", myUrl);
                        hashMap.put("description", description.getText().toString());
//                        hashMap.put("study", study.getText().toString());
//                        hashMap.put("sports", sports.getText().toString());

                        hashMap.put("category", selectedCategory);

                        reference.child(postid).setValue(hashMap);
//                        ProgressBar progressBar = findViewById(R.id.progressBar);
//                        progressBar.setVisibility(View.VISIBLE);

//                        Intent intent = new Intent(PostActivity.this, FeedFragment.class);
//                        startActivity(intent);
//                        finish(); // 현재 액티비티 종료
                        FeedFragment feedFragment = new FeedFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content, feedFragment); // R.id.fragment_container는 Fragment를 표시할 레이아웃 ID로 수정하세요.
                        transaction.addToBackStack(null); // 이전 Fragment로 돌아갈 수 있도록 백 스택에 추가
                        transaction.commit();
//                        ClubFragment clubFragment = new ClubFragment();
//                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.container, ClubFragment); // R.id.fragment_container는 Fragment를 표시할 레이아웃 ID로 수정하세요.
//                        transaction.addToBackStack(null); // 이전 Fragment로 돌아갈 수 있도록 백 스택에 추가
//                        transaction.commit();

                    } else {
                        Toast.makeText(PostActivity.this, "실패", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void saveCategory(String category) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference categoryReference = database.getReference("categories");

        categoryReference.child("selectedCateGory").setValue(category);
    }



}
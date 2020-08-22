package com.proatcoding.pets.activity.petmarket;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.proatcoding.pets.R;
import com.proatcoding.pets.adapter.MyPetImagesAdapter;
import com.proatcoding.pets.models.LoginDTO;
import com.proatcoding.pets.models.PetMarketDTO;
import com.proatcoding.pets.multipleimagepicker.MultiImageSelector;
import com.proatcoding.pets.multipleimagepicker.MultiMyPetImageSelector;
import com.proatcoding.pets.sharedprefrence.SharedPrefrence;
import com.proatcoding.pets.utils.Consts;
import com.proatcoding.pets.utils.CustomTextView;
import com.proatcoding.pets.utils.CustomTextViewBold;
import com.proatcoding.pets.utils.ImageCompression;
import com.proatcoding.pets.utils.ProjectUtils;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UploadMyPetimageActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recycler_view_images;
    ImageView ivback;

    Context mContext;
    CustomTextView TVupload;
    CustomTextViewBold CTVBno;

    public static ArrayList<String> mSelectedImagesList = new ArrayList<>();

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 401;
    private final int REQUEST_IMAGE = 301;


    private MultiMyPetImageSelector mMultiImageSelector;
    private MyPetImagesAdapter mImagesAdapter;

    HashMap<String, File> Imagelist;

    int count = 0;
    int i = 5;
    File file;
    private final int MAX_IMAGE_SELECTION_LIMIT = 5;

    SharedPrefrence sharedPrefrence;
    LoginDTO loginDTO;
    String functionid;
    private GridLayoutManager gridLayoutManager;
    ImageCompression imageCompression;
    ArrayList<PetMarketDTO.PetImage> newsImageLIST = new ArrayList<>();
    String pet_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectUtils.setStatusBarGradiant(UploadMyPetimageActivity.this);
        setContentView(R.layout.activity_uploadmypet);
        mContext = UploadMyPetimageActivity.this;
        sharedPrefrence = SharedPrefrence.getInstance(mContext);
        loginDTO = sharedPrefrence.getParentUser(Consts.LOGINDTO);

        mMultiImageSelector = MultiMyPetImageSelector.create();
        newsImageLIST = (ArrayList<PetMarketDTO.PetImage>) getIntent().getSerializableExtra(Consts.IMAGE);
        pet_id = getIntent().getStringExtra(Consts.PET_ID);
        findUI();
    }

    private void findUI() {
        recycler_view_images = findViewById(R.id.recycler_view_images);
        ivback = findViewById(R.id.ivback);

        TVupload = findViewById(R.id.TVupload);
        CTVBno = findViewById(R.id.CTVBno);

        //CTVBno.setVisibility(View.VISIBLE);

        TVupload.setOnClickListener(this);

        ivback.setOnClickListener(this);

        if (newsImageLIST.size() > 0) {
            mImagesAdapter = new MyPetImagesAdapter(this, newsImageLIST, pet_id);
            gridLayoutManager = new GridLayoutManager(this, 2);
            recycler_view_images.setHasFixedSize(true);
            recycler_view_images.setLayoutManager(gridLayoutManager);
            recycler_view_images.setAdapter(mImagesAdapter);
        } else {
            CTVBno.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivback:

                finish();
                break;
            case R.id.TVupload:
                if (checkAndRequestPermissions()) {
                    mMultiImageSelector.showCamera(true);
                    mMultiImageSelector.count(i);
                    mMultiImageSelector.multi();
                    mMultiImageSelector.origin(mSelectedImagesList);
                    mMultiImageSelector.start(UploadMyPetimageActivity.this, REQUEST_IMAGE, pet_id);

                }
                finish();
                break;

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            try {
                Imagelist = new HashMap<>();
                mSelectedImagesList = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);


               /* if (mSelectedImagesList.size() == 0) {
                    CTVBno.setVisibility(View.VISIBLE);
                } else {
                    CTVBno.setVisibility(View.GONE);
                }

                for (j = 0; j < mSelectedImagesList.size(); j++) {

                    String imagePath = ProjectUtils.compressImage(mSelectedImagesList.get(j), mContext);

                    file = new File(imagePath);
                    Imagelist.put("files[" + String.valueOf(j) + "]", file);
                }*/

            /*    mImagesAdapter = new InvitationImagesAdapter(this, mSelectedImagesList);
                gridLayoutManager = new GridLayoutManager(this, 3);
                recycler_view_images.setHasFixedSize(true);
                recycler_view_images.setLayoutManager(gridLayoutManager);
                recycler_view_images.setAdapter(mImagesAdapter);*/


            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    private boolean checkAndRequestPermissions() {
        int externalStoragePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (externalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(UploadMyPetimageActivity.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
        }
    }


}

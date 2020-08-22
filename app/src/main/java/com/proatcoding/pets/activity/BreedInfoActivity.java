package com.proatcoding.pets.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.proatcoding.pets.R;
import com.proatcoding.pets.SysApplication;
import com.proatcoding.pets.adapter.BreedAdapter;
import com.proatcoding.pets.models.BreedDTO;
import com.proatcoding.pets.sharedprefrence.SharedPrefrence;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BreedInfoActivity extends AppCompatActivity {
    private ListView lvBreedInfo;
    private List<BreedDTO> breedCategories;
    private SharedPrefrence share;
    private  ImageView ivSearchBreeds;
    private  LinearLayout llSearchBreeds;
    private  MaterialEditText etSearchBreeds;
    private BreedAdapter adapter;
    private SysApplication sysApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed_info);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        share = SharedPrefrence.getInstance(BreedInfoActivity.this);
        sysApplication = SysApplication.getInstance();

        Collections.sort(breedCategories, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                BreedDTO p1 = (BreedDTO) o1;
                BreedDTO p2 = (BreedDTO) o2;
                return p1.getBreed_name().compareToIgnoreCase(p2.getBreed_name());
            }

        });


        init();
        ivSearchBreeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (llSearchBreeds.getVisibility() == View.VISIBLE) {
                    etSearchBreeds.setText("");
                    ivSearchBreeds.setImageResource(R.drawable.search_new);
                    llSearchBreeds.setVisibility(View.GONE);
                } else {
                    ivSearchBreeds.setImageResource(R.drawable.close);
                    llSearchBreeds.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void init() {
        ivSearchBreeds =  findViewById(R.id.ivSearchBreeds);
        llSearchBreeds =  findViewById(R.id.llSearchBreeds);
        etSearchBreeds =  findViewById(R.id.etSearchBreeds);
        lvBreedInfo =  findViewById(R.id.friends);

        llSearchBreeds.setVisibility(View.GONE);
        ivSearchBreeds.setVisibility(View.VISIBLE);

    }
}

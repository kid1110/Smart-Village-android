package com.example.smartvillage.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartvillage.CommunityActivity;
import com.example.smartvillage.FarmKnowActivity;
import com.example.smartvillage.HallActivity;
import com.example.smartvillage.R;
import com.example.smartvillage.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private View root;
    private LinearLayout hall,skill,shopping,community;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        init();
        skill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FarmKnowActivity.class);
                startActivity(intent);
            }
        });
        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CommunityActivity.class);
                startActivity(intent);
            }
        });
        hall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HallActivity.class);
                startActivity(intent);
            }
        });





        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void init(){
        hall = root.findViewById(R.id.home_hall);
        shopping = root.findViewById(R.id.village_buy);
        skill = root.findViewById(R.id.skill_learn);
        community = root.findViewById(R.id.comment_community);
    }
}
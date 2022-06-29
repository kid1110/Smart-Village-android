package com.example.smartvillage.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartvillage.AddCardActivity;
import com.example.smartvillage.entity.Card;
import com.example.smartvillage.CardAdapter;
import com.example.smartvillage.CardHelper;
import com.example.smartvillage.R;
import com.example.smartvillage.entity.Jwt;
import com.example.smartvillage.entity.User;
import com.example.smartvillage.utils.JsonUtils;
import com.example.smartvillage.utils.JwtUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import java.util.ArrayList;
import java.util.List;



public class DashboardFragment extends Fragment {
    private static final float dark = 0.5f;
    private static final float bright = 1.0f;
    private static final int  isAdmin= 1;
    private static final int MAX_LENGTH = 50;
    private  View root;
    private  RecyclerView cardRecyclerView;
    private Context context;
    private List<Card>  dashBoardCardList;
    private CardAdapter cardAdapter;
    private CircularProgressIndicator progressIndicator;
    private SharedPreferences sharedPreferences;
    private TextView dashtip;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        dashBoardCardList = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null){
            root = inflater.inflate(R.layout.fragment_dashboard,container,false);
        }
        sharedPreferences = getContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("user",null);
        System.out.println("fra"+token);

        //取用户信息
        Jwt user = JwtUtils.getJwtInfo(token);
        FloatingActionButton button = root.findViewById(R.id.dashboard_floating_action_button);
        progressIndicator = root.findViewById(androidx.constraintlayout.widget.R.id.progress_circular);
        dashtip = root.findViewById(R.id.text_dashboard);

        //如果是管理员
        if(user.getAdmin() == 1){
            button.setVisibility(View.VISIBLE);
            button.setEnabled(true);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), AddCardActivity.class);
                    startActivity(intent);
                }
            });
        }
        //等待cardRecycleView加载完






        return root;

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    private void initCard(){
        dashBoardCardList.clear();
        dashBoardCardList = CardHelper.GetCards();
        if (dashBoardCardList == null){
            dashBoardCardList = new ArrayList<>();
        }
        if(!dashBoardCardList.isEmpty()){
            for (Card card:dashBoardCardList){
                String data;
                if(card.getContent().length() >MAX_LENGTH){
                    data = card.getContent().substring(0, MAX_LENGTH + 1);
                }else{
                    data = card.getContent();
                }
                card.setShortInfo(data);
            }
        }


    }
    private void init(){

        cardRecyclerView = (RecyclerView) root.findViewById(R.id.cardRecycler);
        cardAdapter = new CardAdapter(getActivity(),dashBoardCardList);
        cardRecyclerView.setAdapter(cardAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
       cardRecyclerView.setLayoutManager(layoutManager);


    }

    @Override
    public void onResume() {
        super.onResume();
//        设置黑暗
        backgroundAlpha(dark);
        progressIndicator.setVisibility(View.VISIBLE);

        new Thread(()->{
            initCard();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if( !dashBoardCardList.isEmpty()){
                        dashtip.setVisibility(View.INVISIBLE);
                        init();
                        progressIndicator.setVisibility(View.INVISIBLE);
                        backgroundAlpha(bright);
                    }else{
                        dashtip.setText("暂无公告");
                        dashtip.setVisibility(View.VISIBLE);
                        progressIndicator.setVisibility(View.INVISIBLE);
                        backgroundAlpha(bright);
                    }

                }
            });


        }).start();
    }
    /***
     * 此方法用于改变背景的透明度，从而达到“变暗”的效果
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }


}
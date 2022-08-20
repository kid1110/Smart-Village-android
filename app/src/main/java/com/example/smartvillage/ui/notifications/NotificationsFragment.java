package com.example.smartvillage.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartvillage.OrderListActivity;
import com.example.smartvillage.R;
import com.example.smartvillage.UpdatePasswordActivity;
import com.example.smartvillage.databinding.FragmentNotificationsBinding;
import com.example.smartvillage.entity.Jwt;
import com.example.smartvillage.utils.JwtUtils;
import com.lucasurbas.listitemview.ListItemView;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private ListItemView changePassword,appointment,logout;
    private View root;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    private TextView userTitle;
    private String token;
    private Jwt jwt;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        //初始化内容
        init();
        userTitle.setText("hello: "+ jwt.getUsername());

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdatePasswordActivity.class);
                startActivity(intent);
            }
        });
        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrderListActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.clear().apply();
                getActivity().finish();

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
        changePassword = (ListItemView) root.findViewById(R.id.user_change_password);
        appointment = (ListItemView) root.findViewById(R.id.user_my_appointment);
        userTitle = (TextView)root.findViewById(R.id.userTitle);
        logout = (ListItemView) root.findViewById(R.id.user_login_out);
        sharedPreferences = getContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);

        token = sharedPreferences.getString("user",null);
        edit = sharedPreferences.edit();
        jwt = JwtUtils.getJwtInfo(token);
    }
}
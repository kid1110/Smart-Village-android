package com.example.smartvillage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartvillage.utils.BitmapUtils;
import com.example.smartvillage.utils.CameraUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.imageview.ShapeableImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPhotosActivity extends AppCompatActivity {
    //存储拍完照后的图片
    private File outputImagePath;
    private String base64Pic;
    private Bitmap orc_bitmap;
    private ShapeableImageView addPhotos;
    private byte[] data;
    //glide请求选项设置
    private RequestOptions requestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);
    //启动相机标识
    public static final int TAKE_PHOTO = 1;
    //启动相册标识
    public static final int SELECT_PHOTO = 2;

    private RxPermissions rxPermissions;
    private BottomSheetDialog bottomSheetDialog;
    private View bottomView;
    private boolean hasPermissions = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photos);
        checkVersion();
        addPhotos = findViewById(R.id.add_photos2);
        addPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAvatar(v);
            }
        });
        Button addPhotosFinish = findViewById(R.id.add_Photos_finish);
        addPhotosFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent();
              intent.putExtra("basePhotos",data);
              setResult(RESULT_OK,intent);
              finish();
            }
        });

    }
    private void showMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void checkVersion(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            rxPermissions  = new RxPermissions(this);
            rxPermissions.request(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(
                    granted->{
                        if (granted){
                            showMsg("已获取权限");
                            hasPermissions = true;
                        }else{
                            showMsg("权限未开启");
                            hasPermissions = false;
                        }
                    }
            );
        }else{
            showMsg("无需请求动态权限");
        }
    }
    public void changeAvatar(View view) {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomView = getLayoutInflater().inflate(R.layout.dialog_bottom, null);
        bottomSheetDialog.setContentView(bottomView);
        TextView tvTakePictures = bottomView.findViewById(R.id.tv_take_pictures);
        TextView tvOpenAlbum = bottomView.findViewById(R.id.tv_open_album);
        TextView tvCancel = bottomView.findViewById(R.id.tv_cancel);

        //拍照
        tvTakePictures.setOnClickListener(v -> {
            takePhoto();
            showMsg("拍照");
            bottomSheetDialog.cancel();
        });
        //打开相册
        tvOpenAlbum.setOnClickListener(v -> {
            openAlbum();
            showMsg("打开相册");
            bottomSheetDialog.cancel();
        });
        //取消
        tvCancel.setOnClickListener(v -> {
            bottomSheetDialog.cancel();
        });
        bottomSheetDialog.show();
    }
    private void takePhoto() {
        if (!hasPermissions) {
            showMsg("未获取到权限");
            checkVersion();
            return;
        }
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss");
        String filename = timeStampFormat.format(new Date());
        outputImagePath = new File(getExternalCacheDir(),
                filename + ".jpg");
        Intent takePhotoIntent = CameraUtils.getTakePhotoIntent(this, outputImagePath);
        // 开启一个带有返回值的Activity，请求码为TAKE_PHOTO
//        registerForActivityResult(,TAKE_PHOTO);
        startActivityForResult(takePhotoIntent, TAKE_PHOTO);
    }
    private void openAlbum() {
        if (!hasPermissions) {
            showMsg("未获取到权限");
            checkVersion();
            return;
        }
        startActivityForResult(CameraUtils.getSelectPhotoIntent(), SELECT_PHOTO);
    }
    private void displayImage(String imagePath){
        if(!TextUtils.isEmpty(imagePath)){
            Glide.with(this).load(imagePath).centerCrop().into(addPhotos);
            orc_bitmap = CameraUtils.compression(BitmapFactory.decodeFile(imagePath));
            data = BitmapUtils.BitmapToBytes(orc_bitmap);
            System.out.println(orc_bitmap);
            System.out.println("data"+data.toString());



        }else{
            showMsg("图片获取失败");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //拍照后返回
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //显示图片
                    displayImage(outputImagePath.getAbsolutePath());
                }
                break;
            //打开相册后返回
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    String imagePath = null;
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        //4.4及以上系统使用这个方法处理图片
                        imagePath = CameraUtils.getImageOnKitKatPath(data, this);
                    } else {
                        imagePath = CameraUtils.getImageBeforeKitKatPath(data, this);
                    }
                    //显示图片
                    displayImage(imagePath);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("basePhotos",data);
        setResult(RESULT_OK,intent);
        finish();
    }

}
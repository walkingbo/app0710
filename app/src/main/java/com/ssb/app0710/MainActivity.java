package com.ssb.app0710;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView onOffView;
    TextView provider;
    TextView allProviders;
    TextView enableProvider;
    TextView latitudeView,longitudeView,accuracyView,timestampView;

    //위치정보 파악을 위한 변수
    LocationManager manager;

    //사용 가능 공급자와 정밀도를 저장하기 위한 변수
    List<String> enableProviders;
    float bestAccuracy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allProviders =(TextView)findViewById(R.id.allproviders);
        enableProvider =(TextView)findViewById(R.id.enableprovider);
        provider=(TextView)findViewById(R.id.provider);

        latitudeView =(TextView)findViewById(R.id.latitude);
        longitudeView =(TextView)findViewById(R.id.longitude);
        accuracyView =(TextView)findViewById(R.id.accuracy);
        timestampView =(TextView)findViewById(R.id.timestamp);

        manager =(LocationManager)getSystemService(LOCATION_SERVICE);

        //동적 권한 요청 - FINE_LOCATION
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //권한요청 하기
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
        }else {
            //위치 정보 제공자를 찾아오는 메소드
            getProviders();
            //위치 정보가 변경될 때 호출될 리스너를 설정하는 메소드
            getLocation();
        }


    }

    //문자열을 매개변수로 받아서 토스트로 출력해주는 메소드
    private void showToast(String message){
        Toast toast =Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG);
        toast.show();

    }
    //첫번째 매개변수는 권한 요청을 할 때 설정한 번호
    //두번째 매개변수는 요청한 권한
    //세번째 매개변수는 사용자가 권한에 대해서 응답한 것
    @Override
    public void onRequestPermissionsResult(int requestCode, String[]permissions,int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode==100&&grantResults.length>0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getProviders();
                getLocation();
            }else {
                showToast("권한을 사용할 수 없습니다.");
            }
        }
    }
}

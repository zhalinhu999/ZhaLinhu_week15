package cn.edu.swufe.zhalinhu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FrameActivity extends FragmentActivity {

    private Fragment mFragments[];
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rbtHome,rbtFunc,rbtSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        mFragments = new Fragment[3];
        fragmentManager = getSupportFragmentManager();
        mFragments[0] = fragmentManager.findFragmentById(R.id.fragmet_main);
        mFragments[1] = fragmentManager.findFragmentById(R.id.fragmet_func);
        mFragments[2] = fragmentManager.findFragmentById(R.id.fragmet_setting);

        fragmentTransaction =
                fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
        fragmentTransaction.show(mFragments[0]).commit();
        //按钮相应
        rbtHome = (RadioButton)findViewById(R.id.radioHome);
        rbtFunc = (RadioButton)findViewById(R.id.radioFunc);
        rbtSetting = (RadioButton)findViewById(R.id.radioSetting);

        radioGroup = (RadioGroup)findViewById(R.id.bottomGroup);
        //按钮添加事件监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i("radioGroup", "checkedID: "+ checkedId);
                fragmentTransaction =
                        fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
                rbtHome.setBackgroundResource(R.drawable.shape2);
                rbtFunc.setBackgroundResource(R.drawable.shape2);
                rbtSetting.setBackgroundResource(R.drawable.shape2);

                switch (checkedId){
                    case R.id.radioHome:
                        fragmentTransaction.show(mFragments[0]).commit();
                        rbtHome.setBackgroundResource(R.drawable.shape3);
                        break;
                    case R.id.radioFunc:
                        fragmentTransaction.show(mFragments[1]).commit();
                        rbtFunc.setBackgroundResource(R.drawable.shape3);
                        break;
                    case R.id.radioSetting:
                        fragmentTransaction.show(mFragments[2]).commit();
                        rbtSetting.setBackgroundResource(R.drawable.shape3);
                        break;
                }
            }
        });

    }
}

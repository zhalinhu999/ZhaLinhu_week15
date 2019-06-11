package cn.edu.swufe.zhalinhu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FuncFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.frame_func,container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btn_fun01=(Button)getActivity().findViewById(R.id.btn_func1);
        Button btn_fun02=(Button)getActivity().findViewById(R.id.btn_func2);
        Button btn_fun03=(Button)getActivity().findViewById(R.id.btn_func3);
        Button btn_fun04=(Button)getActivity().findViewById(R.id.btn_func4);


        btn_fun01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Rateconverter.class);
                startActivity(intent);
            }
        });
        btn_fun02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Score.class);
                startActivity(intent);
            }
        });
        btn_fun03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),XueyListActivity.class);
                startActivity(intent);
            }
        });
        btn_fun04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MyList2Activity.class);
                startActivity(intent);
            }
        });

    }

}

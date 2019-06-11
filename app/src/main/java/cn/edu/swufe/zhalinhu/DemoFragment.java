package cn.edu.swufe.zhalinhu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DemoFragment extends Fragment {
    private final String TAG = "score";
    TextView score_A;
    TextView score_B;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.frame_demo,container);
    }

    public void onActivityGreate(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        View view = getActivity().getLayoutInflater().inflate(R.layout.frame_demo, null);

        score_A = view.findViewById(R.id.score_A);
        score_B= view.findViewById(R.id.score_B);
    }

    public void btnAdd1(View btn){
        if(btn.getId()==R.id.btn_score_1_A){
            showScore_A(1);
        }else{
            showScore_B(1);
        }
    }

    public void btnAdd2(View btn){
        if(btn.getId()==R.id.btn_score_2_A){
            showScore_A(2);
        }else{
            showScore_B(2);
        }
    }

    public void btnAdd3(View btn){
        if(btn.getId()==R.id.btn_score_3_A){
            showScore_A(3);
        }else{
            showScore_B(3);
        }
    }

    public void btnReset(View btn){
        score_A.setText("0");
        score_B.setText("0");
    }
    private void showScore_A(int inc){
        Log.i("show","inc="+inc);
        String oldScore = (String) score_A.getText();
        int newScore = Integer.parseInt(oldScore) + inc;
        score_A.setText(""+newScore);
    }
    private void showScore_B(int inc){
        Log.i("show","inc="+inc);
        String oldScore = (String) score_B.getText();
        int newScore = Integer.parseInt(oldScore) + inc;
        score_B.setText(""+newScore);
    }


}

package cn.edu.swufe.zhalinhu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Score extends Activity {
    private final String TAG = "score";
    TextView score_A;
    TextView score_B;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        score_A = (TextView) findViewById(R.id.score_A);
        score_B = (TextView) findViewById(R.id.score_B);
    }

    @Override
    //生命周期函数中暂时储存参数
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String scorea = ((TextView) findViewById(R.id.score_A)).getText().toString();
        String scoreb = ((TextView) findViewById(R.id.score_B)).getText().toString();

        Log.i(TAG,"onSave....");
        outState.putString("teama_score",scorea);
        outState.putString("teamb_score",scoreb);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String scorea = savedInstanceState.getString("teama_score");
        String scoreb = savedInstanceState.getString("teamb_score");

        Log.i(TAG,"onRestore...");
        ((TextView) findViewById(R.id.score_A)).setText(scorea);
        ((TextView) findViewById(R.id.score_B)).setText(scoreb);

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
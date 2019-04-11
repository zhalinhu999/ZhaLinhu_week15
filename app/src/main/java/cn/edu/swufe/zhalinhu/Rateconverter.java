package cn.edu.swufe.zhalinhu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Rateconverter extends AppCompatActivity {
    EditText rmb;
    TextView showrlt ;
    private float dollar_rate = 6.7f;
    private float pound_rate = 8.774f;
    private float yen_rate = 0.8559f;
    private float hk_rate = 0.06014f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rateconverter);
        rmb = findViewById(R.id.rmb);
        showrlt = findViewById(R.id.showrlt);
    }
    public void OnClick(View btn){
        String stn = rmb.getText().toString();
        float rmb = 0;
        if(stn.length()>0){
            rmb = Float.parseFloat(stn);
        }else{
            Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
        }
        if(btn.getId() == R.id.btn_Dollar ){
            float r = rmb * (1/ dollar_rate) ;
            showrlt.setText(String.format("%.2f",r));
        } else if (btn.getId() == R.id.btn_Pound ){
            float r = rmb * (1/ pound_rate) ;
            showrlt.setText(String.format("%.2f",r));
        }else if (btn.getId() == R.id.btn_HK ) {
            float r = rmb * (1/ hk_rate);
            showrlt.setText(String.format("%.2f", r));
        }else{
            float r = rmb * (1/ yen_rate) ;
            showrlt.setText(String.format("%.2f",r));
        }
    }
    public void openAnother(View btn){
        Intent Score = new Intent(this,Score.class);
        //Intent Web = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.baidu.com"));
        Intent Tel = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:13698568546"));
        startActivity(Score);
    }

    public void call(View btn){
//        Intent Score = new Intent(this,Score.class);
        //Intent Web = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.baidu.com"));
        Intent Tel = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:15310437089"));
        startActivity(Tel);
    }

    private void openConfig() {
        Intent Config = new Intent(this,Config.class);

        Config.putExtra("dollar_rate_key",dollar_rate);
        Config.putExtra("pound_rate_key",pound_rate);
        Config.putExtra("yen_rate_key",yen_rate);
        Config.putExtra("hk_rate_key",hk_rate);

        Log.i("TAG","openAnother : dollar_rate_key="+dollar_rate);
        Log.i("TAG","openAnother : pound_rate_key="+pound_rate);
        Log.i("TAG","openAnother : yen_rate_key="+yen_rate);
        Log.i("TAG","openAnother : hk_rate_key="+hk_rate);

        startActivityForResult(Config,1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_set){
            openConfig();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1&&resultCode == 2){
            Bundle bundle = data.getExtras();
            dollar_rate = bundle.getFloat("key_dollar",0.1f);
            pound_rate = bundle.getFloat("key_pound",0.1f);
            yen_rate = bundle.getFloat("key_yen",0.1f);
            hk_rate = bundle.getFloat("key_hk",0.1f);

            Log.i("TAG","onActivityResult: dollar_rate="+dollar_rate);
            Log.i("TAG","onActivityResult: pound_rate="+pound_rate);
            Log.i("TAG","onActivityResult: yen_rate="+yen_rate);
            Log.i("TAG","onActivityResult: hk_rate="+hk_rate);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}


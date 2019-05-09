package cn.edu.swufe.zhalinhu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Rateconverter extends AppCompatActivity implements Runnable{
    EditText rmb;
    TextView showrlt ;
    Handler handler;

    private final String TAG = "Rate";
    private float dollar_rate = 6.7f;
    private float pound_rate = 8.774f;
    private float yen_rate = 0.8559f;
    private float hk_rate = 0.06014f;

    private String updateDate = "";


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rateconverter);
        rmb = findViewById(R.id.rmb);
        showrlt = findViewById(R.id.showrlt);

        //获取sp里面的数据
        SharedPreferences sharedPreferences = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
        dollar_rate = sharedPreferences.getFloat("dollar_rate",0.0f);
        pound_rate = sharedPreferences.getFloat("pound_rate",0.0f);
        yen_rate = sharedPreferences.getFloat("yen_rate",0.0f);
        hk_rate = sharedPreferences.getFloat("hk_rate",0.0f);
        updateDate = sharedPreferences.getString("updateDate", "");

        //获得当前系统时间
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String todayStr = sdf.format(today);


        Log.i("TAG","onCreate:sp dollerRate=" + dollar_rate);
        Log.i("TAG","onCreate:sp poundRate=" + pound_rate);
        Log.i("TAG","onCreate:sp yenRate=" + yen_rate);
        Log.i("TAG","onCreate:sp hkRate=" + hk_rate);
        Log.i("TAG","onCreate:sp updateDate=" + updateDate);
        Log.i("TAG","onCreate:sp todayStr=" + todayStr);

        //判断时间
        if(!todayStr.equals(updateDate)){
            Log.i("TAG","onCreate :需要更新");//开启子线程
            Thread t = new Thread(this);
            t.start();
        }else {
            Log.i("TAG","onCreate :不需要更新");
        }

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what==5){
                    //String str  = (String)msg.obj;
                    Bundle bd1 = (Bundle) msg.obj;
                    dollar_rate = bd1.getFloat("dollar_rate");
                    pound_rate = bd1.getFloat("pound_rate");
                    yen_rate = bd1.getFloat("yen_rate");
                    hk_rate = bd1.getFloat("hk_rate");

                    Log.i(TAG,"handleMessage:dollar_rate "+dollar_rate);
                    Log.i(TAG,"handleMessage:pound_rate "+pound_rate);
                    Log.i(TAG,"handleMessage:yen_rate "+yen_rate);
                    Log.i(TAG,"handleMessage:hk_rate "+hk_rate);

                    //保存更新的日期
                    SharedPreferences sharedPreferences = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putFloat("dollar_rate",dollar_rate);
                    editor.putFloat("pound_rate",pound_rate);
                    editor.putFloat("yen_rate",yen_rate);
                    editor.putFloat("hk_rate",hk_rate);
                    editor.putString("update_date",todayStr);
                    editor.commit();

                    Toast.makeText(Rateconverter.this,"汇率已更新",Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };
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
        }else if(item.getItemId()==R.id.open_list){
            //打开列表
            Intent list = new Intent(this,MyList2Activity.class);
            startActivity(list );
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

            //将新设置的汇率写到sp里面
            SharedPreferences sharedPreferences = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("dollar_rate",dollar_rate);
            editor.putFloat("pound_rate",pound_rate);
            editor.putFloat("yen_rate",yen_rate);
            editor.putFloat("hk_rate",hk_rate);
            editor.commit();
            Log.i("TAG","onActivityResult: 数据已经保存到sharedPreferences");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void run() {
        Log.i(TAG,"run().....");
        for (int i = 1;i<=3;i++){
            Log.i(TAG ,"run:i = "+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //用户保存获取的汇率
        Bundle bundle = new  Bundle();

        //获取网络数据
//        URL url = null;
//        try {
//            url = new URL("http://www.usd-cny.com/bankofchina.htm");
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            InputStream in = http.getInputStream();
//
//            String html = inputStream2String(in);
//            Log.i(TAG,"run:html="+html);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG,"run"+doc.title());
            Elements tables = doc.getElementsByTag("table");

            Element table6 = tables.get(0);
            //Log.i(TAG,"run"+tables);
            //获取td中的数据
            Elements tds = table6.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                Log.i(TAG,"run:"+ td1.text() + "==>" + td2.text());

                String str1 = td1.text();
                String val = td2.text();

                if("美元".equals(str1)){
                    bundle.putFloat("dollar_rate",100f/Float.parseFloat(val));
                }else if("英镑".equals(str1)){
                    bundle.putFloat("pound_rate",100f/Float.parseFloat(val));
                }else if("日元".equals(str1)){
                    bundle.putFloat("yen_rate",100f/Float.parseFloat(val));
                }else if("港币".equals(str1)){
                    bundle.putFloat("hk_rate",100f/Float.parseFloat(val));
                }
            }




        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取Msg对象，用于返回主线程
        Message msg = handler.obtainMessage(5);
        //msg.what = 5;
        //msg.obj = "hello from run()";
        msg.obj = bundle;
        handler.sendMessage(msg);

    }
    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[]bufer = new char[bufferSize];
        final StringBuilder out  = new StringBuilder();
        Reader in = new InputStreamReader(inputStream,"gb2312");
        for (;;){
            int rsz = in.read(bufer,0,bufer.length);
            if(rsz<0)
                break;
            out.append(bufer,0,rsz);
        }
        return out.toString();
    }
}


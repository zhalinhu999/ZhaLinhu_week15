package cn.edu.swufe.zhalinhu;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RateListActivity extends ListActivity implements Runnable{
    String data[] = {"one","two","three"};
    Handler handler;
    private final String TAG = "Rate";

    private String logDate = "";
    private  final String DATE_SP_KEY = "lastRateDateStr";

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<String> list1 = new ArrayList<String>();
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ratelist);已经有了布局

        SharedPreferences sp = getSharedPreferences("myrate",Context.MODE_PRIVATE);
        logDate = sp.getString(DATE_SP_KEY,"");
        Log.i("List","lastRateDateStr="+ logDate);

        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        setListAdapter(adapter);

        Thread t = new Thread(this);
        t.start();

    handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==7){
                List<String> list2  = (List<String>) msg.obj;
                ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,list2);
                setListAdapter(adapter);
            }
        }
    };

    }

    @Override
    public void run() {
        //获取网络数据,带回到list的主线程
        List<String> retList = new ArrayList<String>();
        String curDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        Log.i(TAG, "run: curDateStr:"+curDateStr+"logDate:"+ logDate);

        if(curDateStr.equals(logDate)){
            //不从网络获取数据
            Log.i(TAG, "run: 日期相等，从数据库获取数据");
            RateManager manager = new RateManager(this);
            for (RateItem item : manager.listAll()){
                retList.add(item.getCurName() + "-->" + item.getCurRate());
            }
        }else {
            Log.i(TAG, "run: 日期不相等，获取网络数据");
            Document doc = null;
            try {
                doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
                Log.i(TAG,"run"+doc.title());
                Elements tables = doc.getElementsByTag("table");

                Element table6 = tables.get(0);
                //Log.i(TAG,"run"+tables);
                //获取td中的数据
                Elements tds = table6.getElementsByTag("td");

                //保存在数据库中
                List<RateItem> ratelist = new ArrayList<RateItem>();
                for(int i=0;i<tds.size();i+=6){
                    Element td1 = tds.get(i);
                    Element td2 = tds.get(i+5);
                    Log.i(TAG,"run:"+ td1.text() + "==>" + td2.text());

                    String str1 = td1.text();
                    String val = td2.text();

                    retList.add(str1+"==>" + val);

                    ratelist.add(new RateItem(str1,val));
                }

                //把数据写入数据库中
                RateManager manager = new RateManager(this);
                manager.deletAll();
                manager.addAll(ratelist);

                //记录更新日期
                SharedPreferences sp = getSharedPreferences("myrate",Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString(DATE_SP_KEY,curDateStr);
                edit.commit();
                Log.i(TAG, "run: 更新日期结束"+ curDateStr);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        Message msg = handler.obtainMessage(7);
        msg.obj = retList;
        handler.sendMessage(msg);

    }
}

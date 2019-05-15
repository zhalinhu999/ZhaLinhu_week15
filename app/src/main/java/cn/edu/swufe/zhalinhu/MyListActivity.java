package cn.edu.swufe.zhalinhu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<String> date = new ArrayList<String>();
    private String TAG  ="MyList";
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        ListView listView = (ListView) findViewById(R.id.myList);

        //init date
        for(int i=0;i<=10;i++){
            date.add("item" + i);
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,date);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.nodate));//没有数据的时候显示
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> listv, View view, int position, long id) {
        Log.i(TAG, "onItemClick: position " + position);

        adapter.remove(listv.getItemAtPosition(position));//移除数据，自动刷新
        //adapter.notifyDataSetChanged();
    }
}

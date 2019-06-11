package cn.edu.swufe.zhalinhu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class XueyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuey);

        String title = getIntent().getStringExtra("title");
        String http = getIntent().getStringExtra("http");

        ((TextView) findViewById(R.id.xy_name)).setText(title);
        ((TextView) findViewById(R.id.xy_http)).setText(http);
    }
}

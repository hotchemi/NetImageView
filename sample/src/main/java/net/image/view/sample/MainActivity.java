package net.image.view.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list);
        List<String> list = Arrays.asList(
                "http://rejasupotaro.github.io/images/rejasupotaro.jpg?1402842499",
                "https://pbs.twimg.com/profile_images/378800000194224027/5d5d92036fba329fbfc83f06bb2f464f.jpeg"
        );
        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
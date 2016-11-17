package com.example.nemanja.simpletodolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class SimpleToDoListActivity extends AppCompatActivity {

    private EditText item;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
            private final String PREFS_TASKS = "prefs_tasks";
            private final String KEY_TASKS_LIST = "list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_to_do_list);

        item = (EditText) findViewById(R.id.itemEditText);
        ImageButton add = (ImageButton) findViewById(R.id.add_item_button);
        ListView dynamicListView = (ListView) findViewById(R.id.itemsListView);

        list = new ArrayList<>();
        list.add("Execom Test");

        String savedList = getSharedPreferences(PREFS_TASKS, MODE_PRIVATE).getString(KEY_TASKS_LIST, null);
            if (savedList != null) {
                String[] items = savedList.split(",");
                list = new ArrayList<>(Arrays.asList(items));
                }

        adapter = new ArrayAdapter<>(SimpleToDoListActivity.this, android.R.layout.simple_list_item_1, list);
        dynamicListView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = item.getText().toString();
                if (todoItem.length() > 0) {
                    list.add(todoItem);
                    adapter.notifyDataSetChanged();
                    item.setText("");
                }
            }
        });

        dynamicListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_simple_to_do_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();

        StringBuilder savedList = new StringBuilder();
        for (String s : list) {
           savedList.append(s);
           savedList.append(",");
        }
        getSharedPreferences(PREFS_TASKS, MODE_PRIVATE).edit()
        .putString(KEY_TASKS_LIST, savedList.toString()).commit();
                 }
 }





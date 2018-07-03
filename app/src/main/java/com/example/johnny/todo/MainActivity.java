package com.example.johnny.todo;

//Simple To Do list application that uses Adapters and a text file where the data is saved
//Made by Xhoni Robo
// https://github.com/Decalz

//Imports generated automatically from Android Studio
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readItems();                                                                                //read the items on the text file
        lvItems = (ListView) findViewById(R.id.lvItems);
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);                                        //Initialize an adapter to keep count of the items in the list
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();                                                                    //Listener used to remove items from the list
    }

                                                                                                    //LongClickListener attached to lstview
    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                                                                                                    //Remove the item within array at the specified position
                        items.remove(pos);
                                                                                                    //Refresh the adapter
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                                                                                                    //Return true consumes the long click event (marks it handled)
                        return true;
                    }
                });
    }

    public void onAddItem(View v) {                                                                 //Function to add an item
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    private void readItems() {                                                                      //Function to read items from a text file
        File filesDir = getFilesDir();                                                              //FileUtils requires dependencies specified in the build.gradle Module: app
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {                                                                     //Function to write the items into a text file. This allows to
        File filesDir = getFilesDir();                                                              //read the items later if the application is closed
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

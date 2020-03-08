package ht.berth.relel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ht.berth.relel.DBO.MyDBHelper;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addBtn;
    RecyclerView recyclerView;
    RecyclerCustomAdapter adapter;
    MyDBHelper myDBHelper = new MyDBHelper(this);
    ArrayList<String> contactIdAl, fnameAL, lnameAL, numberAL, addressAL, emailAL;
    TextView number_contact ;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addBtn = (FloatingActionButton) findViewById(R.id.add_button);
        number_contact = new TextView(this);
        number_contact =findViewById(R.id.number_contact);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        contactIdAl = new ArrayList<>();
        fnameAL = new ArrayList<>();
        lnameAL = new ArrayList<>();
        numberAL = new ArrayList<>();
        addressAL = new ArrayList<>();
        emailAL = new ArrayList<>();
        //
        ArrayDataStorage();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new RecyclerCustomAdapter(MainActivity.this, MainActivity.this, contactIdAl, fnameAL, lnameAL, numberAL, addressAL, emailAL);
        recyclerView.setAdapter(adapter);
        //Pa bliye korige bug sa
        //Lew gen yon LayoutManager event lan pa mache sou addbtn na
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    void ArrayDataStorage() {
        Cursor cursor = myDBHelper.readAllData();

        int number;
        number = cursor.getCount();
        number_contact.setText(String.valueOf(number));

        if (cursor.getCount() == 0) {
            //Pa Bliye
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                contactIdAl.add(cursor.getString(0));
                fnameAL.add(cursor.getString(1));
                lnameAL.add(cursor.getString(2));
                numberAL.add(cursor.getString(3));
                addressAL.add(cursor.getString(4));
                emailAL.add(cursor.getString(5));

            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView search = (SearchView) menuItem.getActionView();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Cursor cursor = myDBHelper.searchData(s.toLowerCase());
                if (cursor.getCount() > 0) {
                    do {
                        contactIdAl.add(cursor.getString(0));
                        fnameAL.add(cursor.getString(1));
                        lnameAL.add(cursor.getString(2));
                        numberAL.add(cursor.getString(3));
                        addressAL.add(cursor.getString(4));
                        emailAL.add(cursor.getString(5));
                    } while (cursor.moveToNext());
                }
                cursor.close();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDBHelper db = new MyDBHelper(MainActivity.this);
                db.deleteAll();
                //Refresh Activity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.LEFT) {

            }
        }
    };

}

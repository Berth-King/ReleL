package ht.berth.relel;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ht.berth.relel.DBO.MyDBHelper;

public class Item extends AppCompatActivity {
    TextView fnameT, lnameT, numberT, addressT, emailT, idT;
    String id, fname, lname, number, address, email;
    FloatingActionButton edit;
    ImageView imageCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        fnameT = (TextView) findViewById(R.id.item_first_name);
        lnameT = (TextView) findViewById(R.id.item_last_name);
        numberT = (TextView) findViewById(R.id.item_number);
        addressT = (TextView) findViewById(R.id.item_address);
        emailT = (TextView) findViewById(R.id.item_email);
        imageCall = new ImageView(this);
        imageCall = findViewById(R.id.call_item);


        getAndSetIntentData();

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(fname + " " + lname);
        }

        edit = (FloatingActionButton) findViewById(R.id.edit_item);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Item.this, ModifyActicity.class);
                intent.putExtra("I", id);
                intent.putExtra("F", String.valueOf(fname));
                intent.putExtra("L", String.valueOf(lname));
                intent.putExtra("N", String.valueOf(number));
                intent.putExtra("A", String.valueOf(address));
                intent.putExtra("E", String.valueOf(email));
                startActivityForResult(intent, 1);

            }
        });


        numberT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(view);
            }
        });
        getAndSetIntentData();
        imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageCall.setBackgroundColor(Color.RED);
                call(view);
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);
        MenuItem delete = menu.findItem(R.id.delete);
        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                alertDiag();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("FirstName") && getIntent().hasExtra("LastName") &&
                getIntent().hasExtra("Number") && getIntent().hasExtra("Address") &&
                getIntent().hasExtra("Email") && getIntent().hasExtra("Id")) {
            //Getting Data from Intent
            id = getIntent().getStringExtra("Id");
            fname = getIntent().getStringExtra("FirstName");
            lname = getIntent().getStringExtra("LastName");
            number = getIntent().getStringExtra("Number");
            address = getIntent().getStringExtra("Address");
            email = getIntent().getStringExtra("Email");

            //Setting Intent Data
            fnameT.setText(fname);
            lnameT.setText(lname);
            numberT.setText(number);
            addressT.setText(address);
            emailT.setText(email);
//            Log.d("stev", title+" "+author+" "+pages);
        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void alertDiag() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete" + fname + " " + lname + " ?");
        builder.setMessage("Are yo sure you want to delete" + fname + " " + lname + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDBHelper db = new MyDBHelper(Item.this);
                db.delete(id);
                startActivity(new Intent(Item.this, MainActivity.class));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();

    }

    public void call(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:+" + number));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        startActivity(intent);
    }
}

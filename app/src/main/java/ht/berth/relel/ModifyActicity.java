package ht.berth.relel;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ht.berth.relel.DBO.MyDBHelper;

public class ModifyActicity extends AppCompatActivity {
    EditText fnameET, lnameET, numberET, addressET , emailET;
    String id ,fname, lname, number, address , email;
    FloatingActionButton modification;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item_modi, menu);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_acticity);
        fnameET = findViewById(R.id.mod_first_name);
        lnameET = findViewById(R.id.mod_last_name);
        numberET = findViewById(R.id.mod_number);
        addressET = findViewById(R.id.mod_address);
        emailET = findViewById(R.id.mod_email);
        modification = findViewById(R.id.modify);

        getAndSetIntentData();
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(fname + " "+ lname);
        }

        modification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lol men sa ki tap fe w trouble lan pa bliye pran nouvo val EditTextyo
                fname = fnameET.getText().toString().trim();
                lname = lnameET.getText().toString().trim();
                number = numberET.getText().toString().trim();
                address = addressET.getText().toString().trim();
                email = emailET.getText().toString().trim();

                MyDBHelper db = new MyDBHelper(ModifyActicity.this);
                db.updateChanges(id, fname, lname,number,address, email);
                startActivity(new Intent(ModifyActicity.this, MainActivity.class));
            }
        });
    }



    void getAndSetIntentData(){
        if(getIntent().hasExtra("F") && getIntent().hasExtra("L") &&
                getIntent().hasExtra("N") && getIntent().hasExtra("A") &&
                getIntent().hasExtra("E") && getIntent().hasExtra("I")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("I");
            fname = getIntent().getStringExtra("F");
            lname = getIntent().getStringExtra("L");
            number = getIntent().getStringExtra("N");
            address = getIntent().getStringExtra("A");
            email = getIntent().getStringExtra("E");

            //Setting Intent Data
            fnameET.setText(fname);
            lnameET.setText(lname);
            numberET.setText(number);
            addressET.setText(address);
            emailET.setText(email);
            Log.d("Contact", fname+" "+lname+" "+numberET);
        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();        }
    }
    void alertDiag(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete" + fname +" " + lname +  " ?");
        builder.setMessage("Are yo sure you want to delete" + fname +" " + lname +  " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDBHelper db = new MyDBHelper(ModifyActicity.this);
                db.delete(id);
                startActivity(new Intent(ModifyActicity.this, MainActivity.class));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();

    }
}

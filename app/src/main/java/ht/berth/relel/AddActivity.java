package ht.berth.relel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hbb20.CountryCodePicker;


import ht.berth.relel.DBO.MyDBHelper;

public class AddActivity extends AppCompatActivity {
    EditText fnameET , lnameET, numberET, addressET , emailET ;
    FloatingActionButton save_btn;
    CountryCodePicker codePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        codePicker = new CountryCodePicker(AddActivity.this);
        codePicker = (CountryCodePicker) findViewById(R.id.code_picker);

        fnameET = (EditText) findViewById(R.id.first_name);
        lnameET = (EditText) findViewById(R.id.last_name);
        numberET = (EditText) findViewById(R.id.number);
        addressET = (EditText) findViewById(R.id.address);
        emailET = (EditText) findViewById(R.id.email);

        save_btn = (FloatingActionButton) findViewById(R.id.save);


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDBHelper myDB = new MyDBHelper(AddActivity.this);
                if (!(fnameET.getText().toString().trim().isEmpty() ) && !(lnameET.getText().toString().trim().isEmpty()) && !(numberET.getText().toString().trim().isEmpty()) ) {
                    String full_number = codePicker.getFullNumber() + numberET.getText().toString().trim() ;
                    myDB.saveContact(fnameET.getText().toString().trim(),
                            lnameET.getText().toString().trim(),
                            full_number,
                            addressET.getText().toString().trim(),
                            emailET.getText().toString().trim());
                    startActivity(new Intent(AddActivity.this, MainActivity.class));
                }else {
                    Toast.makeText(AddActivity.this, "Set First Or Last name Or Number please " , Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}

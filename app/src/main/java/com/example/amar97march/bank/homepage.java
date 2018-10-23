package com.example.amar97march.bank;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amar97march on 17-10-2018.
 */

public class homepage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseHelper myDb;
    TextView userName;
    TextView money,transHis;
    EditText acHolder;
    EditText moneyTrans;
    Button send, signOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        Intent intent = getIntent();
        final String recievedEmail = intent.getStringExtra("email");

        System.out.println(recievedEmail);
        userName = (TextView) findViewById(R.id.username);
        money = (TextView) findViewById(R.id.money);
        transHis = (TextView) findViewById(R.id.transactonHis);
        acHolder = (EditText) findViewById(R.id.acHolder);
        moneyTrans = (EditText) findViewById(R.id.moneyTrans);
        send = (Button) findViewById(R.id.sendMoney);
        signOut = (Button) findViewById(R.id.signOut);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);



        myDb = MainActivity.myDb;
        Cursor res = myDb.getAllData(recievedEmail);
        if (res.getCount() == 0){
            //Show message
            return;
        }

        if (res != null && res.moveToFirst()) {
            userName.setText(res.getString(1));
            money.setText(res.getString(3));
            transHis.setText(res.getString(6));
            res.close();
        }

        //Signing out
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(homepage.this, MainActivity.class);
                startActivity(i);
            }
        });

        //Spinner implementation
        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("50");
        categories.add("100");
        categories.add("500");
        categories.add("1000");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        //send money button action
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = myDb.updateMoney(recievedEmail,acHolder.getText().toString(),Integer.parseInt(moneyTrans.getText().toString()));
                if (result == true)
                    Toast.makeText(homepage.this,moneyTrans.getText().toString()+" transferred",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(homepage.this,"Invalid Receiver",Toast.LENGTH_LONG).show();
                recreate();

            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        moneyTrans.setText(parent.getItemAtPosition(position).toString());


    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }



}

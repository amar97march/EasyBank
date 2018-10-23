package com.example.amar97march.bank;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(MainActivity.this);

        final Button loginBtn = (Button) findViewById(R.id.loginbtn);
        final EditText loginEmail = (EditText) findViewById(R.id.loginemail);
        final EditText loginPassword = (EditText) findViewById(R.id.loginPassword);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData(loginEmail.getText().toString());
                if (res.getCount() == 0){
                    Toast.makeText(MainActivity.this,"Invalid Email",Toast.LENGTH_LONG).show();
                    return;
                }

                if (res != null && res.moveToFirst()) {

                    if ( loginPassword.getText().toString().equals(res.getString(5)) ){
                        System.out.println(res.getString(5));
                        res.close();
                        Intent i = new Intent(MainActivity.this, homepage.class);
                        i.putExtra("email",loginEmail.getText().toString());
                        startActivity(i);
                    }else{
                        Toast.makeText(MainActivity.this,"Invalid Password",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });






        Button signupBtn = (Button) findViewById(R.id.signupBtn);


        signupBtn.setOnClickListener(new View.OnClickListener(){


        @Override
        public void onClick (View v) {
            Intent i = new Intent(MainActivity.this, signup.class);

            startActivity(i);
        }
        });
    }

    @Override
    public void onBackPressed() {
        //Do nothing
    }
}

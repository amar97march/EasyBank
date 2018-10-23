package com.example.amar97march.bank;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.Toast;

/**
 * Created by amar97march on 16-10-2018.
 */

public class signup extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText firstName, lastName, email,password;
    Button signupinnerBtn;
    CheckBox chkbx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        myDb = MainActivity.myDb;

        signupinnerBtn = (Button) findViewById(R.id.signupinner);
        firstName = (EditText) findViewById(R.id.firstEntry);
        lastName = (EditText) findViewById(R.id.lastEntry);
        email = (EditText) findViewById(R.id.emailEntry);
        password = (EditText) findViewById(R.id.passwordEntry);
        chkbx = (CheckBox) findViewById(R.id.checkBox);
        signupinnerBtn.setClickable(false);

        //Checkbox
        chkbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chkbx.isChecked()) {
                    signupinnerBtn.setClickable(true);
                }else {
                    signupinnerBtn.setClickable(false);
                }

            }
        });


        signupinnerBtn.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick (View v) {

                boolean isInserted = false;
                if(firstName != null && lastName != null && email != null && password != null){
                    isInserted = myDb.insertData(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), password.getText().toString());
                }
                if (isInserted == true) {
                    Toast.makeText(signup.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(signup.this, homepage.class);
                    i.putExtra("email", email.getText().toString());
                    startActivity(i);
                }
                else if(isInserted == false)
                    Toast.makeText(signup.this,"Registered Unsuccessfully",Toast.LENGTH_LONG).show();


            }
        });

    }
}

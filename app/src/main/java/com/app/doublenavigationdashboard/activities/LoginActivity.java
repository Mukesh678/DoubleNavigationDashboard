package com.app.doublenavigationdashboard.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.doublenavigationdashboard.R;
import com.app.doublenavigationdashboard.database.DatabaseHelper;
import com.app.doublenavigationdashboard.fragment.CustomerDetailsFragment;

/**
 * Created by d on 07/07/2016.
 */
public class LoginActivity extends AppCompatActivity {
    EditText user, pass;
    Button login, not_reg;
    DatabaseHelper db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user =(EditText)findViewById(R.id.eduser);
        pass = (EditText)findViewById(R.id.edpass);
        login =(Button)findViewById(R.id.login);
        not_reg =(Button)findViewById(R.id.not_reg);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                db=new DatabaseHelper(LoginActivity.this);
                String username=user.getText().toString();
                String password= pass.getText().toString();

                String StoredPassword =db.getregister(username);
                if(password.equals(StoredPassword)){

                    Toast.makeText(getApplicationContext(), StoredPassword+"Login Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }
                else{
                    Toast.makeText(getApplicationContext(), "Username/Password incorrect", Toast.LENGTH_LONG).show();
                    user.setText("");
                    pass.setText("");
                }


            }
        });

        not_reg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));

            }
        });





    }

    /* @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_settings, menu);
            return super.onCreateOptionsMenu(menu);
           //
     }
     */
   /* public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_settings:
                // search action
                Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
                return true;
        }
        return false;

    }*/

}

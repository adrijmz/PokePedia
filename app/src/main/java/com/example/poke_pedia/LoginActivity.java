package com.example.poke_pedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poke_pedia.db.DataBaseUsers;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DataBaseUsers DB = new DataBaseUsers(this);

        EditText username = findViewById(R.id.username_edittxt_login);
        EditText password = findViewById(R.id.password_edittxt_login);
        Button loginButton = findViewById(R.id.signin_button_login);

        TextView forgotPass = findViewById(R.id.forgot_pass_textview);
        forgotPass.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ForgotPassword.class)));

        loginButton.setOnClickListener(view -> {

            String user = username.getText().toString();
            String pass = password.getText().toString();

            if(user.equals("")||pass.equals(""))
                Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            else{
                boolean checkUserPass = DB.checkUserPass(user, pass);
                if(checkUserPass){
                    Toast.makeText(LoginActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                    MySharedPreference.setUserName(LoginActivity.this,user);
                    startActivity(intent);
                }
                else
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        Button createUser = findViewById(R.id.create_user_button_login);
        createUser.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),SignupActivity.class)));
    }
}
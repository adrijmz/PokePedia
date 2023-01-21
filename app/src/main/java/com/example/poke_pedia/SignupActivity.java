package com.example.poke_pedia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.poke_pedia.db.DataBaseUsers;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if(MySharedPreference.getUserName(SignupActivity.this).length()>0){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        NotificationHandler handler = new NotificationHandler(this);
        DataBaseUsers DB = new DataBaseUsers(this);

        EditText username = findViewById(R.id.username_edittxt_signup);
        EditText password = findViewById(R.id.password_edittxt_signup);
        EditText email = findViewById(R.id.email_edittxt_signup);
        Button signup = findViewById(R.id.register_button_signup);
        Button signin = findViewById(R.id.signin_button_signup);

        signup.setOnClickListener(view -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            String emailStr = email.getText().toString();

            if(user.equals("")||pass.equals("")||emailStr.equals(""))
                Toast.makeText(SignupActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            else{
                boolean checkUser = DB.checkUsername(user);
                if(!checkUser){
                    boolean insert = DB.insertData(user, pass);
                    if(insert){
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        Toast.makeText(SignupActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                        Notification.Builder nBuilder = handler.createNotification("PokePedia register", "We have sent you an email to verify your account", true);
                        handler.getManager().notify(1,nBuilder.build());
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(SignupActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(SignupActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
            }
        });
        signin.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
    }
}
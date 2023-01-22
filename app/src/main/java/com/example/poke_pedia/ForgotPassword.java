package com.example.poke_pedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.poke_pedia.db.DataBaseUsers;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        EditText username = findViewById(R.id.email_edittxt_forgot_pass);
        EditText newPass = findViewById(R.id.password_edittxt_forgot_pass);
        Button changePass = findViewById(R.id.change_pass_button_forgot_pass);

        DataBaseUsers DB = new DataBaseUsers(this);

        changePass.setOnClickListener(view -> {
            String user = username.getText().toString();
            String pass = newPass.getText().toString();

            if(user.equals("")||pass.equals(""))
                Toast.makeText(ForgotPassword.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            else{
                boolean checkUserExists = DB.checkUserExists(user);
                if(checkUserExists){
                    DB.changePassword(user,pass);
                    Toast.makeText(ForgotPassword.this, "Password changed succesfully", Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(ForgotPassword.this, "Unable to find that user", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView goBack = findViewById(R.id.go_back_forgot_pass);
        goBack.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));
    }
}
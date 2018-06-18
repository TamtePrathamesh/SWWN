package com.handsintech.coder.e_astro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class login_activity extends AppCompatActivity{

    EditText epass,eemail;
    String editemail;
    String editpass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity1);
        getSupportActionBar().hide();
        eemail = (EditText)findViewById(R.id.email);
        epass=(EditText)findViewById(R.id.password);
    }
    public void onLogin(View v){
         editpass=epass.getText().toString();
         editemail = eemail.getText().toString();
         System.out.println("after credentials retrieve: ");
         System.out.println("email="+editemail + " password=" + editpass);
    }

    public void forgotPassword(View v){
        Intent recover = new Intent(this,RecoverAccount.class);
        startActivity(recover);
    }

}


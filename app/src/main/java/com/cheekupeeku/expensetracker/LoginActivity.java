package com.cheekupeeku.expensetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cheekupeeku.expensetracker.dao.UserDAO;
import com.cheekupeeku.expensetracker.databinding.RegisterBinding;
import com.cheekupeeku.expensetracker.model.User;

public class LoginActivity extends AppCompatActivity {
    RegisterBinding binding;
    SharedPreferences sp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RegisterBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        sp = getSharedPreferences("user",MODE_PRIVATE);
        binding.btnAction.setText("Login");
        binding.tvNewUser.setVisibility(View.VISIBLE);

        String mobile = sp.getString("mobile","");
        if(!mobile.equals("")){
            sendUserToMainActivity();
            finish();
        }

        binding.tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(in);
                finish();
            }
        });
        binding.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = binding.etMobile.getText().toString();
                String password = binding.etPassword.getText().toString();

                User user = new User(mobile,password);
                if(UserDAO.authenticateUser(LoginActivity.this,user)){
                    Toast.makeText(LoginActivity.this, "Login sucess", Toast.LENGTH_SHORT).show();
                    sendUserToMainActivity();
                }
                else
                    Toast.makeText(LoginActivity.this, "Invalid user", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void sendUserToMainActivity(){
        Intent in = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(in);
    }
}

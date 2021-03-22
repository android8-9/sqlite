package com.cheekupeeku.expensetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cheekupeeku.expensetracker.dao.UserDAO;
import com.cheekupeeku.expensetracker.databinding.RegisterBinding;
import com.cheekupeeku.expensetracker.model.User;

public class RegisterActivity extends AppCompatActivity {
    RegisterBinding binding;
    SharedPreferences sp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RegisterBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        binding.tvNewUser.setVisibility(View.GONE);

        sp = getSharedPreferences("user",MODE_PRIVATE);
        binding.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = binding.etMobile.getText().toString();
                String password = binding.etPassword.getText().toString();
                User user = new User(mobile,password);
                if(UserDAO.save(RegisterActivity.this,user)){
                    Toast.makeText(RegisterActivity.this, "Registeration success", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(RegisterActivity.this,MainActivity.class);

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("mobile",user.getMobile());
                    editor.commit();
                    startActivity(in);
                    finish();
                }
                else
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


package com.cheekupeeku.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.cheekupeeku.expensetracker.dao.CategoryDAO;
import com.cheekupeeku.expensetracker.dao.ExpenseDAO;
import com.cheekupeeku.expensetracker.databinding.ActivityMainBinding;
import com.cheekupeeku.expensetracker.databinding.AddExpenseBinding;
import com.cheekupeeku.expensetracker.model.Category;
import com.cheekupeeku.expensetracker.model.Expense;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Add expenses");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String title = item.getTitle().toString();
        if(title.equals("Add expenses")){
            AddExpenseBinding binding = AddExpenseBinding.inflate(LayoutInflater.from(this));
            AlertDialog.Builder ab = new AlertDialog.Builder(this);
            ab.setView(binding.getRoot());
            ab.setTitle("Add expense");

            ArrayList<Category>al = CategoryDAO.getCategoryList(MainActivity.this);
            ArrayAdapter<Category>adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_checked,al);
            binding.categorySpinner.setAdapter(adapter);

            binding.tvDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog dp = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                          date = dayOfMonth+"-"+(month+1)+"-"+year;
                          binding.tvDate.setText(date);
                        }
                    },2021,2,18);
                    dp.show();
                }
            });
            ab.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  Category c =(Category) binding.categorySpinner.getSelectedItem();
                  String tag = binding.etTag.getText().toString();
                  int amount = Integer.parseInt(binding.etAmount.getText().toString());
                  String paymentMode = "";
                  if(binding.rdCash.isChecked())
                      paymentMode = "Cash";
                  else if(binding.rdOnline.isChecked())
                      paymentMode = "Online";
                    Expense e = new Expense();
                    e.setAmount(amount);
                    e.setCategoryId(c.getId());
                    e.setEdate(date);
                    e.setPaymentMode(paymentMode);
                    e.setTag(tag);
                    boolean status = ExpenseDAO.save(MainActivity.this,e);
                    if(status)
                        Toast.makeText(MainActivity.this, "Expense saved", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
                }
            });
            ab.setNegativeButton("Cancel",null);
            ab.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
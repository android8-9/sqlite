package com.cheekupeeku.expensetracker.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.cheekupeeku.expensetracker.DatabaseHelper;
import com.cheekupeeku.expensetracker.dao.CategoryDAO;
import com.cheekupeeku.expensetracker.dao.ExpenseDAO;
import com.cheekupeeku.expensetracker.databinding.ExpenseItemListBinding;
import com.cheekupeeku.expensetracker.model.Category;
import com.cheekupeeku.expensetracker.model.Expense;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {
    Context context;
    ArrayList<Expense>al;
    public ExpenseAdapter(Context context,ArrayList<Expense>al){
        this.context = context;
        this.al = al;
    }
    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExpenseItemListBinding binding = ExpenseItemListBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ExpenseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
       Expense e = al.get(position);
       holder.binding.tvAmount.setText(""+e.getAmount());
       holder.binding.tvCategory.setText(e.getCategoryName());
       holder.binding.tvDate.setText(e.getEdate());
       holder.binding.tvTag.setText(e.getTag());
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder{
       ExpenseItemListBinding binding;
       public ExpenseViewHolder(ExpenseItemListBinding binding){
           super(binding.getRoot());
           this.binding = binding;
           binding.ivMenu.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int position = getAdapterPosition();
                   Expense e = al.get(position);

                   PopupMenu popupMenu = new PopupMenu(context,binding.ivMenu);
                   Menu menu = popupMenu.getMenu();
                   menu.add("View");
                   menu.add("Edit");
                   menu.add("Delete");
                   popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                       @Override
                       public boolean onMenuItemClick(MenuItem item) {
                           String title = item.getTitle().toString();
                           if(title.equals("View")){

                           }
                           else if(title.equals("Edit")){
                               com.cheekupeeku.expensetracker.databinding.AddExpenseBinding
                                       addBinding = com.cheekupeeku.expensetracker.databinding.AddExpenseBinding.inflate(LayoutInflater.from(context));
                               AlertDialog.Builder ab = new AlertDialog.Builder(context);
                               ab.setTitle("Update expense");

                               ArrayList<Category>categoryList = CategoryDAO.getCategoryList(context);
                               categoryList.add(0,new Category(e.getCategoryId(),e.getCategoryName()));
                               addBinding.categorySpinner.setAdapter(new ArrayAdapter<Category>(context, android.R.layout.simple_list_item_checked,categoryList));
                               addBinding.etTag.setText(e.getTag());
                               addBinding.tvDate.setText(e.getEdate());
                               addBinding.etAmount.setText(""+e.getAmount());
                               if(e.getPaymentMode().equalsIgnoreCase("cash"))
                                   addBinding.rdCash.setChecked(true);
                               else if(e.getPaymentMode().equalsIgnoreCase("online"))
                                   addBinding.rdOnline.setChecked(true);

                               ab.setView(addBinding.getRoot());
                               ab.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       SQLiteDatabase database = new DatabaseHelper(context).getWritableDatabase();
                                       ContentValues values = new ContentValues();
                                       Category c = (Category) addBinding.categorySpinner.getSelectedItem();
                                       values.put("category_id",c.getId());
                                       values.put("edate",addBinding.tvDate.getText().toString());
                                       values.put("tag",addBinding.etTag.getText().toString());
                                       values.put("amount",addBinding.etAmount.getText().toString());
                                       String paymentMode = "";
                                       if(addBinding.rdCash.isChecked()) paymentMode = "Cash";
                                       else if(addBinding.rdOnline.isChecked()) paymentMode = "Online";
                                       values.put("payment_mode",paymentMode);
                                       database.update("expense",values,"id=?",new String[]{""+e.getId()});
                                       Toast.makeText(context, "Expense updated", Toast.LENGTH_SHORT).show();


                                   }
                               });
                               ab.setNegativeButton("Cancel",null);
                               ab.show();
                           }
                           else if(title.equals("Delete")){
                               AlertDialog.Builder ab = new AlertDialog.Builder(context);
                               ab.setTitle("Deleting expense");
                               ab.setMessage("Are you sure ?");
                               ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       boolean status = ExpenseDAO.deleteExpense(e.getId(),context);
                                       if(status){
                                           al.remove(position);
                                           notifyDataSetChanged();
                                       }
                                   }
                               });
                               ab.setNegativeButton("No",null);
                               ab.show();
                           }
                           return false;
                       }
                   });
                   popupMenu.show();
               }
           });
       }
   }
}

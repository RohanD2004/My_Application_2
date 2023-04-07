package com.pqr.myapplication.ui.Add;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pqr.myapplication.DBHelper;
import com.pqr.myapplication.MainActivity;
import com.pqr.myapplication.R;

import java.util.ArrayList;

public class AddStockFragment extends Fragment {

    private AddStockViewModel mViewModel;
    View view;
    AutoCompleteTextView tname ;
    AutoCompleteTextView tcode;
    AutoCompleteTextView tqty ;
    AutoCompleteTextView tprice ;
    AutoCompleteTextView texp ;
    AutoCompleteTextView a1;
    TextView display ;
    Button addbtn,view_data;
    ArrayList<String> stlist = new ArrayList<>();
    public static String[] arr = new String[]{""};
    public   DBHelper DB = new DBHelper(getContext());


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_stock, container, false);
        tname = view.findViewById(R.id.tb_name);
        tcode = view.findViewById(R.id.tb_bcode);
        tqty = view.findViewById(R.id.tb_qty);
        tprice = view.findViewById(R.id.tab_price);
        texp = view.findViewById(R.id.tb_exp);
        addbtn =view.findViewById(R.id.addbtn);
        view_data = view.findViewById(R.id.view_data);

        DB = new DBHelper(getActivity());
        Cursor tab_name = DB.getstock();
        while(tab_name.moveToNext()){
            stlist.add(tab_name.getString(0)+"\n");
        }
        arr= stlist.toArray(new String[0]);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,arr);
        tname.setAdapter(arrayAdapter);
        String name= tname.getText().toString();

        tcode.setAdapter(arrayAdapter);
        tqty.setAdapter(arrayAdapter);
        tprice.setAdapter(arrayAdapter);
        texp.setAdapter(arrayAdapter);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name= tname.getText().toString();
                String code = tcode.getText().toString();
                String qty = tqty.getText().toString();
                String price = tprice.getText().toString();
                String exp = texp.getText().toString();
                DB = new DBHelper(getActivity());

                Boolean checkinsertdata = DB.addstock(name, code, qty, price, exp);
                if (checkinsertdata == true) {
                    Toast.makeText(getContext(),"Stock Added Succesfull",Toast.LENGTH_SHORT).show();
                } else

                    Toast.makeText(getContext(), "Stock Added UnSuccesfull", Toast.LENGTH_SHORT).show();
            }


        });

        view_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getstock();
                if(res.getCount()==0){
                    Toast.makeText(getActivity(), "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Name :"+res.getString(0)+"\n");
//                    buffer.append("Contact :"+res.getString(1)+"\n");
//                    buffer.append("Date of Birth :"+res.getString(2)+"\n\n");
                }
            }
        });


        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddStockViewModel.class);
        // TODO: Use the ViewModel
    }


    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    public DBHelper sned_data()
    {
        return  DB;
    }


}
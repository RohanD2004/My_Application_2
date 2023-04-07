package com.pqr.myapplication.ui.home;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.pqr.myapplication.DBHelper;
import com.pqr.myapplication.MainActivity;
import com.pqr.myapplication.R;
import com.pqr.myapplication.databinding.FragmentHomeBinding;
import com.pqr.myapplication.ui.Add.AddStockFragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ListView userlist;
    View view;
    ArrayList<String> thelist = new ArrayList<String>();
    SearchView sv ;
    ArrayAdapter<String> adapter;
    AddStockFragment ad1 = new AddStockFragment();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        view = inflater.inflate(R.layout.fragment_add_stock, container, false);


        sv =view.findViewById(R.id.searchview);
        userlist = view.findViewById(R.id.userlist);
//        userlist.setVisibility(View.INVISIBLE);
         DBHelper db1 = new DBHelper(getContext());
        Cursor res = db1.getstock();
        while(res.moveToNext()){
            thelist.add("Name :"+res.getString(6)+"\n");
        }
        ArrayAdapter listAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,thelist);
        userlist.setAdapter(listAdapter);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listAdapter.getFilter().filter(s);
                userlist.setVisibility(View.VISIBLE);
                return false;
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
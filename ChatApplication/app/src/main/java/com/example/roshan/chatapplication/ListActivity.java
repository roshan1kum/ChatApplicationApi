package com.example.roshan.chatapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.roshan.chatapplication.Adapter.UserAdapter;
import com.example.roshan.chatapplication.Model.Person;
import com.example.roshan.chatapplication.Service.ListService;
import com.example.roshan.chatapplication.Service.ServiceBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        SharedPreferences sharedPreferences=getSharedPreferences(getPackageName()+".my_file", Context.MODE_PRIVATE);
        String Token=sharedPreferences.getString("token","N/A");
//
        ListService listService= ServiceBuilder.buildService(ListService.class);
        Call<List<Person>> listRequest = listService.getPerson(Token);

        listRequest.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                List<Person> list=response.body();
                ArrayList<Person> arr=new ArrayList<Person>();
                arr.addAll(list);
                RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
                //rvContacts.setHasFixedSize(true);
                UserAdapter adapter = new UserAdapter(arr);
                rvContacts.setAdapter(adapter);
                rvContacts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rvContacts.addItemDecoration(new DividerItemDecoration(getApplicationContext(),LinearLayoutManager.VERTICAL));
                rvContacts.setAdapter(adapter);
                //adapter.setOnItemClickListener(new UserAdapter.O)
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Toast.makeText(ListActivity.this,"error",Toast.LENGTH_SHORT).show();

            }
        });

    }
}

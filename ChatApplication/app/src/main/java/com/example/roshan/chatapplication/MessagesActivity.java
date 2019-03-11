package com.example.roshan.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.roshan.chatapplication.Model.Messages;
import com.example.roshan.chatapplication.Service.ListService;
import com.example.roshan.chatapplication.Service.ServiceBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesActivity extends AppCompatActivity {

    String Token;
    String toUserID;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Button button =(Button) findViewById(R.id.sendMessage);

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName() + ".my_file", Context.MODE_PRIVATE);
         Token = sharedPreferences.getString("token", "N/A");
         toUserID=sharedPreferences.getString("id","N/A");

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
            getmessages();
//        ListService messageService = ServiceBuilder.buildService(ListService.class);
//        Call<ArrayList<Messages>> messageRequest = messageService.getMessage(Token, id);
//
//
//        messageRequest.enqueue(new Callback<ArrayList<Messages>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Messages>> call, Response<ArrayList<Messages>> response) {
//                List<Messages> list = response.body();
//                ListView listView = findViewById(R.id.view);
//                String[] arr = new String[list.size()];
//                //Toast.makeText(MessagesActivity.this,id,Toast.LENGTH_SHORT).show()
//                for (int i = 0; i < list.size(); i++) {
//                    arr[i] = list.get(i).getText();
//
//                    //Toast.makeText(MessagesActivity.this,a,Toast.LENGTH_SHORT).show();
//                }
//
//                ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arr);
//                listView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Messages>> call, Throwable t) {
//
//            }
//        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText=findViewById(R.id.sendText);
                Messages message=new Messages();
                message.setText(editText.getText().toString());
                message.setToUserId(id);
                ListService messageService= ServiceBuilder.buildService(ListService.class);
                Call<Messages> msg=messageService.createMessage(message,Token);
                msg.enqueue(new Callback<Messages>() {
                    @Override
                    public void onResponse(Call<Messages> call, Response<Messages> response) {
                        getmessages();
                        editText.setText("");
                    }

                    @Override
                    public void onFailure(Call<Messages> call, Throwable t) {
                        getmessages();
                        editText.setText("");
                    }
                });

            }
        });
    }

    void getmessages()
    {
        ListService messageService = ServiceBuilder.buildService(ListService.class);
        Call<ArrayList<Messages>> messageRequest = messageService.getMessage(Token, id);


        messageRequest.enqueue(new Callback<ArrayList<Messages>>() {
            @Override
            public void onResponse(Call<ArrayList<Messages>> call, Response<ArrayList<Messages>> response) {
                List<Messages> list = response.body();
                ListView listView = findViewById(R.id.view);
                String[] arr = new String[list.size()];
                //Toast.makeText(MessagesActivity.this,id,Toast.LENGTH_SHORT).show()
                for (int i = 0; i < list.size(); i++) {
                    arr[i] = list.get(i).getText();

                    //Toast.makeText(MessagesActivity.this,a,Toast.LENGTH_SHORT).show();
                }

                ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arr);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Messages>> call, Throwable t) {

            }
        });
    }
}



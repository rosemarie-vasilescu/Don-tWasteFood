package com.example.dontwastefood.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dontwastefood.Adapters.ChatAdapter;
import com.example.dontwastefood.Listeners.ChatBotApi;
import com.example.dontwastefood.Listeners.ChatBotResponseListener;
import com.example.dontwastefood.Models.Chat;
import com.example.dontwastefood.Models.Message;
import com.example.dontwastefood.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.common.StringUtils;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatBotActivity extends AppCompatActivity {
   private RecyclerView chat;
   private EditText userMessage;
   private FloatingActionButton sendMessage;
   private final String BOT_KEY = "bot";
   private final String USER_KEY = "user";
   private ArrayList<Chat> list;
   private ChatAdapter chatAdapter;
   private String ingredient;
   private ChatBotRequestManager manager;
   private TextView txtWelcomeMsg;
   private Button btnAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        chat = findViewById(R.id.idRVChats);
        userMessage = findViewById(R.id.idEdtMessage);
        sendMessage = findViewById(R.id.idFABSend);
        txtWelcomeMsg = findViewById(R.id.txtWelcomeMsg);

        list = new ArrayList<>();
        manager = new ChatBotRequestManager(this);
        ingredient="";
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_search);
        chatAdapter = new ChatAdapter(list,this);

        chat.setLayoutManager(new LinearLayoutManager(ChatBotActivity.this));



        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtWelcomeMsg.setVisibility(View.INVISIBLE);
                if(userMessage.getText().toString().isEmpty()){
                    Toast.makeText(ChatBotActivity.this, "Please enter your message", Toast.LENGTH_SHORT).show();
                    return;
                }

                manager.getResponse(chatBotResponseListener, userMessage.getText().toString());
                list.add(new Chat(userMessage.getText().toString(),USER_KEY));
                chat.setAdapter(chatAdapter);
                userMessage.setText("");
            }
        });
    }
    private final ChatBotResponseListener chatBotResponseListener = new ChatBotResponseListener() {

        @Override
        public void didFetch(Message response, String message) {

             list.add(new Chat(response.getCnt(),BOT_KEY));
            chat.setAdapter(chatAdapter);
             chatAdapter.notifyDataSetChanged();
             findIngredient(response.getCnt());
        }

        @Override
        public void didError(String message) {
            Log.i("chatbot",message);
        list.add(new Chat("Please revert your question",BOT_KEY));

        chatAdapter.notifyDataSetChanged();
        }
    };
    private void findIngredient(String response){

        if(response.contains("recipe with"))
        {
            String[] ingredients = response.split("recipe with");
            ingredient = ingredients[1];
            if(ingredients[1].contains("and")){
                ingredient = ingredients[1].replace("and",",");
                Toast.makeText(this, "ingredient" + ingredient, Toast.LENGTH_SHORT).show();

            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(ChatBotActivity.this, RecipeByIngredientsActivity.class)
                            .putExtra("ingredients",ingredient));
                }
            }, 3000);
        }

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(ChatBotActivity.this,MainActivity.class));
                finish();
                return true;
            case android.R.id.undo:
                startActivity(new Intent(ChatBotActivity.this, RecipeByIngredientsActivity.class)
                        .putExtra("ingredients",ingredient));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
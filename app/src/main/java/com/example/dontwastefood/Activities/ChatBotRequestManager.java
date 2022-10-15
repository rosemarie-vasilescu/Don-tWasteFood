package com.example.dontwastefood.Activities;

import android.content.Context;
import android.widget.Toast;

import com.example.dontwastefood.Listeners.ChatBotResponseListener;
import com.example.dontwastefood.Models.Message;
import com.example.dontwastefood.Models.RandomRecipeApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public class ChatBotRequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.brainshop.ai/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
public ChatBotRequestManager(Context context) {
        this.context = context;
    }

    public void getResponse(ChatBotResponseListener listener,String message){
        CallChatBot callChatBot = retrofit.create(CallChatBot.class);
        Call<Message> call = callChatBot.
         callChatBot("http://api.brainshop.ai/get?bid=166655&key=REOoJA1hQvwWZKMf&uid=[uid]&msg=" + message);
          call.enqueue(new Callback<Message>() {
             @Override
              public void onResponse(Call<Message> call, Response<Message> response) {
                 if(!response.isSuccessful()){
                   listener.didError(response.message());
                 return;
                }
               listener.didFetch(response.body(),response.message());
    }

    @Override
    public void onFailure(Call<Message> call, Throwable t) {
        listener.didError(t.getMessage());
    }
});
    }
    private interface CallChatBot{
        @GET
        Call<Message> callChatBot(
                @Url String url

        );
    }
}

package com.example.dontwastefood.Listeners;

import com.example.dontwastefood.Models.Message;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ChatBotApi {
    @GET
    Call<Message> getMessage(@Url String url);
}

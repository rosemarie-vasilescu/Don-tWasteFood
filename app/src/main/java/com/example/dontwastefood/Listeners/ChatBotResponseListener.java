package com.example.dontwastefood.Listeners;

import com.example.dontwastefood.Models.Message;


public interface ChatBotResponseListener {
    void didFetch(Message response, String message);
    void didError(String message);
}

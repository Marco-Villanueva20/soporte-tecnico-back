package com.cibertec.controller;

import com.cibertec.model.Chat;
import com.cibertec.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ChatWebSocketController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("chat/guardar")
    public ResponseEntity<?> enviarMensaje(Chat chat) {
        return  chatService.enviarMensaje(chat);
    }
}

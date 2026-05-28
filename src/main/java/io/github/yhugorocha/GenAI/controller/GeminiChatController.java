package io.github.yhugorocha.GenAI.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gemini-chat")
public class GeminiChatController {

    private final ChatClient chatClient;
    private final static String SYSTEM_PROMPT = "You are a helpful assistant that summarizes meeting notes.";

    public GeminiChatController(@Qualifier("geminiChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping("/summarize-meeting-notes")
    public String summarizeMeetingNotes(@RequestBody String meetingNotes) {

        return chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .user(u -> u.text("Here are the meeting notes: {meetingNotes} Please provide a " +
                        "concise summary of the key points and action items.")
                        .param("meetingNotes", meetingNotes))
                .call()
                .content();
    }
}

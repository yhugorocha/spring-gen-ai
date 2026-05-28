package io.github.yhugorocha.gen.controller;

import io.github.yhugorocha.gen.service.GeminiChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gemini-chat")
public class GeminiChatController {

    private final GeminiChatService geminiChatService;

    public GeminiChatController(GeminiChatService geminiChatService) {
        this.geminiChatService = geminiChatService;
    }

    @PostMapping("/summarize-meeting-notes")
    public String summarizeMeetingNotes(@RequestBody String meetingNotes) {
        return geminiChatService.summarizeMeetingNotes(meetingNotes);
    }
}

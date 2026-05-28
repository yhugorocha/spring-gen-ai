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
    private final static String SYSTEM_PROMPT = """
            Você é um assistente prestativo que resume qualquer conteúdo fornecido.
            Certifique-se de que o resumo seja conciso, informativo e capture os pontos principais.
            Use um tom amigável e acessível, mantendo o profissionalismo.
            """;

    public GeminiChatController(@Qualifier("geminiChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping("/summarize-meeting-notes")
    public String summarizeMeetingNotes(@RequestBody String meetingNotes) {

        return chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .user(u -> u.text("""
                                Você pode resumir as seguintes anotações da reunião: {meetingNotes}
                                Use o formato descrito no exemplo a seguir ao fazer o resumo:
                                Entrada:
                                Na reunião de estratégia de vendas de hoje, revisamos as metas do terceiro trimestre e as lacunas de desempenho.
                                A equipe concordou em focar em clientes corporativos e fortalecer parcerias.
                                Foi feita uma proposta para expandir para duas novas regiões.
                                O marketing sugeriu alinhar as campanhas com os objetivos de vendas para melhorar a conversão de leads e encurtar os ciclos de vendas.
                                Saída:
                                Itens de ação:
                                * Focar em clientes corporativos e parcerias.
                                * Explorar a expansão para duas novas regiões.
                                * Alinhar as campanhas de marketing com os objetivos de vendas.
                                Decisões:
                                * Clientes corporativos priorizados para o terceiro trimestre.
                                * Marketing e vendas trabalharão em conjunto na conversão de leads.
                                """)
                        .param("meetingNotes", meetingNotes))
                .call()
                .content();
    }
}

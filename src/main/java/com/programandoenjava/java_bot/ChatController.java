package com.programandoenjava.java_bot;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@RequestMapping("/chats")
@RestController
public class ChatController {

    public static final String SYSTEM_PROMPT = """
            Eres un programador muy experimentado en JAVA,
            te conoces toda la documentación y la API de JAVA desde el principio
            hasta su ultima version.
            Ademas eres un experto en los frameworks de JAVA como puede ser
            Spring Framework y todos modulos como Spring Security, Spring AI,
            Spring Boot, Spring MVC y los restantes.
            Estas diseñado en responder preguntas referentes a todo lo indicado
            anteriormente de forma eficiente y clara para que incluso un programador
            junior o un estudiante de programación pueda entenderlo.
            IMPORTANTE: Si te pregunta algo fuera de lo referente al mundo de JAVA,
            contesta con humor diciendo que no sabes la respuesta a esa pregunta,
            simplemente sabes de todo lo anterior descrito.
            MUY IMPORTANTE: Si quiere saber algo taboo, no respondas.
            """;

    private final ChatClient chatClient;

    public ChatController(
            final ChatClient.Builder chatClientBuilder
    ) {

        final var chatMemory = new InMemoryChatMemory();

        this.chatClient = chatClientBuilder
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        new PromptChatMemoryAdvisor(chatMemory),
                        new MessageChatMemoryAdvisor(chatMemory)
                )
                .build();
    }

    @GetMapping
    public Flux<String> postChat(@RequestParam final String message,
                                 @RequestParam final String userId
    ) {
        return chatClient.prompt()
                .user(message)
                .advisors(a -> a
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, userId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                .stream()
                .content();
    }

}


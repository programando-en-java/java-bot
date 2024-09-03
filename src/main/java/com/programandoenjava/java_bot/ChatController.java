package com.programandoenjava.java_bot;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public ChatController(final ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping
    public String postChat(@RequestBody final ChatRequest chatRequest) {
        return this.chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .user(chatRequest.message())
                .call()
                .content();
    }
}

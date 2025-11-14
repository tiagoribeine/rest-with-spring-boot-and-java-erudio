package github.com.tiagoribeine.controllers;
import github.com.tiagoribeine.model.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController // Faz com que seja reconhecido como um Controller Rest
public class GreetingController {

    private static final String template = "Hello, %s!"; // %s! será substituido pelo valor enviado como parâmetro através da requisição HTTP
    private final AtomicLong counter = new AtomicLong();

    // http://localhost:8080/greeting?name=Tiago - será passado como query param
    @RequestMapping("/greeting") //Faz com que seja reconhecido como um metodo exposto via HTTP - ("/greeting") -> Nome do Endpoint
    public Greeting greeting(
            @RequestParam(value = "name", defaultValue = "Word") // Para ler esse parâmetro precisa-se adicionar essa annotation
            String name){
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
        //counter.incrementAndGet() Irá mockar o Id, a cada requisição ele irá incrementar e retornar um valor maior

    }

}

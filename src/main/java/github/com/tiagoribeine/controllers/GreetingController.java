package github.com.tiagoribeine.controllers;
import github.com.tiagoribeine.model.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController // Faz com que seja reconhecido como um Controller Rest
public class GreetingController {

    private static final String template = "Hello, %s!"; //  Template Mensagem: %s é placeholder que será substituído pelo valor enviado como parâmetro através da requisição HTTP
    private final AtomicLong counter = new AtomicLong(); // Gera IDs únicos thread-safe

    // http://localhost:8080/greeting?name=Tiago - será passado como query param
    @RequestMapping("/greeting")
    /* @RequestMapping: Faz com que seja reconhecido como um metodo exposto via HTTP - podendo ser chamado por URL ("/greeting").
    Torna metodo acessível pela internet e nao somente por classes Java */
    public Greeting greeting(
            @RequestParam(value = "name", defaultValue = "Word") // Captura parâmetro da URL (?name=valor)
                    /*
                    // 'defaultValue = "World"' = valor padrão se não enviarem o parâmetro
                    // 'value = "name"' = nome do parâmetro na URL
                     */
            String name){
        return new Greeting(counter.incrementAndGet(), //Irá mockar o Id, a cada requisição ele irá incrementar e retornar um valor maior
                String.format(template, name)); //Monta mensagem: "Hello, {nome}!"
    }

}

package github.com.tiagoribeine.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //Sinaliza ao Spring que essa classe tem configurações e pode ter declaração de Beans(precisa carregar as configurações da classe ao carregar o contexto)
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        //Via HEADER PARAM http://localhost:8080/api/person/v1/2?mediaType=xml - Adiciona mais um parâmetro, url fica muito grande e complexa de gerenciar
        configurer.favorParameter(false)
                .ignoreAcceptHeader(false) //
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("yaml", MediaType.APPLICATION_YAML);
        }
        //Via QUERY PARAM http://localhost:8080/api/person/v1/2?mediaType=xml - Adiciona mais um parâmetro, url fica muito grande e complexa de gerenciar
        /*
        configurer.favorParameter(true)
                .parameterName("mediaType")
                .ignoreAcceptHeader(true) //
                    .useRegisteredExtensionsOnly(false)
                    .defaultContentType(MediaType.APPLICATION_JSON)
                        .mediaType("json", MediaType.APPLICATION_JSON)
                        .mediaType("xml", MediaType.APPLICATION_XML);
         */


}

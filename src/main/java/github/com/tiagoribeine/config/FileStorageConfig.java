package github.com.tiagoribeine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file")     
public class FileStorageConfig {

    //Propósito: Configuração centralizada para armazenamento de arquivos.
    //Essa classe atua como um "Beann de configuração" que mapeia propriedades de arquivo application.properties para um objeto Java reutilizável
    
    private String upload_dir;

    public FileStorageConfig() {
    }

    public String getUpload_dir() {
        return upload_dir;
    }

    public void setUpload_dir(String upload_dir) {
        this.upload_dir = upload_dir;
    }
}

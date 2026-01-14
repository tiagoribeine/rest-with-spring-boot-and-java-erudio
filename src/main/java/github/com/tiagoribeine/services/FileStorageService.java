package github.com.tiagoribeine.services;

import github.com.tiagoribeine.config.FileStorageConfig;
import github.com.tiagoribeine.exception.FileNotFoundException;
import github.com.tiagoribeine.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    private final Path fileStorageLocation; //Variável onde será armazenado o arquivo

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        Path path = Paths.get(fileStorageConfig.getUpload_dir()) //Caminho de diretório onde se salva o arquivo e normalizando caracteres inválidos
                .toAbsolutePath().normalize();

        this.fileStorageLocation = path;

        //Tentando criar um arquivo nesse diretório:
        try{
            logger.info("Creating Directories");
            Files.createDirectories(this.fileStorageLocation); //Cria o diretório de armazenamento caso ele nao exista
        } catch(Exception e){
            logger.error("Could not create the directory where files will be stored");
            throw new FileStorageException("Could not create the directory where files will be stored", e); //Recebe a mensagem e a exceção
        }
    }

    //Metodo para salvar o arquivo em disco:
    public String storeFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename()); //Determinando o nome do arquivo -- Remove caracteres não aceitos

        try{
           //Tentando salvar o arquivo
            if(fileName.contains("..")){
                logger.error("Sorry! Filename Contains a Invalid path Sequence" + fileName);
                throw new FileStorageException("Sorry! Filename Contains a Invalid path Sequence" + fileName); // Nome inválido
            }

            logger.info("Saving file in Disk");

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            //Copia o arquivo para o disco convertendo para InputStream, destino, se ja existir arquivo com o mesmo nome será substituido
            return fileName; //Retorna o nome do arquivo.

        } catch(Exception e){
            logger.error("Could not Store file " + fileName + ". Please try Again!");
           throw new FileStorageException("Could not Store file " + fileName + ". Please try Again!", e);
        }
    }

    //Download de arquivos
    public Resource loadFileAsResource(String fileName){
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize(); //Pega o caminho até o arquivo
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else{
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (Exception e){
            logger.error("File not found " + fileName);
            throw new FileNotFoundException("File not found " + fileName, e);
        }
    }
}

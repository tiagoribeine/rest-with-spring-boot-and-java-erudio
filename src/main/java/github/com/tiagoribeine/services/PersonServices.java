package github.com.tiagoribeine.services;

import github.com.tiagoribeine.controllers.PersonController;
import github.com.tiagoribeine.data.dto.PersonDTO;
import github.com.tiagoribeine.exception.RequiredObjectIsNullException;
import github.com.tiagoribeine.exception.ResourceNotFoundException;
import github.com.tiagoribeine.model.Person;
import github.com.tiagoribeine.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

import static github.com.tiagoribeine.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// Aqui teremos Operações para cadastrar uma pessoa

@Service //Marca a classe como Serviço gerenciado pelo Spring. Faz com que fique disponível e seja injetado onde necessário
public class PersonServices {

    private final AtomicLong counter = new AtomicLong(); // Gera IDs únicos thread-safe
    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName()); //Cria um logger(gravador) específico para a classe PersonServices;


    @Autowired
    PersonRepository repository; //Spring insere o repository no Service, permitindo conexão com o banco de dados

    @Autowired
    PagedResourcesAssembler<PersonDTO> assembler;

    // Busca TODAS as pessoas
    public PagedModel<EntityModel<PersonDTO>> findAll(Pageable pageable){
        logger.info("Finding all people!"); //Registra uma operação normal do sistema a nivel informativo. Exibe a info no console/terminal

        var people = repository.findAll(pageable);

        var peopleWithLinks = people.map(person -> {
           //Pegando cada person e convertendo para DTO
            var dto = parseObject(person, PersonDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        Link findAllLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PersonController.class)
                        .findAll(pageable.getPageNumber(),
                        pageable.getPageSize(),
                        String.valueOf(pageable.getSort()))).withSelfRel();
        return assembler.toModel(peopleWithLinks, findAllLink);
    }

    public PagedModel<EntityModel<PersonDTO>> findByName(String firstName, Pageable pageable){
        logger.info("Finding people by Name!"); //Registra uma operação normal do sistema a nivel informativo. Exibe a info no console/terminal

        var people = repository.findPeopleByName(firstName, pageable);

        var peopleWithLinks = people.map(person -> {
           //Pegando cada person e convertendo para DTO
            var dto = parseObject(person, PersonDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        Link findAllLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PersonController.class)
                        .findAll(pageable.getPageNumber(),
                        pageable.getPageSize(),
                        String.valueOf(pageable.getSort()))).withSelfRel();
        return assembler.toModel(peopleWithLinks,
                findAllLink);
    }

    //Busca uma pessoa por ID
    public PersonDTO findById(Long id) {
        logger.info("Finding one Person");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Alterar também no controller, inicialmente estava String

        //Implementando HATEOAS - findById
        var dto = parseObject(entity, PersonDTO.class); //Utilizando o metodo do ObjectMapper para converter em uma DTO
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO create(PersonDTO person){

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one Person!"); //Registra uma operação normal do sistema a nivel informativo.
        var entity = parseObject(person, Person.class);

        // Implementando o HATEOAS - create
        var dto =  parseObject(repository.save(entity), PersonDTO.class); //Salva e ja retorna ao controller
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO update(PersonDTO person){

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating One Person!"); //Registra uma operação normal do sistema a nivel informativo.
        Person entity = repository.findById(person.getId()) //Recuperamos a entidade pelo id fornecido pelo client. São dados que ja estão no banco
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Alterar também no controller, inicialmente estava String

        //Usando setters para alterar os parâmetros de entity
        entity.setFirstName(person.getFirstName()); //person foi fornecida pelo Client
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        // Implementando o HATEOAS - update
        var dto =  parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Transactional
    public PersonDTO disablePerson(Long id) {

        logger.info("Disabling one person!"); //Registra uma operação normal do sistema a nivel informativo.

        repository.findById(id) //Recuperamos a entidade pelo id fornecido pelo client. São dados que ja estão no banco
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Alterar também no controller, inicialmente estava String
        repository.disablePerson(id);

        var entity = repository.findById(id).get();
        var dto =  parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {

        logger.info("Deleting one person!"); //Registra uma operação normal do sistema a nivel informativo.
        Person entity = repository.findById(id) //Recuperamos a entidade pelo id fornecido pelo client. São dados que ja estão no banco
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Alterar também no controller, inicialmente estava String
        repository.delete(entity);
    }

    private void addHateoasLinks(PersonDTO dto) {

        // find by Id
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        // find All
        dto.add(linkTo(methodOn(PersonController.class).findAll(1, 12,"asc" )).withRel("findAll").withType("GET"));
        // Create
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        // UPDATE
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        // PATCH
        dto.add(linkTo(methodOn(PersonController.class).disablePerson(dto.getId())).withRel("update").withType("PATCH"));
        // DELETE
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
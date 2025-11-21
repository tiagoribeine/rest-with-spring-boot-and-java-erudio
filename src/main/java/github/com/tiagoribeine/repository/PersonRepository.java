package github.com.tiagoribeine.repository;

import github.com.tiagoribeine.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    /* <Entidade que esse repository gerencia, tipo do Id da Entidade>
     Só isso é o suficiente para executar um CRUD
     JpaRepository representa um "contrato" com metodos CRUD básicos*/

}

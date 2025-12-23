package github.com.tiagoribeine.repository;

import github.com.tiagoribeine.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Modifying
    @Query("UPDATE Person p SET p.enabled = false WHERE p.id =:id")
    void disablePerson(@Param("id") Long id);
    /* <Entidade que esse repository gerencia, tipo do Id da Entidade>
     Só isso é o suficiente para executar um CRUD
     JpaRepository representa um "contrato" com metodos CRUD básicos*/

}

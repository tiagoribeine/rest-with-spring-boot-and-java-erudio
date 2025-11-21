package github.com.tiagoribeine.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

//Definindo os atributos de Person
@Entity //Marca como entidade JPA sendo gerenciada pelo Hibernate. Informa que a entidade representa uma tabela no banco de dados
@Table(name = "person") //Define nome da tabela no banco(opcional - usa "person" se omitir)
public class Person implements Serializable {
    /* Indica que a classe pode ser serializada(instância transformada em Bytes. Permite que:
    1. Seja salvo em um arquivo
    2. Seja enviado pela rede
    3. Salvo na sessão HTTP
    4. Persistir em banco: Alguns objetos permitem salvar objetos serializados. JPA/Hibernate podem usar serialização para campos complexos
    */

    private static final long serialVersionUID = 1L; //Número da versão para controle durante a serialização

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Como será gerado o Id
    private Long id; // Será fornecido pelo banco de dados

    @Column(name = "first_name", nullable = false, length = 80) //Nome da Coluna na tabela do DB, Não pode ser nulo, tamanho máximo
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 80) //Nome da Coluna na tabela do DB, Não pode ser nulo, tamanho máximo
    private String lastName;

    @Column(nullable = false, length = 100) //Nome não especificado, Spring entende que o nome é igual na coluna da tabela(db) e na entidade
    private String address;

    @Column(nullable = false, length = 6) //Nome não especificado, Spring entende que o nome é igual na coluna da tabela(db) e na entidade
    private String gender;

    public Person() {} //Construtor - Spring Data JPA e frameworks de serialização exigem construtor vazio

    //Getters e Setters - Spring, Jackson e JPA usam getters/setters
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    //Gerando o Equals and HashCode: Botão Direito >> Generate >> Equals and HashCode
    @Override
    public boolean equals(Object o) { //Compara se dois objetos são iguais baseado nos valores de seus atributos
        if (!(o instanceof Person person)) return false;
        return Objects.equals(getId(), person.getId()) && Objects.equals(getFirstName(), person.getFirstName()) && Objects.equals(getLastName(), person.getLastName()) && Objects.equals(getAddress(), person.getAddress()) && Objects.equals(getGender(), person.getGender());
    }

    @Override
    public int hashCode() { //Gera um código único baseado nos atributos
        return Objects.hash(getId(), getFirstName(), getLastName(), getAddress(), getGender());
    }
}

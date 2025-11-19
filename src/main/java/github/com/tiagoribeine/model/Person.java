package github.com.tiagoribeine.model;

import java.io.Serializable;
import java.util.Objects;

//Definindo os atributos de Person

public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstName;
    private String LastName;
    private String address;
    private String gender;

    public Person() {} //Construtor

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    //Gerando o Equals and HashCode: Botão Direito >> Generate >> Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person person)) return false;
        return Objects.equals(getId(), person.getId()) && Objects.equals(getFirstName(), person.getFirstName()) && Objects.equals(getLastName(), person.getLastName()) && Objects.equals(getAddress(), person.getAddress()) && Objects.equals(getGender(), person.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getAddress(), getGender());
    }
}

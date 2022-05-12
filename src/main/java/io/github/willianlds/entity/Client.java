package io.github.willianlds.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name",length = 100)
    @NotEmpty(message = "{field.name.required}")
    private String name;

    @Column(name = "cpf", length = 11)
    @NotEmpty(message = "{field.cpf.required}")
    @CPF(message = "{field.cpf.invalid}")
    private String cpf;

    @JsonIgnore
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Set<OrderSale> orders;

    public Client(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}

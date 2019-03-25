package com.cybercom.fruitstore.domain.FruitType;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class FruitTypeEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    @Column(unique = true)
    private String name;

    public FruitTypeEntity(@NotNull String name) {
        this.name = name;
    }
}

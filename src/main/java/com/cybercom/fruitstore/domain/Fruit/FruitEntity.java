package com.cybercom.fruitstore.domain.Fruit;

import com.cybercom.fruitstore.domain.FruitType.FruitTypeEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class FruitEntity {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(cascade = {CascadeType.ALL})
    private FruitTypeEntity type;
    @NotNull
    private String name;
    private Integer price;

    public FruitEntity(FruitTypeEntity type, @NotNull String name, Integer price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }

}
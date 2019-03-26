package com.cybercom.fruitstore.domain.Fruit;

import com.cybercom.fruitstore.domain.FruitType.FruitTypeEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
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
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private FruitTypeEntity type;
    @NotNull
    private String name;
    private Integer price;

    public FruitEntity(FruitTypeEntity type, @NotNull String name, Integer price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FruitEntity that = (FruitEntity) o;
        return id.equals(that.id) &&
                Objects.equals(type, that.type) &&
                name.equals(that.name) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
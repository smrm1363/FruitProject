package com.mohammadreza.domain.fruit;

import com.mohammadreza.domain.fruitType.FruitTypeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

/**
 * This is an entity for fruit type
 */
@Entity
@Data
@NoArgsConstructor
@ApiModel(description = "All details about the FruitEntity.")
public class FruitEntity {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The automatic generated FruitEntity ID")
    private Integer id;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ApiModelProperty(notes = "The FruitTypeEntity of the FruitEntity")
    private FruitTypeEntity type;
    @NotNull
    @ApiModelProperty(notes = "The name of the FruitEntity")
    private String name;
    @PositiveOrZero
    @ApiModelProperty(notes = "The price of the FruitEntity")
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
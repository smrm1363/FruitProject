package com.cybercom.fruitstore.domain.FruitType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@ApiModel(description = "All details about the FruitTypeEntity.")
public class FruitTypeEntity {
    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The automatic generated FruitTypeEntity ID")
    private Integer id;
    @NotNull
    @Column(unique = true)
    @ApiModelProperty(notes = "The unique name of the FruitTypeEntity")
    private String name;

    public FruitTypeEntity(@NotNull String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FruitTypeEntity that = (FruitTypeEntity) o;
        return id.equals(that.id) &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

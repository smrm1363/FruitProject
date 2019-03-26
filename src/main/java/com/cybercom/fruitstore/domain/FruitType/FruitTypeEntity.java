package com.cybercom.fruitstore.domain.FruitType;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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

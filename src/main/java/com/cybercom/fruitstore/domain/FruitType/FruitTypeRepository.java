package com.cybercom.fruitstore.domain.FruitType;

import com.cybercom.fruitstore.domain.Fruit.FruitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FruitTypeRepository extends JpaRepository<FruitTypeEntity,Integer> {

}
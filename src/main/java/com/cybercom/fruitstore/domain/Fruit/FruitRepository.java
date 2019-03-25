package com.cybercom.fruitstore.domain.Fruit;

import com.cybercom.fruitstore.domain.Fruit.FruitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FruitRepository extends JpaRepository<FruitEntity,Integer> {

}
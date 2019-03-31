package com.cybercom.fruitstore.domain.FruitType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FruitTypeRepository extends JpaRepository<FruitTypeEntity,Integer> {
     List<FruitTypeEntity> findByName(String name);
}
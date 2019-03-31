package com.cybercom.fruitstore.domain.Fruit;

import com.cybercom.fruitstore.common.ApplicationException;
import com.cybercom.fruitstore.domain.FruitType.FruitTypeEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class FruitServiceIntegrationTest {

    @Autowired
    FruitService fruitService;
    @Test
    @Transactional
    public void saveOrUpdate() throws ApplicationException {
        FruitEntity baseFruitEntity = new FruitEntity(new FruitTypeEntity("Citrus"),"Orange",120);
        FruitEntity resultFruitEntity = fruitService.saveOrUpdate(baseFruitEntity);
        /**
         * Test insert FruitEntity
         */
        assertNotNull(resultFruitEntity.getId());
        FruitEntity foundFruitEntity1 = fruitService.find(baseFruitEntity.getId());
        assertEquals(foundFruitEntity1.getId(), baseFruitEntity.getId());
        /**
         * Test having single FruitTypeEntity when we have the same name
         */
        FruitEntity baseFruitEntity2 = fruitService.saveOrUpdate(new FruitEntity(new FruitTypeEntity("Citrus"),"Lemon",100));
        FruitEntity resultFruitEntity2 = fruitService.find(baseFruitEntity2.getId());
        assertEquals(resultFruitEntity2.getType().getId(), resultFruitEntity.getType().getId());
    }

    @Test
    public void find() throws ApplicationException {
        FruitEntity baseFruitEntity = new FruitEntity(new FruitTypeEntity("Citrus"),"Orange",120);
        FruitEntity resultFruitEntity = fruitService.saveOrUpdate(baseFruitEntity);
        FruitEntity foundFruitEntity = fruitService.find(resultFruitEntity.getId());
        assertEquals(foundFruitEntity,resultFruitEntity);
    }

    @Test
    @Transactional
    public void findAll() {
        FruitEntity baseFruitEntity = new FruitEntity(new FruitTypeEntity("Citrus"),"Orange",120);
        fruitService.saveOrUpdate(baseFruitEntity);
        FruitEntity baseFruitEntity2 = new FruitEntity(new FruitTypeEntity("Citrus"),"Lemon",120);
        fruitService.saveOrUpdate(baseFruitEntity2);
        List<FruitEntity> result = fruitService.findAll();
        assertTrue(result.stream().anyMatch(fruitEntity -> fruitEntity.getName().equals("Orange")));
        assertTrue(result.stream().anyMatch(fruitEntity -> fruitEntity.getName().equals("Lemon")));
    }
}
package com.cybercom.fruitstore.domain.Fruit;

import com.cybercom.fruitstore.common.ApplicationException;
import com.cybercom.fruitstore.domain.FruitType.FruitTypeEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class FruitServiceIntegrationTest {

    @Autowired
    FruitService fruitService;
    @Test
    @Transactional
    public void saveOrUpdate() throws ApplicationException {
        FruitEntity fruitEntity = new FruitEntity(new FruitTypeEntity("Banana"),"BananaIrani",12000);
        fruitEntity = fruitService.saveOrUpdate(fruitEntity);
        assertTrue(fruitEntity.getId()!=null);
        FruitEntity fruitEntity1 = fruitService.find(fruitEntity.getId());
        assertTrue(fruitEntity1.getId()==fruitEntity.getId());
        FruitEntity fruitEntity2 = fruitService.saveOrUpdate(new FruitEntity(new FruitTypeEntity("Banana"),"BananaIrani2",10000));
        FruitEntity fruitEntity3 = fruitService.find(fruitEntity2.getId());
        assertTrue(fruitEntity3.getType().getId()==fruitEntity1.getType().getId());
    }

    @Test
    public void find() {
    }

    @Test
    public void findAll() {
    }
}
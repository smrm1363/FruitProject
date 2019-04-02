package com.mohammadreza.domain.fruitType;

import com.mohammadreza.common.ApplicationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FruitTypeServiceTest {
    @Mock
    FruitTypeRepository fruitTypeRepositoryMock;
    @Mock
    Environment envMock;
    private FruitTypeService fruitTypeService;
    @Before
    public void setUp() {
        fruitTypeService = new FruitTypeService(fruitTypeRepositoryMock,envMock);
    }

    @Test
    public void saveOrUpdate() {
        FruitTypeEntity fruitTypeEntity = new FruitTypeEntity("Citrus");
        fruitTypeEntity.setId(1);
        /**
         * Update situation
         */
        when(fruitTypeRepositoryMock.findById(any())).thenReturn(Optional.of(fruitTypeEntity));
        when(fruitTypeRepositoryMock.save(any())).thenReturn(fruitTypeEntity);
        assertEquals(fruitTypeService.saveOrUpdate(fruitTypeEntity), fruitTypeEntity);
        /**
         * Insert situation
         */
        when(fruitTypeRepositoryMock.findById(any())).thenReturn(Optional.empty());
        when(fruitTypeRepositoryMock.save(any())).thenReturn(fruitTypeEntity);
        assertEquals(fruitTypeService.saveOrUpdate(fruitTypeEntity), fruitTypeEntity);
    }

    @Test
    public void find() throws ApplicationException {
        FruitTypeEntity fruitTypeEntity = new FruitTypeEntity("Citrus");
        fruitTypeEntity.setId(1);
        when(fruitTypeRepositoryMock.findById(any())).thenReturn(Optional.of(fruitTypeEntity));
        assertEquals(fruitTypeService.find(1), fruitTypeEntity);
    }

    @Test(expected =ApplicationException.class)
    public void findExceptionExpect() throws ApplicationException {

        when(fruitTypeRepositoryMock.findById(any())).thenReturn(Optional.empty());
        fruitTypeService.find(1);
    }
    @Test
    public void findAll() {
        when(fruitTypeRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        assertNotNull(fruitTypeService.findAll());
    }

    @Test
    public void findByName() {
        FruitTypeEntity fruitTypeEntity = new FruitTypeEntity("Citrus");
        fruitTypeEntity.setId(1);
        List<FruitTypeEntity> fruitTypeEntityList = new ArrayList<>();
        fruitTypeEntityList.add(fruitTypeEntity);
        when(fruitTypeRepositoryMock.findByName(any())).thenReturn(fruitTypeEntityList);
        assertEquals(fruitTypeService.findByName("name").get(), fruitTypeEntity);
    }

    @Test
    public void delete() throws ApplicationException {
        doNothing().when(fruitTypeRepositoryMock).deleteById(any());
        fruitTypeService.delete(1);
    }
    @Test(expected = ApplicationException.class)
    public void deleteExceptionExpect() throws ApplicationException {

        doThrow(EmptyResultDataAccessException.class).when(fruitTypeRepositoryMock).deleteById(any());
        fruitTypeService.delete(1);
    }
}
package com.mohammadreza.domain.fruit;

import com.mohammadreza.common.ApplicationException;
import com.mohammadreza.domain.fruitType.FruitTypeEntity;
import com.mohammadreza.domain.fruitType.FruitTypeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FruitServiceTest {
    @Mock
    FruitRepository fruitRepositoryMock;
    @Mock
    Environment envMock;
    @Mock
    FruitTypeService fruitTypeServiceMock;
    private FruitService fruitService;
    @Before
    public void setupMock() {
        fruitService = new FruitService(fruitRepositoryMock,fruitTypeServiceMock,envMock);
    }
    @Test
    public void saveOrUpdate() {

        FruitTypeEntity fruitTypeEntity= new FruitTypeEntity("Citrus");
        fruitTypeEntity.setId(1);
        FruitEntity fruitEntity = new FruitEntity(fruitTypeEntity,"Orange",10);
        fruitEntity.setId(2);
        /**
         * Update situation
         */
        doReturn(Optional.of(fruitEntity))
                    .when(fruitRepositoryMock).findById(any());
        doReturn(Optional.empty())
                    .when(fruitTypeServiceMock).findByName(any());
        when(fruitRepositoryMock.save(any())).thenReturn(fruitEntity);
        assertEquals(fruitService.saveOrUpdate(fruitEntity), fruitEntity);
        /**
         * Insert situation
         */
        doReturn(Optional.empty())
                .when(fruitRepositoryMock).findById(any());
        doReturn(Optional.empty())
                .when(fruitTypeServiceMock).findByName(any());
        when(fruitRepositoryMock.save(any())).thenReturn(fruitEntity);
        assertEquals(fruitService.saveOrUpdate(fruitEntity), fruitEntity);
          }

    @Test
    public void find() throws ApplicationException {

        FruitTypeEntity fruitTypeEntity= new FruitTypeEntity("Citrus");
        fruitTypeEntity.setId(1);
        FruitEntity fruitEntity = new FruitEntity(fruitTypeEntity,"Orange",10);
        fruitEntity.setId(2);
        when(fruitRepositoryMock.findById(any())).thenReturn(Optional.of(fruitEntity));
        FruitEntity result = fruitService.find(1);
        assertEquals(result, fruitEntity);
    }
    @Test(expected =ApplicationException.class)
    public void findExceptionExpect() throws ApplicationException {

        when(fruitRepositoryMock.findById(any())).thenReturn(Optional.empty());
        fruitService.find(1);
    }
    @Test
    public void findAll() {

        when(fruitRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        assertNotNull(fruitService.findAll());
    }
    @Test
    public void delete() throws ApplicationException {

        doNothing().when(fruitRepositoryMock).deleteById(any());
        fruitService.delete(1);
    }
    @Test(expected = ApplicationException.class)
    public void deleteExceptionExpect() throws ApplicationException {

        doThrow(EmptyResultDataAccessException.class).when(fruitRepositoryMock).deleteById(any());
        fruitService.delete(1);
    }
    @Test(expected = ApplicationException.class)
    public void updateObserver() throws IOException, ApplicationException {
        FruitService fruitServiceSpy = Mockito.spy(fruitService);
        fruitServiceSpy.updateObserver("");
    }
    @Test
    public void updateObserverExceptionExpect() throws IOException, ApplicationException {
        FruitService fruitServiceSpy = Mockito.spy(fruitService);
        doReturn(null).when(fruitServiceSpy).saveOrUpdate(any());
        fruitServiceSpy.updateObserver("{\n" +
                "  \"name\": \"string\",\n" +
                "  \"price\": 0,\n" +
                "  \"type\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"string\"\n" +
                "  }\n" +
                "}");
    }
}
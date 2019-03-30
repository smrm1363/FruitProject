package com.cybercom.fruitstore.domain.Fruit;

import com.cybercom.fruitstore.common.ApplicationException;
import com.cybercom.fruitstore.common.MessageObserver;
import com.cybercom.fruitstore.domain.FruitType.FruitTypeEntity;
import com.cybercom.fruitstore.domain.FruitType.FruitTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FruitService implements MessageObserver {
    private final FruitRepository fruitRepository;
    private final FruitTypeService fruitTypeService;
    private final Environment env;
    @Autowired
    public FruitService(FruitRepository fruitRepository, FruitTypeService fruitTypeService, Environment env) {
        this.fruitRepository = fruitRepository;
        this.fruitTypeService = fruitTypeService;
        this.env = env;
    }
//    @Transactional
    public FruitEntity saveOrUpdate(FruitEntity fruitEntity)
    {
        FruitEntity foundFruitEntity;
        /**
         * Means update
         */
        if(fruitEntity.getId()!=null)
        {
            foundFruitEntity = fruitRepository.findById(fruitEntity.getId())
                    .map(fruitEntity1 ->
                    {
                        fruitEntity1.setName(fruitEntity.getName());
                        fruitEntity1.setPrice(fruitEntity.getPrice());
                        fruitEntity1.setType(fruitEntity.getType());
                        return fruitEntity1;
                    }).orElse(fruitEntity);

        }
        else
            foundFruitEntity = fruitEntity;
        if(foundFruitEntity.getType()!=null)
        {
            /**
             * Because the name of the FruitTypeEntity should be unique, I find the FruitTypeEntity if exists, otherwise, I create a new one.
             * It means I avoid data redundancy
             */
            FruitTypeEntity fruitTypeEntity =fruitTypeService.findByName(foundFruitEntity.getType().getName()).orElseGet(() -> foundFruitEntity.getType());
            foundFruitEntity.setType(fruitTypeEntity);
        }
        return fruitRepository.save(foundFruitEntity);

    }
    public FruitEntity find(Integer id) throws ApplicationException {
        return fruitRepository.findById(id)
                .orElseThrow(()->new ApplicationException(env.getProperty("domain.Fruit.FruitNotFound")));
    }
    public List<FruitEntity> findAll()
    {
        return fruitRepository.findAll();
    }
    public void delete(Integer id) throws ApplicationException {
        try {
            fruitRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException exception)
        {
            exception.printStackTrace();
            throw new ApplicationException(env.getProperty("domain.Fruit.FruitNotFound"));
        }
    }

    @Override
    public void update(String message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        FruitEntity fruitEntity = mapper.readValue(message, FruitEntity.class);
        saveOrUpdate(fruitEntity);
    }
}

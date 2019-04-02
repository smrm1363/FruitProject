package com.mohammadreza.domain.fruit;

import com.mohammadreza.common.ApplicationException;
import com.mohammadreza.common.MessageObserver;
import com.mohammadreza.domain.fruitType.FruitTypeEntity;
import com.mohammadreza.domain.fruitType.FruitTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * This is our business logic for fruit entity
 */
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

    /**
     * This method do both Save and Update operations depends on the situation. If {@link FruitEntity} does not have ID it does update
     * It also insert or update fruit Type with Cascade
     * @param fruitEntity
     * @return with ID
     */
    public FruitEntity saveOrUpdate(@Valid FruitEntity fruitEntity) {
        FruitEntity foundFruitEntity;
        /**
         * Means update
         */
        if (fruitEntity.getId() != null) {
            foundFruitEntity = fruitRepository.findById(fruitEntity.getId())
                    .map(fruitEntity1 ->
                    {
                        fruitEntity1.setName(fruitEntity.getName());
                        fruitEntity1.setPrice(fruitEntity.getPrice());
                        fruitEntity1.setType(fruitEntity.getType());
                        return fruitEntity1;
                    }).orElse(fruitEntity);

        } else
            foundFruitEntity = fruitEntity;
        if (foundFruitEntity.getType() != null) {
            /**
             * Because the name of the FruitTypeEntity should be unique, I find the FruitTypeEntity if exists, otherwise, I create a new one.
             * It means I avoid data redundancy
             */
            FruitTypeEntity fruitTypeEntity = fruitTypeService.findByName(foundFruitEntity.getType().getName()).orElseGet(foundFruitEntity::getType);
            foundFruitEntity.setType(fruitTypeEntity);
        }
        return fruitRepository.save(foundFruitEntity);

    }

    /**
     * This method finds an {@link FruitEntity} by ID
     * @param id
     * @return
     * @throws ApplicationException if {@link FruitEntity} does not exist
     */
    public FruitEntity find(Integer id) throws ApplicationException {
        return fruitRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(env.getProperty("domain.Fruit.FruitNotFound")));
    }

    /**
     * This method find all saved {@link FruitEntity}
     * @return
     */
    public List<FruitEntity> findAll() {
        return fruitRepository.findAll();
    }

    /**
     * This method delete an {@link FruitEntity} with Id
     * @param id
     * @throws ApplicationException
     */
    public void delete(Integer id) throws ApplicationException {
        try {
            fruitRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            exception.printStackTrace();
            throw new ApplicationException(env.getProperty("domain.Fruit.FruitNotFound"));
        }
    }

    /**
     * This method is for Observer design pattern. It get message and call saveOrUpdate method after converting the message to an Object
     * @param message
     * @throws IOException
     * @throws ApplicationException
     */
    @Override
    public void updateObserver(String message) throws IOException, ApplicationException {
        ObjectMapper mapper = new ObjectMapper();
        FruitEntity fruitEntity;
        try {
            fruitEntity = mapper.readValue(message, FruitEntity.class);
        } catch (MismatchedInputException exception) {
            throw new ApplicationException(env.getProperty("domain.Fruit.FruitJsonIsNotTrue"));
        }
        saveOrUpdate(fruitEntity);
    }
}

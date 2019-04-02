package com.mohammadreza.domain.fruitType;

import com.mohammadreza.common.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This is the business logic of {@link FruitTypeEntity}
 */
@Service
public class FruitTypeService {
    private final FruitTypeRepository fruitTypeRepository;
    private final Environment env;

    @Autowired
    public FruitTypeService(FruitTypeRepository fruitTypeRepository, Environment env) {
        this.fruitTypeRepository = fruitTypeRepository;
        this.env = env;
    }

    /**
     * This method do both Save and Update operations depends on the situation. If {@link FruitTypeEntity} does not have ID it does update
     * @param fruitTypeEntity
     * @return
     */
    public FruitTypeEntity saveOrUpdate(FruitTypeEntity fruitTypeEntity) {
        FruitTypeEntity foundFruitTypeEntity;
        /**
         * Means update
         */
        if (fruitTypeEntity.getId() != null) {
            foundFruitTypeEntity = fruitTypeRepository.findById(fruitTypeEntity.getId())
                    .map(fruitTypeEntity1 ->
                    {
                        fruitTypeEntity1.setName(fruitTypeEntity.getName());
                        return fruitTypeEntity1;
                    }).orElse(fruitTypeEntity);
        } else
            foundFruitTypeEntity = fruitTypeEntity;
        return fruitTypeRepository.save(foundFruitTypeEntity);
    }

    /**
     * This method finds an {@link FruitTypeEntity} by ID
     * @param id
     * @return
     * @throws ApplicationException if there is not fruitType
     */
    public FruitTypeEntity find(Integer id) throws ApplicationException {
        return fruitTypeRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(env.getProperty("domain.FruitType.FruitTypeNotFound")));
    }

    /**
     * This method returns all saved FruitTypes
     * @return
     */
    public List<FruitTypeEntity> findAll() {
        return fruitTypeRepository.findAll();
    }

    /**
     * This method finds a {@link FruitTypeEntity} by it's name
     * @param name
     * @return
     */
    public Optional<FruitTypeEntity> findByName(String name) {
        List<FruitTypeEntity> fruitTypeEntities = fruitTypeRepository.findByName(name);
        return fruitTypeEntities.stream().findFirst();
    }

    /**
     * this method deletes a {@link FruitTypeEntity} with it's ID
     * @param id
     * @throws ApplicationException
     */
    public void delete(Integer id) throws ApplicationException {
        try {
            fruitTypeRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            exception.printStackTrace();
            throw new ApplicationException(env.getProperty("domain.FruitType.FruitTypeInUsed"));
        } catch (EmptyResultDataAccessException exception) {
            exception.printStackTrace();
            throw new ApplicationException(env.getProperty("domain.FruitType.FruitTypeNotFound"));
        }

    }
}

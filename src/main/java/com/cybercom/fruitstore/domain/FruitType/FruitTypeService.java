package com.cybercom.fruitstore.domain.FruitType;

import com.cybercom.fruitstore.common.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FruitTypeService {
    private final FruitTypeRepository fruitTypeRepository;
    private final Environment env;
    @Autowired
    public FruitTypeService(FruitTypeRepository fruitTypeRepository, Environment env) {
        this.fruitTypeRepository = fruitTypeRepository;
        this.env = env;
    }

    public FruitTypeEntity saveOrUpdate(FruitTypeEntity fruitTypeEntity)
    {
        FruitTypeEntity foundFruitTypeEntity;
        /**
         * Means update
         */
        if(fruitTypeEntity.getId()!=null)
        {
            foundFruitTypeEntity = fruitTypeRepository.findById(fruitTypeEntity.getId())
                    .map(fruitTypeEntity1 ->
                    {
                        fruitTypeEntity1.setName(fruitTypeEntity.getName());
                        return fruitTypeEntity1;
                    }).orElse(fruitTypeEntity);
        }
        else
            foundFruitTypeEntity = fruitTypeEntity;
        return fruitTypeRepository.save(foundFruitTypeEntity);
    }
    public FruitTypeEntity find(Integer id) throws ApplicationException {
        return fruitTypeRepository.findById(id)
                .orElseThrow(()->new ApplicationException(env.getProperty("domain.FruitType.FruitTypeNotFound")));
    }
    public List<FruitTypeEntity> findAll()
    {
        return fruitTypeRepository.findAll();
    }
    public Optional<FruitTypeEntity> findByName(String name)
    {
        List<FruitTypeEntity> fruitTypeEntities = fruitTypeRepository.findByName(name);
        return fruitTypeEntities.stream().findFirst();
    }

}

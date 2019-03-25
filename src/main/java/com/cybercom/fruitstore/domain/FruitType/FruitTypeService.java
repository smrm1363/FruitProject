package com.cybercom.fruitstore.domain.FruitType;

import com.cybercom.fruitstore.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

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
        return fruitTypeRepository.findById(fruitTypeEntity.getId())
                .map(foundFruitTypeEntity ->
                {
                    foundFruitTypeEntity.setName(fruitTypeEntity.getName());
                    return fruitTypeRepository.save(foundFruitTypeEntity);
                }).orElseGet(() ->
                        fruitTypeRepository.save(fruitTypeEntity));
    }
    public FruitTypeEntity find(Integer id) throws ApplicationException {
        return fruitTypeRepository.findById(id)
                .orElseThrow(()->new ApplicationException(env.getProperty("domain.FruitType.FruitTypeNotFound")));
    }
}

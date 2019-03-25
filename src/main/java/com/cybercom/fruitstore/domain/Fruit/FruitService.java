package com.cybercom.fruitstore.domain.Fruit;

import com.cybercom.fruitstore.ApplicationException;
import com.cybercom.fruitstore.domain.FruitType.FruitTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class FruitService {
    private final FruitRepository fruitRepository;
    private final FruitTypeService fruitTypeService;
    private final Environment env;
    @Autowired
    public FruitService(FruitRepository fruitRepository, FruitTypeService fruitTypeService, Environment env) {
        this.fruitRepository = fruitRepository;
        this.fruitTypeService = fruitTypeService;
        this.env = env;
    }
    public FruitEntity saveOrUpdate(FruitEntity fruitEntity)
    {
       return fruitRepository.findById(fruitEntity.getId()).map(foundFruitEntity ->
       {
           foundFruitEntity.setName(fruitEntity.getName());
           foundFruitEntity.setPrice(fruitEntity.getPrice());
           foundFruitEntity.setType(fruitEntity.getType());
           return fruitRepository.save(foundFruitEntity);
       }).orElseGet(() -> fruitRepository.save(fruitEntity));
    }
    public FruitEntity find(Integer id) throws ApplicationException {
        return fruitRepository.findById(id)
                .orElseThrow(()->new ApplicationException(env.getProperty("domain.Fruit.FruitNotFound")));
    }
}

package com.cybercom.fruitstore.domain.FruitType;

import com.cybercom.fruitstore.common.ApplicationException;
import com.cybercom.fruitstore.domain.Fruit.FruitEntity;
import com.cybercom.fruitstore.domain.Fruit.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import java.util.List;


@RestController
@RequestMapping(path="/fruitType")
public class FruitTypeRest extends HttpServlet {
private final FruitTypeService fruitTypeService;

@Autowired
    public FruitTypeRest(FruitTypeService fruitTypeService) {
    this.fruitTypeService = fruitTypeService;
    }

@GetMapping("/getFruitType/{id}")
public ResponseEntity<FruitTypeEntity> getFruitType(@PathVariable("id") String id) throws ApplicationException {
    return ResponseEntity.ok(fruitTypeService.find(Integer.valueOf(id)));
}
@GetMapping("/getFruitTypeList")
    public ResponseEntity<List<FruitTypeEntity>> getFruitTypeList() throws ApplicationException {
        return ResponseEntity.ok(fruitTypeService.findAll());
    }
@PostMapping("/store/fruitType")
public ResponseEntity<FruitTypeEntity> storeFruitType(@RequestBody FruitTypeEntity fruitTypeEntity){
    return ResponseEntity.ok(fruitTypeService.saveOrUpdate(fruitTypeEntity));
}
@PutMapping(path="/update")
public void update(@RequestBody FruitTypeEntity fruitTypeEntity){
    fruitTypeService.saveOrUpdate(fruitTypeEntity);
}
@DeleteMapping(path = "/delete/{id}")
public void delete(@PathVariable("id") String id)
{fruitTypeService.delete(Integer.valueOf(id));}





}
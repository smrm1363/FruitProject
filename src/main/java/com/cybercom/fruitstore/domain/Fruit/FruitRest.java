package com.cybercom.fruitstore.domain.Fruit;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServlet;

import com.cybercom.fruitstore.common.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path="/fruitstore")
public class FruitRest extends HttpServlet {
private final FruitService fruitService;

@Autowired
    public FruitRest(FruitService fruitService) {
        this.fruitService = fruitService;
    }

@GetMapping("/getFruit/{id}")
public ResponseEntity<FruitEntity> getFruit(@PathVariable("id") String id) throws ApplicationException {
    return ResponseEntity.ok(fruitService.find(Integer.valueOf(id)));
}
@GetMapping("/getFruitList")
    public ResponseEntity<List<FruitEntity>> getFruitList() throws ApplicationException {
        return ResponseEntity.ok(fruitService.findAll());
    }
@PostMapping("/store/fruit")
public ResponseEntity<FruitEntity> storeFruit(@RequestBody FruitEntity fruitEntity){
    return ResponseEntity.ok(fruitService.saveOrUpdate(fruitEntity));
}
@PutMapping(path="/update")
public void update(@RequestBody FruitEntity fruitEntity){
    fruitService.saveOrUpdate(fruitEntity);
}
@DeleteMapping(path = "/delete/{id}")
public void delete(@PathVariable("id") String id)
{fruitService.delete(Integer.valueOf(id));}





}
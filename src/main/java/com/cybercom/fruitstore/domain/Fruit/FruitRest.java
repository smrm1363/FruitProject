package com.cybercom.fruitstore.domain.Fruit;

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

    //  public static String JSON="application/json";
//
//	private static final long serialVersionUID = 4279154482819675728L;
@GetMapping("/getFruit/{id}")
public ResponseEntity<FruitEntity> getFruit(@PathVariable("id") String id) throws ApplicationException {
    return ResponseEntity.ok(fruitService.find(Integer.valueOf(id)));
}

@PostMapping("/store/fruit")
public ResponseEntity<FruitEntity> storeFruit(@RequestBody FruitEntity fruitEntity){
    return ResponseEntity.ok(fruitService.saveOrUpdate(fruitEntity));
}
@RequestMapping(path="/update")
public void update(@RequestHeader FruitEntity f){
db.save(f);
}





}
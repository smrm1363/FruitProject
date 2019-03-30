package com.cybercom.fruitstore.domain.FruitType;

import com.cybercom.fruitstore.common.ApplicationException;
import com.cybercom.fruitstore.domain.Fruit.FruitEntity;
import com.cybercom.fruitstore.domain.Fruit.FruitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(path="/fruitType")
@Api(value = "Fruit Type API", description = "Simple CRUD operations for a fruit type")
public class FruitTypeRest extends HttpServlet {
private final FruitTypeService fruitTypeService;

@Autowired
    public FruitTypeRest(FruitTypeService fruitTypeService) {
    this.fruitTypeService = fruitTypeService;
    }

    @ApiOperation(value = "Finds one available FruitTypeEntity by it's ID", response = FruitTypeEntity.class)
    @GetMapping("/getFruitType/{id}")
public ResponseEntity<FruitTypeEntity> getFruitType(@ApiParam(value = "FruitType id from which Fruit object will retrieve", required = true)@PathVariable("id") String id) throws ApplicationException {
    return ResponseEntity.ok(fruitTypeService.find(Integer.valueOf(id)));
}
@ApiOperation(value = "Finds one available FruitTypeEntity by it's ID", response = FruitEntity.class)
@GetMapping("/getFruitTypeList")
    public ResponseEntity<List<FruitTypeEntity>> getFruitTypeList() throws ApplicationException {
        return ResponseEntity.ok(fruitTypeService.findAll());
    }
    @ApiOperation(value = "Inserts a FruitTypeEntity.", response = FruitEntity.class)
    @PostMapping("/store/fruitType")
public ResponseEntity<FruitTypeEntity> storeFruitType(@ApiParam(value = "New FruitTypeEntity, it's Id field should be empty otherwise it operates like update", required = true)@RequestBody @Valid FruitTypeEntity fruitTypeEntity){
    return ResponseEntity.ok(fruitTypeService.saveOrUpdate(fruitTypeEntity));
}
    @ApiOperation(value = "Updates a FruitTypeEntity.")
@PutMapping(path="/update")
public void update(@ApiParam(value = "Existed FruitTypeEntity, it's Id field should not be empty otherwise it operates like insert", required = true)@RequestBody @Valid FruitTypeEntity fruitTypeEntity){
    fruitTypeService.saveOrUpdate(fruitTypeEntity);
}
    @ApiOperation(value = "Deletes a FruitTypeEntity by it's ID")
@DeleteMapping(path = "/delete/{id}")
public void delete(@ApiParam(value = "ID of existed FruitTypeEntity", required = true)@PathVariable("id") String id) throws ApplicationException {fruitTypeService.delete(Integer.valueOf(id));}





}
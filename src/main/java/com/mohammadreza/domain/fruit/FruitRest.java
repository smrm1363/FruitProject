package com.mohammadreza.domain.fruit;

import com.mohammadreza.common.ApplicationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.validation.Valid;
import java.util.List;

/**
 * This is a REST controller for fruit entity, it support CRUD operations
 */
@RestController
@RequestMapping(path = "/fruitStore")
@Api(value = "fruit Store API", description = "Simple CRUD operations for a fruit store")
public class FruitRest extends HttpServlet {
    private final FruitService fruitService;

    @Autowired
    public FruitRest(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @ApiOperation(value = "Finds one available fruit by it's ID", response = FruitEntity.class)
    @GetMapping("/getFruit/{id}")
    public ResponseEntity<FruitEntity> getFruit(@ApiParam(value = "fruit id from which fruit object will retrieve", required = true) @PathVariable("id") String id) throws ApplicationException {
        return ResponseEntity.ok(fruitService.find(Integer.valueOf(id)));
    }

    @ApiOperation(value = "Finds all available Fruits", response = List.class)
    @GetMapping("/getFruitList")
    public ResponseEntity<List<FruitEntity>> getFruitList() {
        return ResponseEntity.ok(fruitService.findAll());
    }

    @ApiOperation(value = "Inserts a fruit. It also inserts or update a FruitTypeEntity", response = FruitEntity.class)
    @PostMapping("/store/fruit")
    public ResponseEntity<FruitEntity> storeFruit(@ApiParam(value = "New FruitEntity, it's Id field should be empty otherwise it operates like update", required = true) @RequestBody @Valid FruitEntity fruitEntity) {
        return ResponseEntity.ok(fruitService.saveOrUpdate(fruitEntity));
    }

    @ApiOperation(value = "Updates a fruit. It also inserts or update a FruitTypeEntity")
    @PutMapping(path = "/update")
    public void update(@ApiParam(value = "Existed FruitEntity, it's Id field should not be empty otherwise it operates like insert", required = true) @RequestBody @Valid FruitEntity fruitEntity) {
        fruitService.saveOrUpdate(fruitEntity);
    }

    @ApiOperation(value = "Deletes a fruit by it's ID")
    @DeleteMapping(path = "/delete/{id}")
    public void delete(@ApiParam(value = "ID of existed FruitEntity", required = true) @PathVariable("id") String id) throws ApplicationException {
        fruitService.delete(Integer.valueOf(id));
    }


}
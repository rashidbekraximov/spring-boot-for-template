package com.example.demo.controller.references;

import com.example.demo.controller.AbstractController;
import com.example.demo.dto.references.FormDto;
import com.example.demo.dto.response.DataDto;
import com.example.demo.service.references.FormService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class FormController extends AbstractController<FormService> {

    public FormController(FormService service) {
        super(service);
    }

    @RequestMapping(value = PATH + "/form", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Boolean>> create(@Valid @RequestBody FormDto dto) {
        return service.create(dto);
    }

    @RequestMapping(value = PATH + "/form", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<FormDto>>> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = PATH + "/form/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<FormDto>> get(@PathVariable Integer id) {
        return service.get(id);
    }

    @RequestMapping(value = PATH + "/form/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> delete(@PathVariable Integer id) {
        return service.delete(id);
    }
}

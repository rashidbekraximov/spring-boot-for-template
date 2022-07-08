package com.example.demo.service.references;

import com.example.demo.dto.references.FormDto;
import com.example.demo.dto.response.AppErrorDto;
import com.example.demo.dto.response.DataDto;
import com.example.demo.entity.references.Form;
import com.example.demo.mappers.references.FormMapper;
import com.example.demo.repository.references.FormRepository;
import com.example.demo.service.AbstractFormService;
import com.example.demo.service.auth.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormService extends AbstractFormService implements AbstractService {

    private final FormRepository formRepository;
    private final FormMapper mapper;

    public ResponseEntity<DataDto<List<FormDto>>> getAll() {
        List<Form> formList = formRepository.findAll(Sort.by("id"));
        return new ResponseEntity<>(new DataDto<>(mapper.toDto(formList), (long) formList.size()), HttpStatus.OK);
    }

    public ResponseEntity<DataDto<FormDto>> get(Integer id) {
        Optional<Form> optionalForm = formRepository.findById(id);
        if (optionalForm.isEmpty())
            return findErrorById(getFormReferenceName(), id);

        return new ResponseEntity<>(new DataDto<>(mapper.toDto(optionalForm.get())), HttpStatus.OK);
    }

    public ResponseEntity<DataDto<Boolean>> delete(Integer id) {
        Optional<Form> optionalForm = formRepository.findById(id);
        if (optionalForm.isEmpty())
            return findErrorById(getFormReferenceName(), id);

        formRepository.delete(optionalForm.get());
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<DataDto<Boolean>> create(FormDto dto) {
        Optional<Form> optionalForm = formRepository.findById(dto.getParentFormId());

        Form createDto = mapper.fromCreateDto(dto);
        if (dto.getParentFormId() == null || dto.getParentFormId() == 0)
            createDto.setParentForm(null);
        else
            optionalForm.ifPresent(createDto::setParentForm);

//        if (optionalForm.isEmpty())
//            return new ResponseEntity<>(new DataDto<>(AppErrorDto
//                    .builder()
//                    .status(HttpStatus.NOT_FOUND)
//                    .message("Parent form not found  -> " + dto.getParentFormId())
//                    .build()
//            ), HttpStatus.NOT_FOUND);


        formRepository.save(createDto);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.CREATED);
    }
}

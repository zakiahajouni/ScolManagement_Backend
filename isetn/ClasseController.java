package org.isetn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classes")
public class ClasseController {

    @Autowired
    private ClasseRepository classeRepository;

    @GetMapping
    public ResponseEntity<List<Classe>> getAllClasses() {
        try {
            List<Classe> classes = classeRepository.findAll();
            return new ResponseEntity<>(classes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{classId}")
    public ResponseEntity<Classe> getClassById(@PathVariable Long classId) {
        try {
            Classe classe = classeRepository.findById(classId)
                    .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + classId));
            return new ResponseEntity<>(classe, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/depart/{depart}")
    public ResponseEntity<List<Classe>> getClassesByDepartment(@PathVariable String depart) {
        try {
            List<Classe> classes = classeRepository.findByDepart(depart);
            return new ResponseEntity<>(classes, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception details
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@PostMapping("/put")
    public ResponseEntity<Classe> createClass(@RequestBody Classe newClass) {
        try {
            Classe createdClass = classeRepository.save(newClass);
            return new ResponseEntity<>(createdClass, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception details
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}







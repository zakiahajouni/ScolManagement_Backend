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


    @GetMapping("/class/{className}")
    public ResponseEntity<Classe> getClassByName(@PathVariable String className) {
        try {
            Optional<Classe> classeOptional = classeRepository.findByNomClass(className);
            if (classeOptional.isPresent()) {
                Classe classe = classeOptional.get();
                return new ResponseEntity<>(classe, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
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

    @PutMapping("/updateClass/{id}")
    public ResponseEntity<Classe> updateStudent(
            @PathVariable Long id,
            @RequestBody Classe updatedStudent) {

        try {
            Optional<Classe> existingStudentOptional = classeRepository.findById(id);

            if (existingStudentOptional.isPresent()) {
                Classe existingStudent = existingStudentOptional.get();

                // Update the student properties
                existingStudent.setEtudiants(updatedStudent.getEtudiants());
                existingStudent.setDepart(updatedStudent.getDepart());
                existingStudent.setNomClass(updatedStudent.getNomClass());
                existingStudent.setMatieres(updatedStudent.getMatieres());
                existingStudent.setNbreEtud(updatedStudent.getNbreEtud());

                // Assuming that 'classe' property should not be updated, as it is related to a specific class

                Classe updatedEtudiant = classeRepository.save(existingStudent);
                return new ResponseEntity<>(updatedEtudiant, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteClass/{classId}")
    public ResponseEntity<String> deleteClass(@PathVariable Long classId) {
        try {
            Optional<Classe> classeOptional = classeRepository.findById(classId);
            if (classeOptional.isPresent()) {
                classeRepository.deleteById(classId);
                return new ResponseEntity<>("Class deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Class not found with id: " + classId, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception details
            return new ResponseEntity<>("Failed to delete class: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/put")
    public ResponseEntity<Classe> createClass(@RequestBody Classe newClass) {
        try {
            Classe createdClass = classeRepository.save(newClass);
            return new ResponseEntity<>(createdClass, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception details
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}







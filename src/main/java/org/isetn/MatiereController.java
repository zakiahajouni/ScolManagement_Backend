package org.isetn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classes/matieres")
public class MatiereController {
    @Autowired
    private MatiereRepository matiereRepository;

    @GetMapping("/{classId}")
    public ResponseEntity<List<Matiere>> getMatieresByClass(@PathVariable Long classId) {
        try {
            List<Matiere> matieres = matiereRepository.findByClasses_CodClass(classId);
            return new ResponseEntity<>(matieres, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/addMatiere", method = RequestMethod.POST)
    public Matiere addMatiere(@RequestBody Matiere matiere) {
        try {

            Matiere savedMatiere = matiereRepository.save(matiere);
            return savedMatiere;
        } catch (Exception e) {
            // Handle exceptions appropriately, e.g., log the error
            return null; // You might want to return a ResponseEntity with an appropriate HTTP status code
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Matiere>> getAllMatieres() {
        try {
            List<Matiere> matieres = matiereRepository.findAll();
            return new ResponseEntity<>(matieres, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateMatiere/{id}")
    public ResponseEntity<Matiere> updateMatiere(
            @PathVariable Long id,
            @RequestBody Matiere updatedStudent) {

        try {
            Optional<Matiere> existingStudentOptional = matiereRepository.findById(id);

            if (existingStudentOptional.isPresent()) {
                Matiere existingStudent = existingStudentOptional.get();

                // Update the student properties
                existingStudent.setNomMat(updatedStudent.getNomMat());
                existingStudent.setHours(updatedStudent.getHours());
                existingStudent.setMinutes(updatedStudent.getMinutes());


                // Assuming that 'classe' property should not be updated, as it is related to a specific class

                Matiere updatedEtudiant = matiereRepository.save(existingStudent);
                return new ResponseEntity<>(updatedEtudiant, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteMat/{id}")
    public ResponseEntity<Void> deleteMatiere(@PathVariable Long id) {
        try {
            Optional<Matiere> studentOptional = matiereRepository.findById(id);

            if (studentOptional.isPresent()) {
                matiereRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getMatiere/{id}")
    public ResponseEntity<Matiere> getMatiereById(@PathVariable Long id) {
        try {
            Optional<Matiere> matiereOptional = matiereRepository.findById(id);

            if (matiereOptional.isPresent()) {
                Matiere matiere = matiereOptional.get();
                return new ResponseEntity<>(matiere, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

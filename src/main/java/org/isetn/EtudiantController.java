package org.isetn;

import org.isetn.Classe;
import org.isetn.Etudiant;
import org.isetn.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/classes/etudiants")
public class EtudiantController {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @GetMapping
    public ResponseEntity<List<Etudiant>> getAllErudiants() {
        try {
            List<Etudiant> absences = etudiantRepository.findAll();
            return new ResponseEntity<>(absences, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{classId}") // Add the @PathVariable annotation here
    public ResponseEntity<List<Etudiant>> getStudentsByClass(@PathVariable Long classId) {
        try {
            List<Etudiant> students = etudiantRepository.findByClasse_CodClass(classId);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/addStudent",method = RequestMethod.POST)
    public Etudiant addStudent(@RequestBody Etudiant etudiant) {


            Etudiant savedEtudiant = etudiantRepository.save(etudiant);
            return savedEtudiant;

    }
    @PutMapping("/updateStudent/{id}")
    public ResponseEntity<Etudiant> updateStudent(
            @PathVariable Long id,
            @RequestBody Etudiant updatedStudent) {

        try {
            Optional<Etudiant> existingStudentOptional = etudiantRepository.findById(id);

            if (existingStudentOptional.isPresent()) {
                Etudiant existingStudent = existingStudentOptional.get();

                // Update the student properties
                existingStudent.setNom(updatedStudent.getNom());
                existingStudent.setPrenom(updatedStudent.getPrenom());
                existingStudent.setDateNais(updatedStudent.getDateNais());

                // Assuming that 'classe' property should not be updated, as it is related to a specific class

                Etudiant updatedEtudiant = etudiantRepository.save(existingStudent);
                return new ResponseEntity<>(updatedEtudiant, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteStudent/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        try {
            Optional<Etudiant> studentOptional = etudiantRepository.findById(id);

            if (studentOptional.isPresent()) {
                etudiantRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/subjects/{etudiantId}")
    public ResponseEntity<List<Matiere>> getSubjectsForEtudiant(@PathVariable Long etudiantId) {
        try {
            Etudiant etudiant = etudiantRepository.findById(etudiantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + etudiantId));

            Classe studentClass = etudiant.getClasse();

            if (studentClass != null) {
                Collection<Matiere> subjects = studentClass.getMatieres();

                List<Matiere> subjectList = new ArrayList<>(subjects); // Convert the Collection to a List

                return new ResponseEntity<>(subjectList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }








    }



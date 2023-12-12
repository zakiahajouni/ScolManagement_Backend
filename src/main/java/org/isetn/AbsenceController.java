package org.isetn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/absences")
public class AbsenceController {

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private MatiereRepository matiereRepository;
    @Autowired
    private EtudiantRepository etudiantRepository;


    @GetMapping
    public ResponseEntity<List<Absence>> getAllAbsences() {
        try {
            List<Absence> absences = absenceRepository.findAll();
            return new ResponseEntity<>(absences, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Absence> getAbsenceById(@PathVariable Long id) {
        try {
            Absence absence = absenceRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Absence not found with id: " + id));
            return new ResponseEntity<>(absence, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/updateAbs/{id}", method = RequestMethod.PUT)
    public Absence updateAbsence(@PathVariable Long id, @RequestBody Absence ab) {
        // Set the id for the updated employe
        ab.setIdAbsence(id);
        return absenceRepository.save(ab);
    }

    @DeleteMapping("/deleteAbsence/{id}")
    public ResponseEntity<String> deleteClass(@PathVariable Long id) {
        try {
            Optional<Absence> absenceOptional = absenceRepository.findById(id);
            if (absenceOptional.isPresent()) {
                absenceRepository.deleteById(id);
                return new ResponseEntity<>("Absence deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Absence not found with id: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete Absence: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public Absence addMatiere(@RequestBody Absence matiere) {
        try {

            Absence savedMatiere = absenceRepository.save(matiere);
            return savedMatiere;
        } catch (Exception e) {

            return null;
        }
    }



    @GetMapping("/etud/{etudiantId}")
    public List<Absence> getAbsencesByEtudiant(@PathVariable Long etudiantId) {
        Etudiant etudiant = new Etudiant();
        etudiant.setId(etudiantId);

        return absenceRepository.findByEtudiant(etudiant);
    }
    @GetMapping("/totalHours/{etudiantId}/{codMatiere}")
    public ResponseEntity<Integer> getTotalAbsenceHoursByStudentAndSubject(@PathVariable Long etudiantId, @PathVariable Long codMatiere) {
        try {
            Integer totalHours = absenceRepository.sumAbsenceHoursByStudentAndSubject(etudiantId, codMatiere);
            return new ResponseEntity<>(totalHours != null ? totalHours : 0, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }}
    @GetMapping("/totalHours/etud/{etudiantId}")
        public ResponseEntity<Integer> getTotalAbsenceHoursByStudent(@PathVariable Long etudiantId) {
            try {
                Integer totalHours = absenceRepository.sumAbsenceHoursByStudent(etudiantId);
                return new ResponseEntity<>(totalHours != null ? totalHours : 0, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }





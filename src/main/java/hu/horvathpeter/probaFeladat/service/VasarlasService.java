package hu.horvathpeter.probaFeladat.service;

import hu.horvathpeter.probaFeladat.model.Vasarlas;
import hu.horvathpeter.probaFeladat.repository.BoltRepository;
import hu.horvathpeter.probaFeladat.repository.VasarlasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class VasarlasService {

    @Autowired
    private VasarlasRepository vasarlasRepository;

    @Autowired
    private BoltRepository boltRepository;

    public List<Vasarlas> retrieveAllVasarlas() {
        if(vasarlasRepository.findAll().size() > 0) {
            return vasarlasRepository.findAll();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Vasarlas retrieveVasarlasById(int id) {
        if(vasarlasRepository.findById(id).isPresent()) {
            return vasarlasRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> createVasarlas(Vasarlas vasarlas, int id) {
        Random random = new Random();
        Vasarlas v = new Vasarlas();
        v.setBolt(boltRepository.findById(id).get());
        v.setEsemenyDatumIdo(new Date());
        v.setPartnerId(vasarlas.getPartnerId());
        v.setPenztargepAzonosito(random.nextInt(30));
        v.setVasarlasTetel(vasarlas.getVasarlasTetel());
        v.getVasarlasTetel().forEach(vasarlasTetel -> vasarlasTetel.setVasarlas(v));
        vasarlasRepository.save(v);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> updateVasarlas(Vasarlas vasarlas, int id) {
        if(vasarlasRepository.findById(id).isPresent()) {
            Vasarlas v = vasarlasRepository.findById(id).get();
            v.setId(id);
            v.setBolt(vasarlasRepository.findById(id).get().getBolt());
            v.setVasarlasOsszeg(vasarlas.getVasarlasOsszeg());
            v.setPenztargepAzonosito(vasarlas.getPenztargepAzonosito());
            v.setPartnerId(vasarlas.getPartnerId());
            v.setEsemenyDatumIdo(new Date());
            v.setVasarlasTetel(vasarlas.getVasarlasTetel());
            v.getVasarlasTetel().forEach(vasarlasTetel -> vasarlasTetel.setVasarlas(v));
            vasarlasRepository.save(v);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<Void> deleteVasarlas(int id) {
        if(vasarlasRepository.findById(id).isPresent()) {
            vasarlasRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

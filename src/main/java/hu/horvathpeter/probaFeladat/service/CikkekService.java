package hu.horvathpeter.probaFeladat.service;

import hu.horvathpeter.probaFeladat.model.Cikkek;
import hu.horvathpeter.probaFeladat.repository.CikkekRepository;
import hu.horvathpeter.probaFeladat.repository.VasarlasTetelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CikkekService {

    @Autowired
    private CikkekRepository cikkekRepository;

    @Autowired
    private VasarlasTetelRepository vasarlasTetelRepository;

    public List<Cikkek> retrieveAllCikk() {
        if(cikkekRepository.findAll().size() > 0) {
            return cikkekRepository.findAll();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Cikkek retrieveCikkById(int id) {
        if(cikkekRepository.findById(id).isPresent()) {
            return cikkekRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> createCikk(Cikkek cikkek) {
        Cikkek c = new Cikkek();
        c.setPartnerId(cikkek.getPartnerId());
        c.setVerzio(cikkek.getVerzio());
        c.setNettoEgysegar(cikkek.getNettoEgysegar());
        c.setMennyisegEgyseg(cikkek.getMennyisegEgyseg());
        c.setNev(cikkek.getNev());
        c.setVonalKod(cikkek.getVonalKod());
        c.setCikkSzam(cikkek.getCikkSzam());
        cikkekRepository.save(c);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> updateCikk (Cikkek cikkek, int id) {
        if(cikkekRepository.findById(id).isPresent()) {
            Cikkek c = cikkekRepository.findById(id).get();
            c.setCikkSzam(cikkek.getCikkSzam());
            c.setVonalKod(cikkek.getVonalKod());
            c.setNev(cikkek.getNev());
            c.setMennyisegEgyseg(cikkek.getMennyisegEgyseg());
            c.setNettoEgysegar(cikkek.getNettoEgysegar());
            c.setVerzio(cikkek.getVerzio());
            c.setPartnerId(cikkek.getPartnerId());
            c.setId(id);
            cikkekRepository.save(c);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<Void> deleteCikk(int id) {
        if(cikkekRepository.findById(id).isPresent()) {
            cikkekRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

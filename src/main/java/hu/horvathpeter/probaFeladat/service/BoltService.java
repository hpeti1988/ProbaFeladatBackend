package hu.horvathpeter.probaFeladat.service;

import hu.horvathpeter.probaFeladat.model.Bolt;
import hu.horvathpeter.probaFeladat.repository.BoltRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BoltService {

    @Autowired
    private BoltRepository boltRepository;

    public List<Bolt> retrieveAllBolt() {
        if(boltRepository.findAll().size() > 0) {
            return boltRepository.findAll();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public Bolt retrieveBoltById(int id) {
        if(boltRepository.findById(id).isPresent()) {
            return boltRepository.findById(id).get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Void> createBolt(Bolt bolt) {
        Bolt b = new Bolt();
        b.setId(bolt.getId());
        b.setNev(bolt.getNev());
        b.setPartnerId(bolt.getPartnerId());
        b.setVasarlas(bolt.getVasarlas());
//        b.getVasarlas().forEach(vasarlas -> vasarlas.setBolt(b));
        boltRepository.save(b);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> updateBolt(Bolt bolt, int id) {
        if (boltRepository.findById(id).isPresent()) {
            Bolt b = boltRepository.findById(id).get();
            b.setId(id);
            b.setNev(bolt.getNev());
            b.setPartnerId(bolt.getPartnerId());
            b.setVasarlas(bolt.getVasarlas());
            boltRepository.save(b);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<Void> deleteBolt(int id) {
        if(boltRepository.findById(id).isPresent()) {
            boltRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

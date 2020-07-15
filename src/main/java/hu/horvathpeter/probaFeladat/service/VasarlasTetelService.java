package hu.horvathpeter.probaFeladat.service;

import hu.horvathpeter.probaFeladat.model.VasarlasTetel;
import hu.horvathpeter.probaFeladat.repository.CikkekRepository;
import hu.horvathpeter.probaFeladat.repository.VasarlasRepository;
import hu.horvathpeter.probaFeladat.repository.VasarlasTetelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class VasarlasTetelService {

    @Autowired
    private VasarlasTetelRepository vasarlasTetelRepository;

    @Autowired
    private VasarlasRepository vasarlasRepository;

    @Autowired
    private CikkekRepository cikkekRepository;

    public List<VasarlasTetel> findAllByVasarlas(int id) {
        return vasarlasTetelRepository.findAllByVasarlasId(id);
    }

    public List<VasarlasTetel> retrieveAllVasarlasTetel() {
        if (vasarlasTetelRepository.findAll().size() > 0) {
            return vasarlasTetelRepository.findAll();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public VasarlasTetel retrieveVasarlasTetelById(int id) {
        if(vasarlasTetelRepository.findById(id).isPresent()) {
            return vasarlasTetelRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> createVasarlasTetel (VasarlasTetel vasarlasTetel, int vasarlasId, int cikkId) {
        VasarlasTetel vt = new VasarlasTetel();
        vt.setVasarlas(vasarlasRepository.findById(vasarlasId).get());
        vt.setPartnerct(cikkekRepository.findById(cikkId).get());
        vt.setPartnerId(vasarlasTetel.getPartnerId());
        vt.setMennyiseg(vasarlasTetel.getMennyiseg());
        double brutto = (vt.getPartnerct().getNettoEgysegar() * 1.27) * vt.getMennyiseg();
        vt.setBrutto((float) brutto);
        float sum = vt.getVasarlas().getVasarlasOsszeg();
        vt.getVasarlas().setVasarlasOsszeg(sum + (float)brutto);
        vasarlasTetelRepository.save(vt);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> updateVasarlasTetel (VasarlasTetel vasarlasTetel, int id) {
        if(vasarlasTetelRepository.findById(id).isPresent()) {
            VasarlasTetel vt = new VasarlasTetel();
            vt.setVasarlas(vasarlasTetelRepository.findById(id).get().getVasarlas());
            vt.setMennyiseg(vasarlasTetel.getMennyiseg());
            vt.setBrutto(vasarlasTetel.getBrutto());
            vt.setId(id);
            vt.setPartnerId(vasarlasTetel.getPartnerId());
            vt.setPartnerct(vasarlasTetel.getPartnerct());
            vasarlasTetelRepository.save(vt);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<Void> deleteVasarlasTetel (int id) {
        if(vasarlasTetelRepository.findById(id).isPresent()) {
           VasarlasTetel vt = vasarlasTetelRepository.findById(id).get();
           float brutto = vt.getBrutto();
           float sum = vt.getVasarlas().getVasarlasOsszeg();
           vt.getVasarlas().setVasarlasOsszeg(sum - brutto);
           vasarlasTetelRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

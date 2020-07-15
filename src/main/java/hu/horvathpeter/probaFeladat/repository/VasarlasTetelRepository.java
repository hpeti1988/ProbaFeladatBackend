package hu.horvathpeter.probaFeladat.repository;

import hu.horvathpeter.probaFeladat.model.VasarlasTetel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VasarlasTetelRepository extends JpaRepository<VasarlasTetel, Integer> {
    List<VasarlasTetel> findAllByVasarlasId(int id);
}

package hu.horvathpeter.probaFeladat.repository;

import hu.horvathpeter.probaFeladat.model.Cikkek;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CikkekRepository extends JpaRepository<Cikkek, Integer> {
    Cikkek findByVasarlasTetelId(int id);
}

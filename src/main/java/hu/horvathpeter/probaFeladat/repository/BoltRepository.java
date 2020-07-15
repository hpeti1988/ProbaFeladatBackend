package hu.horvathpeter.probaFeladat.repository;

import hu.horvathpeter.probaFeladat.model.Bolt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoltRepository extends JpaRepository<Bolt, Integer> {
}

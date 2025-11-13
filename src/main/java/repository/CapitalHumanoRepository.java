package repository;

import model.CapitalHumano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapitalHumanoRepository extends JpaRepository<CapitalHumano, Integer> {
}

package Repository;

import Models.CarType;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CarTypeRepository extends JpaRepository<CarType, String> {
    boolean existsById(String typeName);
}

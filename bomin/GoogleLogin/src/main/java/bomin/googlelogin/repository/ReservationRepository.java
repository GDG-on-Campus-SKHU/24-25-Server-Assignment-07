package bomin.googlelogin.repository;
import bomin.googlelogin.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{
}

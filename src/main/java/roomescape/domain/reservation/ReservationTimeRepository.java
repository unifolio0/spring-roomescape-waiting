package roomescape.domain.reservation;

import java.time.LocalTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationTimeRepository extends JpaRepository<ReservationTime, Long> {
    boolean existsByStartAt(LocalTime time);
}

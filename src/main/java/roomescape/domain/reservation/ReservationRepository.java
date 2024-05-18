package roomescape.domain.reservation;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import roomescape.domain.dto.AvailableTimeDto;
import roomescape.domain.theme.Theme;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    long countByTimeId(long timeId);

    boolean existsByDateAndTimeIdAndThemeId(LocalDate date, long timeId, long themeId);

    @EntityGraph(attributePaths = {"time", "theme"})
    List<Reservation> findAllByMemberIdOrderByDateAsc(Long memberId);

    @Query("select new roomescape.domain.dto.AvailableTimeDto(rt.id, rt.startAt, "
            + "(select count(r) > 0 from Reservation r where r.time.id = rt.id and r.date = :date and r.theme.id = :themeId)) "
            + "from ReservationTime rt")
    List<AvailableTimeDto> findAvailableReservationTimes(LocalDate date, long themeId);

    @Query("select t from Theme t "
            + "join Reservation r on r.theme.id = t.id "
            + "and r.date between :startDate and :endDate "
            + "group by t.id, t.name, t.description, t.thumbnail "
            + "order by count(r.id) desc")
    List<Theme> findPopularThemesDateBetween(LocalDate startDate, LocalDate endDate);


    @Query(value = "select r from Reservation r "
            + "where r.theme.id = :themeId and "
            + "r.member.id = :memberId and "
            + ":dateFrom <= r.date and "
            + "r.date <= :dateTo")
    List<Reservation> findByCriteria(Long themeId, Long memberId, LocalDate dateFrom, LocalDate dateTo);
}

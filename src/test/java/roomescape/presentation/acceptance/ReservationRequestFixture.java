package roomescape.presentation.acceptance;

import java.time.LocalDate;
import roomescape.dto.ReservationRequest;

class ReservationRequestFixture {
    static ReservationRequest of(long timeId, long themeId) {
        return new ReservationRequest(LocalDate.of(2024, 1, 1), timeId, themeId);
    }

    static ReservationRequest of(LocalDate date, long timeId, long themeId) {
        return new ReservationRequest(date, timeId, themeId);
    }
}

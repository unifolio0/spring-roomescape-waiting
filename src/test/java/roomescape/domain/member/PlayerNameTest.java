package roomescape.domain.member;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import roomescape.exception.RoomescapeException;

class PlayerNameTest {
    @DisplayName("이름이 비어있거나 null이면 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void shouldThrowExceptionWhenNameIsNullOrEmpty(String input) {
        assertThatCode(() -> new PlayerName(input))
                .isInstanceOf(RoomescapeException.class)
                .extracting("httpStatus")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @DisplayName("이름이 20자를 초과하면 예외가 발생한다.")
    @Test
    void shouldThrowExceptionWhenNameLengthExceededMaxLength() {
        String input = "-".repeat(21);
        assertThatCode(() -> new PlayerName(input))
                .isInstanceOf(RoomescapeException.class)
                .extracting("httpStatus")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @DisplayName("이름이 올바르게 생성된다.")
    @ParameterizedTest
    @ValueSource(strings = {"1", "12345678901234567890"})
    void shouldCreateName(String input) {
        assertThatCode(() -> new PlayerName(input))
                .doesNotThrowAnyException();
    }
}

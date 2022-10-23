package nextstep.domain.schedule.validator;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import nextstep.domain.schedule.dto.ScheduleCommandDto.Create;
import nextstep.exception.ReservationIllegalArgumentException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DateAndTimeValidator implements ScheduleCreateValidator {

  @Override
  public void validate(Create create) {
    var now = LocalDateTime.now();

    var criteriaDate = now.toLocalDate();
    var criteriaTime = now.toLocalTime();

    var requestDate = create.date();
    var requestTime = create.time();

    if (criteriaDate.isAfter(requestDate)) {
      var message = "이미 지난 날짜에 대해 예약할 수 없습니다. 현재 날짜 : %s, 요청 날짜 : %s".formatted(requestDate, criteriaDate);
      log.debug("예약 생성 중 예외 발생 : {}", message);
      throw new ReservationIllegalArgumentException(message);
    }

    if (criteriaDate.isEqual(requestDate) && criteriaTime.isAfter(requestTime)) {
      var message = "이미 지난 시간에 대해 예약할 수 없습니다. 현재 시간 : %s, 요청 시간 : %s".formatted(requestTime, criteriaTime);
      log.debug("예약 생성 중 예외 발생 : {}", message);
      throw new ReservationIllegalArgumentException(message);
    }
  }
}
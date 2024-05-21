package hr.fer.infsus.sausc.constants;

import java.time.LocalTime;

public interface SAUSCConstants {
    LocalTime START_TIME = LocalTime.of(10, 0);
    LocalTime END_TIME = LocalTime.of(22, 0);
    Integer INTERVAL_MINUTES = 30;
    Integer MAX_RESERVATION_DURATION = 3;

}

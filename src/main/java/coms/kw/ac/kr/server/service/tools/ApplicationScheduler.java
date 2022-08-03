package coms.kw.ac.kr.server.service.tools;

import coms.kw.ac.kr.server.service.tools.ScheduledTask.UpdateRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApplicationScheduler {

    private final List<ScheduledTask> scheduledOnceAnHour;
    private final List<ScheduledTask> scheduledOnceADay;
    private final List<ScheduledTask> scheduledNever;

    private static final Logger logger = LoggerFactory.getLogger(ApplicationScheduler.class);

    public interface ScheduledTaskManualUpdater {
        void update();
    }

    @Autowired
    public ApplicationScheduler(List<ScheduledTask> scheduledUpdates) {
        List<ScheduledTask> onceAnHour = scheduledUpdates.stream()
                .filter(s -> s.getUpdateRate().equals(UpdateRate.ONCE_AN_HOUR)).collect(Collectors.toList());
        List<ScheduledTask> onceADay = scheduledUpdates.stream()
                .filter(s -> s.getUpdateRate().equals(UpdateRate.ONCE_A_DAY)).collect(Collectors.toList());
        List<ScheduledTask> never = scheduledUpdates.stream().filter(s -> s.getUpdateRate().equals(UpdateRate.NEVER))
                .collect(Collectors.toList());

        onceAnHour.sort(comparator);
        onceADay.sort(comparator);
        never.sort(comparator);

        this.scheduledOnceAnHour = Collections.unmodifiableList(onceAnHour);
        this.scheduledOnceADay = Collections.unmodifiableList(onceADay);
        this.scheduledNever = Collections.unmodifiableList(never);
    }

    @Bean
    public ScheduledTaskManualUpdater getManualUpdater(){
        return () -> {
            update(this.scheduledOnceAnHour);
            update(this.scheduledOnceADay);
            update(this.scheduledNever);
        };
    }

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void updateOnceAnHour() {
        update(this.scheduledOnceAnHour);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateOnceADay() {
        update(this.scheduledOnceADay);
    }

    private void update(List<ScheduledTask> scheduleds) {
        int error = 0;
        for (ScheduledTask s : scheduleds) {
            try {
                s.scheduledUpdate();
            } catch (Exception exception) {
                error++;
                logger.error("Exception from scheduled update.", exception);
                continue;
            }
        }
        logger.debug("Scheduled update end. success={}, error={}", scheduleds.size() - error, error);
    }

    private static final Comparator<ScheduledTask> comparator = new Comparator<ScheduledTask>() {
        @Override
        public int compare(ScheduledTask s1, ScheduledTask s2) {
            int p1 = s1.getUpdatePriority();
            int p2 = s2.getUpdatePriority();

            if (p1 > p2)
                return 1;
            else if (p1 == p2)
                return 0;
            else
                return -1;
        }
    };

}
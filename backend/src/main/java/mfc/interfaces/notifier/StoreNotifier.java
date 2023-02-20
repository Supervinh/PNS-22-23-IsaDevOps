package mfc.interfaces.notifier;

import mfc.POJO.Schedule;

import java.util.List;

public interface StoreNotifier {
    boolean notify(List<Schedule> scheduleList);
}

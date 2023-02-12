package mfc.interfaces.notifier;

import java.util.Map;

public interface StoreNotifier {
    boolean notify(Map<String, String> openingHours);
}

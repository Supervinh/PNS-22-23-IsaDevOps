package interfaces;

import java.util.Map;

public interface StoreNotifier {
    boolean notify(Map<String, String> openingHours);
}

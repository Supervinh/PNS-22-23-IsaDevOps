package interfaces;

import POJO.Customer;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface StoreNotifier {

    boolean notify(Map<String,String> openingHours);
}

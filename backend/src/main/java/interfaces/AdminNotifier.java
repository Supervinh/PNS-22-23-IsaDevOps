package interfaces;

import POJO.Admin;
import POJO.Customer;

import java.util.List;

public interface AdminNotifier {
    boolean notify(List<Customer> targets, String message, Admin authorization);
}

package mfc.interfaces.notifier;

import mfc.pojo.Admin;
import mfc.pojo.Customer;

import java.util.List;

public interface AdminNotifier {
    boolean notify(List<Customer> targets, String message, Admin authorization);
}

package mfc.interfaces.notifier;

import mfc.POJO.Admin;
import mfc.POJO.Customer;

import java.util.List;

public interface AdminNotifier {
    boolean notify(List<Customer> targets, String message, Admin authorization);
}

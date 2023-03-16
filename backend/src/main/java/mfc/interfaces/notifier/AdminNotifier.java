package mfc.interfaces.notifier;

import mfc.entities.Admin;
import mfc.entities.Customer;

import java.util.List;

public interface AdminNotifier {
    boolean notify(List<Customer> targets, String message, Admin authorization);
}

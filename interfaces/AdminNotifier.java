package interfaces;

import POJO.Admin;
import POJO.Customer;

import java.util.List;
import java.util.UUID;

public interface AdminNotifier {

    boolean notify(List<Customer> targets, String message, Admin authorization);

}

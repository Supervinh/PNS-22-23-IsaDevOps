package mfc.controllers.dto;

import mfc.POJO.Customer;
import mfc.POJO.Purchase;
import mfc.POJO.Store;

public class ConvertDTO {

    public CustomerDTO convertCustomerToDto(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getMail(), customer.getFidelityPoints(), customer.getCreditCard());
    }

    public StoreDTO convertStoreToDto(Store store) {
        return new StoreDTO(store.getId(), store.getName(), store.getOpeningHours(), store.getOwner());
    }

    public PurchaseDTO convertPurchaseToDto(Purchase purchase) {
        return new PurchaseDTO(purchase.getId(), convertCustomerToDto(purchase.getCustomer()),purchase.getCost(), convertStoreToDto(purchase.getStore()));
    }
}

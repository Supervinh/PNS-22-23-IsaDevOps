package mfc.controllers.dto;

import mfc.POJO.PayOff;
import mfc.POJO.Store;
import mfc.POJO.StoreOwner;

import java.util.Set;
import java.util.stream.Collectors;

public class ConvertDTO {

//    public CustomerDTO convertCustomerToDto(Customer customer) {
//        return new CustomerDTO(customer.getId(), customer.getMail(), customer.getFidelityPoints(), customer.getCreditCard());
//    }

    public StoreDTO convertStoreToDto(Store store) {
        return new StoreDTO(store.getId(), store.getName(), /*store.getOpeningHours(),*/ convertStoreOwnerToDto(store.getOwner()));
    }

    public StoreOwnerDTO convertStoreOwnerToDto(StoreOwner store) {
        return new StoreOwnerDTO(store.getId(), store.getName(), store.getMail(), store.getPassword());
    }

//    public PurchaseDTO convertPurchaseToDto(Purchase purchase) {
//        return new PurchaseDTO(purchase.getId(), convertCustomerToDto(purchase.getCustomer()), purchase.getCost(), convertStoreToDto(purchase.getStore()));
//    }

    public CatalogDTO convertCatalogToDTO(Set<PayOff> payOffSet) {
        return new CatalogDTO(payOffSet.stream().map(this::convertPayoffToDTO).collect(Collectors.toSet()));
    }

    public PayoffDTO convertPayoffToDTO(PayOff payOff) {
        return new PayoffDTO(payOff.getName(), payOff.getCost(), payOff.getPointCost(), convertStoreToDto(payOff.getStore()), payOff.getId());
    }
}

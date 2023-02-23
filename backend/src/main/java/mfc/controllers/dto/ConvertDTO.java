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
        return new StoreDTO(store.getId(), store.getName(), store.getSchedule(), store.getOwner().getName());
    }


    public StoreOwnerDTO convertStoreOwnerToDto(StoreOwner owner) {
        StoreOwnerDTO ret = new StoreOwnerDTO();
        ret.setName(owner.getName());
        ret.setMail(owner.getMail());
        ret.setPassword(owner.getPassword());
        ret.setId(owner.getId());
        return ret;
    }

//    public PurchaseDTO convertPurchaseToDto(Purchase purchase) {
//        return new PurchaseDTO(purchase.getId(), convertCustomerToDto(purchase.getCustomer()), purchase.getCost(), convertStoreToDto(purchase.getStore()));
//    }

    public CatalogDTO convertCatalogToDTO(Set<PayOff> payOffSet) {
        return new CatalogDTO(payOffSet.stream().map(this::convertPayoffToDTO).collect(Collectors.toSet()));
    }

    public PayoffDTO convertPayoffToDTO(PayOff payOff) {
        return new PayoffDTO(payOff.getName(), payOff.getCost(), payOff.getPointCost()/*, convertStoreToDto(payOff.getStore())*/, payOff.getId());
    }
}

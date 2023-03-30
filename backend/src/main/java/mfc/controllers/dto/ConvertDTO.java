package mfc.controllers.dto;

import mfc.entities.*;

import java.util.Set;
import java.util.stream.Collectors;

public class ConvertDTO {

    private ConvertDTO() {
    }

    public static CustomerDTO convertCustomerToDto(Customer customer) { // In more complex cases, we could use ModelMapper
        CustomerDTO dto = new CustomerDTO(customer.getId(), customer.getName(), customer.getMail(), customer.getPassword(), customer.getCreditCard(), customer.getMatriculation());
        dto.setBalance(customer.getBalance());
        return dto;
    }

    public static StoreDTO convertStoreToDto(Store store) {
        return new StoreDTO(store.getId(), store.getName(), store.getSchedule(), store.getOwner().getName());
    }


    public static StoreOwnerDTO convertOwnerToDto(StoreOwner storeOwner) { // In more complex cases, we could use ModelMapper
        return new StoreOwnerDTO(storeOwner.getId(), storeOwner.getName(), storeOwner.getMail(), storeOwner.getPassword());
    }

    public static AdminDTO convertAdminToDto(Admin admin) { // In more complex cases, we could use ModelMapper
        return new AdminDTO(admin.getId(), admin.getName(), admin.getMail(), admin.getPassword());

    }

    public static PurchaseDTO convertPurchaseToDto(Purchase purchase) {
        return new PurchaseDTO(purchase.getId(), purchase.getCustomer().getMail(), purchase.getCost(), purchase.getStore().getName(), false);
    }

    public static CatalogDTO convertCatalogToDTO(Set<Payoff> payoffSet) {
        return new CatalogDTO(payoffSet.stream().map(ConvertDTO::convertPayoffToDTO).collect(Collectors.toSet()));
    }

    public static PayoffDTO convertPayoffToDTO(Payoff payOff) {
        return new PayoffDTO(payOff.getId(), payOff.getName(), payOff.getCost(), payOff.getPointCost(), payOff.getStore().getName(), payOff.isVfp());
    }

    public static PayoffPurchaseDTO convertPayoffPurchaseToDTO(PayoffPurchase payoffPurchase) {
        return new PayoffPurchaseDTO(payoffPurchase.getId(), payoffPurchase.getName(), payoffPurchase.getCost(), payoffPurchase.getPointCost(), payoffPurchase.getStore().getName(), payoffPurchase.getCustomer().getMail());
    }
}

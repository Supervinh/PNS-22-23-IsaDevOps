package mfc.controllers.dto;

import mfc.POJO.PayOff;
import mfc.POJO.Schedule;
import mfc.POJO.Store;
import mfc.POJO.StoreOwner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConvertDTO {

//    public CustomerDTO convertCustomerToDto(Customer customer) {
//        return new CustomerDTO(customer.getId(), customer.getMail(), customer.getFidelityPoints(), customer.getCreditCard());
//    }

    public StoreDTO convertStoreToDto(Store store) {
        return new StoreDTO(store.getName(),convertScheduleToDTO(store.getSchedule()),convertStoreOwnerToDto(store.getOwner()), store.getId());
    }

    public List<ScheduleDTO> convertScheduleToDTO(List<Schedule> scheduleList){
        List<ScheduleDTO> ret = new ArrayList<>();
        for (Schedule s : scheduleList){
            ScheduleDTO currentSchedule = new ScheduleDTO(s.getOpeningTime(), s.getClosingTime());
            ret.add(currentSchedule);
        }
        return ret;
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
        return new PayoffDTO(payOff.getName(), payOff.getCost(), payOff.getPointCost(), convertStoreToDto(payOff.getStore()), payOff.getId());
    }
}

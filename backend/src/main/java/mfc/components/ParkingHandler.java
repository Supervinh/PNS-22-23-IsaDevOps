package mfc.components;

import mfc.exceptions.NoMatriculationException;
import mfc.exceptions.ParkingException;
import mfc.interfaces.Parking;
import mfc.interfaces.ParkingProcessor;
import mfc.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkingHandler implements ParkingProcessor {

    private final Parking parking;

    @Autowired
    public ParkingHandler(Parking parking) {
        this.parking = parking;
    }

    @Override
    public boolean useParkingPayOff(Customer user) throws NoMatriculationException, ParkingException {
        if (user.getMatriculation().equals(""))
            throw new NoMatriculationException();
        if (parking.park(user.getMatriculation()))
            return true;
        else
            throw new ParkingException();
    }
}

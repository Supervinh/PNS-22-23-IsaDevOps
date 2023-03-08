package mfc.components;

import mfc.POJO.Customer;
import mfc.exceptions.NoMatriculationException;
import mfc.exceptions.ParkingException;
import mfc.interfaces.Parking;
import mfc.interfaces.ParkingProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkingHandler implements ParkingProcessor {

    @Autowired
    private Parking parking;

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

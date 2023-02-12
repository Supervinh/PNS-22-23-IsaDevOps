package mfc.repositories;

import mfc.POJO.Purchase;
import org.springframework.stereotype.Repository;
import repositories.BasicRepositoryImpl;

import java.util.UUID;

@Repository
public class PurchaseRepository extends BasicRepositoryImpl<Purchase, UUID> {

}

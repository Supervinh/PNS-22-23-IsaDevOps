package mfc.repositories;

import mfc.pojo.Purchase;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PurchaseRepository extends BasicRepositoryImpl<Purchase, UUID> {
}

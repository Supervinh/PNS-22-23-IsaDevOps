package mfc.repositories;

import mfc.pojo.PayoffPurchase;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PayoffPurchaseRepository extends BasicRepositoryImpl<PayoffPurchase, UUID> {
}

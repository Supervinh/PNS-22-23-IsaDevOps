package mfc.repositories;

import mfc.POJO.Store;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class StoreRepository extends BasicRepositoryImpl<Store, UUID> {
}

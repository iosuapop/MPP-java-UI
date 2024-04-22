package donations.repository;
import donations.domain.Donor;

import java.util.List;

public interface IDonorRepository extends IRepository<Integer, Donor>{
    List<Donor> getDonorName(String name);

    Integer getDonorByID(Donor donor);
}

package donations.repository;

import donations.domain.Charity;

import java.util.List;

public interface ICharityRepository extends IRepository<Integer, Charity>{
    Charity getIDByCharity(Charity charity);
    List<String> getCases();
}

package donations.service;

import donations.domain.Charity;

import java.util.List;

public interface ICharityService {
    List<String> getAllCase();
    Charity getIdByCase(Charity charity);
}

package server.fasade;

import server.model.employee.Employee;
import server.model.institution.Institution;
import server.repository.InstitutionRepository;

import java.util.List;
import java.util.Map;

/**
 * Facade to support operation on Institutions
 */
public class InstitutionFacade {

    InstitutionRepository institutionRepository = InstitutionRepository.getInstance();

    public List<Institution> findAllInstitutions() {
        return institutionRepository.findAll();
    }

    public List<Institution> deleteInstitution(List<Institution> institutions) {
        institutionRepository.deleteEmployee(institutions);
        return institutionRepository.findAll();

    }

    public List<Institution> saveNewInstitution(Institution institution) {
        institutionRepository.saveNewInstitution(institution);
        return institutionRepository.findAll();
    }

    public Institution findInstitutionByName(Institution institution) {
        return institutionRepository.findInstitutionByName(institution);
    }

    public List<Institution> updateInstitution(Map<String, Institution> institutionMap) {
        institutionRepository.updateInstitution(institutionMap);
        return institutionRepository.findAll();

    }
}

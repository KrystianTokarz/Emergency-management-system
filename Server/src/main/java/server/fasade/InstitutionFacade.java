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

    private InstitutionFacade() {
    }

    private static InstitutionFacade instance = null;

    public static InstitutionFacade getInstance() {
        if (instance == null) {
            instance = new InstitutionFacade();
        }
        return instance;
    }

    private InstitutionRepository institutionRepository = InstitutionRepository.getInstance();

    public List<Institution> findAllInstitutions() {
        return institutionRepository.findAll();
    }

    public List<Institution> deleteInstitution(Object institutionsObject) {
        List<Institution> institutions = (List<Institution>) institutionsObject;
        institutionRepository.deleteEmployee(institutions);
        return institutionRepository.findAll();
    }

    public List<Institution> saveNewInstitution(Object institutionObject) {
        Institution institution = (Institution) institutionObject;
        institutionRepository.saveNewInstitution(institution);
        return institutionRepository.findAll();
    }

    public Institution findInstitutionByName(Object institutionObject) {
        Institution institution = (Institution) institutionObject;
        return institutionRepository.findInstitutionByName(institution);
    }

    public List<Institution> updateInstitution(Object institutionMapObject) {
        Map<String, Institution> institutionMap = (Map<String, Institution>) institutionMapObject;
        institutionRepository.updateInstitution(institutionMap);
        return institutionRepository.findAll();
    }
}

package server.repository;

import server.model.institution.Institution;
import server.model.localization.Locality;
import server.model.localization.Province;
import server.model.localization.ProvinceType;
import server.model.localization.Street;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Class LocalizationRepository for CRUD operation on Localization Entity (singleton)
 */
public class LocalizationRepository extends Repository {


    private LocalizationRepository() {
    }

    private static LocalizationRepository instance = null;

    public static LocalizationRepository getInstance() {
        if (instance == null) {
            instance = new LocalizationRepository();
        }
        return instance;
    }

    public Province findById(Long id) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Province> criteriaQuery = criteriaBuilder.createQuery(Province.class);
        Root<Province> localization = criteriaQuery.from(Province.class);
        criteriaQuery.select(localization).where(localization.get("id").in(id));
        Province singleResult = entityManager.createQuery(criteriaQuery).getSingleResult();

        return singleResult;
    }

    public List<Province> findAllProvince() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Province> criteriaQuery = criteriaBuilder.createQuery(Province.class);
        Root<Province> provincesInDatabase = criteriaQuery.from(Province.class);
        criteriaQuery.select(provincesInDatabase);
        List<Province> resultList = entityManager.createQuery(criteriaQuery).getResultList();
        entityManager.clear();
        return resultList;
    }

    public Locality findLocality(String localityName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Locality> criteriaQuery = criteriaBuilder.createQuery(Locality.class);
        Root<Locality> localityInDatabase = criteriaQuery.from(Locality.class);

        criteriaQuery.select(localityInDatabase)
                .where(localityInDatabase.get("locality").in(localityName));
        Locality foundedLocality = entityManager.createQuery(criteriaQuery).getSingleResult();
        entityManager.clear();
        return foundedLocality;
    }


    public Street findStreet(String streetName, String streetNumber) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Street> criteriaQuery = criteriaBuilder.createQuery(Street.class);
        Root<Street> streetInDatabase = criteriaQuery.from(Street.class);


        criteriaQuery.select(streetInDatabase)
                .where(streetInDatabase.get("street").in(streetName)
                        , streetInDatabase.get("specialNumber").in(streetNumber));
        Street foundedStreet = entityManager.createQuery(criteriaQuery).getSingleResult();
        entityManager.clear();
        return foundedStreet;
    }

    public Province findProvince(String provinceName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Province> criteriaQuery = criteriaBuilder.createQuery(Province.class);
        Root<Province> provinceInDatabase = criteriaQuery.from(Province.class);

        int valueOfProvinceType = -1;
        if (provinceName.equals("SWIETOKRZYSKIE"))
            valueOfProvinceType = 0;
        else if (provinceName.equals("LODZKIE"))
            valueOfProvinceType = 1;
        else if (provinceName.equals("MASLOVIA"))
            valueOfProvinceType = 2;
        criteriaQuery.select(provinceInDatabase)
                .where(provinceInDatabase.get("provinceType").in(valueOfProvinceType));
        Province foundedProvince = entityManager.createQuery(criteriaQuery).getSingleResult();
        entityManager.clear();
        return foundedProvince;
    }
}
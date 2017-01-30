package server.fasade;

import server.model.localization.Province;
import server.repository.LocalizationRepository;

import java.util.List;

/**
 * Facade to support operation on Localizations
 */
public class LocalizationFacade {


    private LocalizationFacade() {
    }

    private static LocalizationFacade instance = null;

    public static LocalizationFacade getInstance() {
        if (instance == null) {
            instance = new LocalizationFacade();
        }
        return instance;
    }

    private LocalizationRepository localizationRepository = LocalizationRepository.getInstance();

    public List<Province> findAllProvince() {
        return localizationRepository.findAllProvince();
    }
}

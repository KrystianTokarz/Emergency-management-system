package server.fasade;

import server.model.localization.Province;
import server.repository.LocalizationRepository;

import java.util.List;

/**
 * Facade to support operation on Localizations
 */
public class LocalizationFacade {

    private LocalizationRepository localizationRepository = LocalizationRepository.getInstance();

    public List<Province> findAllProvince() {
        return localizationRepository.findAllProvince();

    }
}

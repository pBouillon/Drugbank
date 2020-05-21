package factory;

import repository.DiseaseRepository;
import repository.DrugRepository;
import repository.RepositoryBase;
import repository.SymptomRepository;

import java.io.IOException;
import java.util.HashMap;

/**
 * Factory to handle repository instances
 * @see repository.RepositoryBase
 */
public class RepositoryFactory {

    /**
     * Private repository keys for cached repositories handling
     */
    private enum RepositoryKey {
        DISEASE,
        DRUG,
        SYMPTOM
    }

    /**
     * Hashmap of all repositories that may have been queried, acting as a cache
     */
    private final HashMap<RepositoryKey, RepositoryBase> _cache;

    /**
     * Default constructor
     */
    public RepositoryFactory() {
        // Initialize the cache with a maximum capacity of all the services it will contains
        _cache = new HashMap(
                RepositoryKey.values().length);
    }

    /**
     * Get a DiseaseRepository object
     * @return An instance of a DiseaseRepository
     * @see DiseaseRepository
     */
    public DiseaseRepository getDiseaseRepository() {
        if (!_cache.containsKey(RepositoryKey.DISEASE)) {
            _cache.put(RepositoryKey.DISEASE, new DiseaseRepository());
        }

        return (DiseaseRepository) _cache.get(RepositoryKey.DISEASE);
    }

    /**
     * Get a DrugRepository object
     * @return An instance of a DrugRepository
     * @see DrugRepository
     */
    public DrugRepository getDrugRepository() {
        if (!_cache.containsKey(RepositoryKey.DRUG)) {
            _cache.put(RepositoryKey.DRUG, new DrugRepository());
        }

        return (DrugRepository) _cache.get(RepositoryKey.DRUG);
    }

    /**
     * Get a SymptomRepository object
     * @return An instance of a SymptomRepository
     * @see SymptomRepository
     */
    public SymptomRepository getSymptomRepository() throws IOException {
        if (!_cache.containsKey(RepositoryKey.SYMPTOM)) {
            _cache.put(RepositoryKey.SYMPTOM, new SymptomRepository());
        }

        return (SymptomRepository) _cache.get(RepositoryKey.SYMPTOM);
    }

}

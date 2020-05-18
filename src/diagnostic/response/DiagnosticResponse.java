package diagnostic.response;

import common.pojo.Disease;
import common.pojo.Drug;
import util.Lazy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Container for the diagnostic manager response
 * @see diagnostic.DiagnosticManager
 */
public class DiagnosticResponse {

    /**
     * Collection of all IDiagnosableEntity with a list of the drugs curing their effects
     */
    public Map<IDiagnosableEntity, Lazy<List<Drug>>> _cures = new HashMap<>();

    /**
     * Get all causes of the IDiagnosableEntity causing the undesirable effect of the request
     * @return A list of all the IDiagnosableEntity causing the effect
     * @see diagnostic.request.DiagnosticRequest
     */
    public List<IDiagnosableEntity> getCauses() {
        return new ArrayList<>(_cures.keySet());
    }

    /**
     * Get all diseases causing of the undesirable effect from the request
     * @return A list of all diseases causing the effect
     * @see diagnostic.request.DiagnosticRequest
     */
    public List<Disease> getDiseaseCauses() {
        return _cures.keySet()
                .stream()
                .filter(key -> key instanceof Disease)
                .map(key -> (Disease) key)
                .collect(Collectors.toList());
    }

    /**
     * Get all drugs causing of the undesirable effect from the request
     * @return A list of all drugs causing the effect
     * @see diagnostic.request.DiagnosticRequest
     */
    public List<Drug> getDrugCauses() {
        return _cures.keySet()
                .stream()
                .filter(key -> key instanceof Drug)
                .map(key -> (Drug) key)
                .collect(Collectors.toList());
    }

    /**
     * Getter for the diagnosed cures
     * @return A map of all the diagnosed cures
     */
    public Map<IDiagnosableEntity, Lazy<List<Drug>>> getCures() {
        return _cures;
    }

    /**
     * Setter for the diagnosed cures
     * @param cures The new Map to set as the diagnosed cures
     */
    public void setCures(Map<IDiagnosableEntity, Lazy<List<Drug>>> cures) {
        _cures = cures;
    }

}

package common.pojo;

import util.Lazy;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic POJO holding drug's data
 * POJO built according to https://stackoverflow.com/a/3527340
 */
public class Drug {

    /**
     * The drug's indication
     */
    private String _indication;

    /**
     * The drug's name
     */
    private String _name;

    /**
     * Collection of all synonyms of this drug
     */
    private final Lazy<List<String>> _synonyms = new Lazy<>();

    /**
     * The drug's toxicity
     */
    private String _toxicity;

    /**
     * Collection of side effects caused by this drugs
     * @see Symptom
     */
    private final Lazy<List<Symptom>> _sideEffects = new Lazy<>();

    /**
     * Default constructor for parameter-less construction
     */
    public Drug() {
        _sideEffects.setSupplier(ArrayList::new);
        _synonyms.setSupplier(ArrayList::new);
    }

    /**
     * Getter for the drug's indication
     * @return The drug's indication as a String
     */
    public String getIndication() {
        return _indication;
    }

    /**
     * Getter for the drug's name
     * @return The drug's name as a String
     */
    public String getName() {
        return _name;
    }

    /**
     * Synonyms getter
     * @return An Iterable of String
     */
    public List<String> getSynonyms() {
        return _synonyms.getOrCompute();
    }

    /**
     * Getter for the drug's toxicity
     * @return The drug's toxicity as a String
     */
    public String getToxicity() {
        return _toxicity;
    }

    /**
     * Side effects getter
     * @return An Iterable of Symptom
     * @see Symptom
     */
    public List<Symptom> getSideEffects() {
        return _sideEffects.getOrCompute();
    }

    /**
     * Drug's indication setter
     * @param indication New indication to be set
     */
    public void setIndication(String indication) {
        _indication = indication;
    }

    /**
     * Drug's name setter
     * @param name New name to be set
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * Synonyms setter
     * @param synonyms Iterable of String corresponding to the synonyms of this drug
     */
    public void setSynonyms(List<String> synonyms) {
        _synonyms.setSupplier(() -> synonyms);
    }


    /**
     * Drug's toxicity setter
     * @param toxicity New name to be set
     */
    public void setToxicity(String toxicity) {
        _toxicity = toxicity;
    }

    /**
     * Side effects setter
     * @param sideEffects Iterable of Symptom corresponding to the side effects of this drug
     * @see Symptom
     */
    public void setSideEffects(List<Symptom> sideEffects) {
        _sideEffects.setSupplier(() -> sideEffects);
    }

}

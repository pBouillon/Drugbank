package common.pojo;

import util.Lazy;

/**
 * Basic POJO holding drug's data
 * POJO built according to https://stackoverflow.com/a/3527340
 */
public class Drug {

    /**
     * The drug's name
     */
    private String _name;

    /**
     * Collection of side effects caused by this drugs
     * @see Symptom
     */
    private final Lazy<Iterable<Symptom>> _sideEffects = new Lazy<>();

    /**
     * Drug ATC
     */
    private String _ATC;

    /**
     * Drug compound ID
     */
    private String _compoundId;

    /**
     * Default constructor for parameter-less construction
     */
    public Drug() { }

    /**
     * Specialized constructor omitting lazy values
     * @param name Drug's name
     */
    public Drug(String name) {
        _name = name;
    }

    /**
     * Specialized constructor
     * @param name Drug's name
     * @param sideEffects List of
     */
    public Drug(String name, Iterable<Symptom> sideEffects) {
        _name = name;
        _sideEffects.setSupplier(() -> sideEffects);
    }

    /**
     * Getter for the drug's name
     * @return The drug's name as a String
     */
    public String getName() {
        return _name;
    }

    /**
     * Side effects getter
     * @return An Iterable of Symptom
     * @see Symptom
     */
    public Iterable<Symptom> getSideEffects() {
        return _sideEffects.getOrCompute();
    }

    /**
     * Getter for the drug's ATC
     * @return the drug ATC if set
     */
    public String get_ATC() {
        return _ATC;
    }

    /**
     * Getter for the drug's compoundId
     * @return the drug compoundId if set
     */
    public String get_compoundId() {
        return _compoundId;
    }

    /**
     * Drug's name setter
     * @param name New name to be set
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * Side effects setter
     * @param sideEffects Iterable of Symptom corresponding to the side effects of this drug
     * @see Symptom
     */
    public void setSideEffects(Iterable<Symptom> sideEffects) {
        _sideEffects.setSupplier(() -> sideEffects);
    }

    /**
     * ATC setter
     * @param _ATC New ATC code
     */
    public void set_ATC(String _ATC) {
        this._ATC = _ATC;
    }

    /**
     * setter for CompoundId
     * @param _compoundId new Compound ID
     */
    public void set_compoundId(String _compoundId) {
        this._compoundId = _compoundId;
    }
}

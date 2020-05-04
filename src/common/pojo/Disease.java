package common.pojo;

import util.Lazy;

/**
 * Basic POJO holding disease's data
 * POJO built according to https://stackoverflow.com/a/3527340
 */
public class Disease {

    /**
     * Lazy list of all symptoms related to this disease
     */
    private final Lazy<Iterable<Symptom>> _associatedSymptoms = new Lazy<>();

    /**
     * Disease's name
     */
    private String _name;

    /**
     * Disease's synonyms
     */
    private Iterable<String> _synonyms;

    /**
     * Default constructor for parameter-less construction
     */
    public Disease() { }

    /**
     * Specialized constructor omitting lazy values
     * @param name Disease's name
     * @param synonyms Disease's synonyms
     */
    public Disease(String name, Iterable<String> synonyms) {
        _name = name;
        _synonyms = synonyms;
    }

    /**
     * Specialized constructor
     * @param associatedSymptoms Collection of all symptoms related to this disease
     * @param name Disease's name
     * @param synonyms Disease's synonyms
     */
    public Disease(Iterable<Symptom> associatedSymptoms, String name, Iterable<String> synonyms) {
        _associatedSymptoms.setSupplier(() -> associatedSymptoms);
        _name = name;
        _synonyms = synonyms;
    }

    /**
     * Get all symptoms related to this disease
     * @return A collection of all symptoms related to this disease
     * @see Symptom
     */
    public Iterable<Symptom> getAssociatedSymptoms() {
        return _associatedSymptoms.getOrCompute();
    }

    /**
     * Get the name of the disease
     * @return The name of the disease as a String
     */
    public String getName() {
         return _name;
    }

    /**
     * Get the synonyms of the disease
     * @return An Iterable of the disease synonyms
     */
    public Iterable<String> getSynonyms() {
        return _synonyms;
    }

    /**
     * Set the collection of all symptoms related to this disease
     * @param associatedSymptoms A collection of all symptoms related to this disease
     */
    public void setAssociatedSymptoms(Iterable<Symptom> associatedSymptoms) {
        _associatedSymptoms.setSupplier(() -> associatedSymptoms);
    }

    /**
     * Set the disease's name
     * @param name The name of the disease
     */
    public void setName(String name) {
       _name = name;
    }

    /**
     * Set the synonyms of this disease
     * @param synonyms An Iterable of the disease synonyms
     */
    public void setSynonyms(Iterable<String> synonyms) {
        _synonyms = synonyms;
    }

}

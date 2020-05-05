package common.pojo;

import util.Lazy;

import java.util.List;

/**
 * Basic POJO holding disease's data
 * POJO built according to https://stackoverflow.com/a/3527340
 */
public class Disease {

    /**
     * Lazy list of all symptoms related to this disease
     */
    private final Lazy<List<Symptom>> _associatedSymptoms = new Lazy<>();

    /**
     * Disease's name
     */
    private String _name;

    /**
     * Disease's synonyms
     */
    private List<String> _synonyms;

    /**
     * Default constructor for parameter-less construction
     */
    public Disease() { }

    /**
     * Specialized constructor omitting lazy values
     * @param name Disease's name
     * @param synonyms Disease's synonyms
     */
    public Disease(String name, List<String> synonyms) {
        _name = name;
        _synonyms = synonyms;
    }

    /**
     * Specialized constructor
     * @param associatedSymptoms Collection of all symptoms related to this disease
     * @param name Disease's name
     * @param synonyms Disease's synonyms
     */
    public Disease(List<Symptom> associatedSymptoms, String name, List<String> synonyms) {
        _associatedSymptoms.setSupplier(() -> associatedSymptoms);
        _name = name;
        _synonyms = synonyms;
    }

    /**
     * Get all symptoms related to this disease
     * @return A collection of all symptoms related to this disease
     * @see Symptom
     */
    public List<Symptom> getAssociatedSymptoms() {
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
     * @return An List of the disease synonyms
     */
    public List<String> getSynonyms() {
        return _synonyms;
    }

    /**
     * Set the collection of all symptoms related to this disease
     * @param associatedSymptoms A collection of all symptoms related to this disease
     */
    public void setAssociatedSymptoms(List<Symptom> associatedSymptoms) {
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
     * @param synonyms An List of the disease synonyms
     */
    public void setSynonyms(List<String> synonyms) {
        _synonyms = synonyms;
    }

}

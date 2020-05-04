package common.pojo;

import util.Lazy;

/**
 * Basic POJO holding symptom's data
 * POJO built according to https://stackoverflow.com/a/3527340
 */
public class Symptom {

    /**
     * Lazy list of all drugs that may cure this symptom
     */
    private final Lazy<Iterable<Drug>> _associatedDrugs = new Lazy<>();

    /**
     * Symptom's name
     */
    private String _name;

    /**
     * Default constructor for parameter-less construction
     */
    public Symptom() { }

    /**
     * Specialized constructor omitting lazy values
     * @param name Symptom's name
     */
    public Symptom(String name) {
        _name = name;
    }

    /**
     * Specialized constructor
     * @param associatedDrugs Collection of all drugs that may cure this disease
     * @param name Symptom's name
     */
    public Symptom(Iterable<Drug> associatedDrugs, String name) {
        _associatedDrugs.setSupplier(() -> associatedDrugs);
        _name = name;
    }

    /**
     * Get all drugs that may cure this disease
     * @return A collection of all drugs that may cure this disease
     * @see Drug
     */
    public Iterable<Drug> getAssociatedDrugs() {
        return _associatedDrugs.getOrCompute();
    }

    /**
     * Symptom name getter
     * @return The symptom name as String
     */
    public String getName() {
        return _name;
    }

    /**
     * Set the collection of all drugs that may cure this disease
     * @param associatedDrugs A collection of all drugs that may cure this disease
     */
    public void setAssociatedDrugs(Iterable<Drug> associatedDrugs) {
        _associatedDrugs.setSupplier(() -> associatedDrugs);
    }

    /**
     * Symptom name setter
     * @param name The symptom name
     */
    public void setName(String name) {
        _name = name;
    }

}

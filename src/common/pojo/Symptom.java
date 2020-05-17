package common.pojo;

import util.Lazy;

import java.util.List;

/**
 * Basic POJO holding symptom's data
 * POJO built according to https://stackoverflow.com/a/3527340
 */
public class Symptom {

    /**
     * Lazy list of all drugs that may cure this symptom
     */
    private final Lazy<List<Drug>> _associatedDrugs = new Lazy<>();

    /**
     * Symptom's name
     */
    private String _name;

    /**
     * Symptom hpo id
     */
    private String _hpoId;

    /**
     * Symptom cui
     */
    private String _cui;

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
    public Symptom(List<Drug> associatedDrugs, String name) {
        _associatedDrugs.setSupplier(() -> associatedDrugs);
        _name = name;
    }

    /**
     * Get all drugs that may cure this disease
     * @return A collection of all drugs that may cure this disease
     * @see Drug
     */
    public List<Drug> getAssociatedDrugs() {
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
     * hpoId getter
     * @return the hpo id
     */
    public String getHpoId() {
        return _hpoId;
    }

    /**
     * cui getter
     * @return the cui
     */
    public String getCui() {
        return _cui;
    }

    /**
     * Set the collection of all drugs that may cure this disease
     * @param associatedDrugs A collection of all drugs that may cure this disease
     */
    public void setAssociatedDrugs(List<Drug> associatedDrugs) {
        _associatedDrugs.setSupplier(() -> associatedDrugs);
    }

    /**
     * Symptom name setter
     * @param name The symptom name
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * hpoId setter
     * @param _hpoId the hpoId
     */
    public void setHpoId(String _hpoId) {
        this._hpoId = _hpoId;
    }

    /**
     * cui setter
     * @param _cui the symptom cui
     */
    public void setCui(String _cui) {
        this._cui = _cui;
    }
}

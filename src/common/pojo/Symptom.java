package common.pojo;

import util.Lazy;

import java.io.Serializable;
import java.util.List;

/**
 * Basic POJO holding symptom's data
 * POJO built according to https://stackoverflow.com/a/3527340
 */
public class Symptom implements Serializable {

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
     * Is a side effect of each Drug with an id in this list
     */
    private List<String> _sideEffectOf;

    /**
     * Is an indication of each Drug with an id in this list
     */
    private List<String> _indicationOf;

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
     * Specialized constructor omitting lazy values
     * @param name Symptom's name
     * @param cui Symptom cui
     * @param hpoId Symptom's HPO ID
     */
    public Symptom(String name, String cui, String hpoId,List<String> sideEffectOf, List<String> indicationOf) {
        // Lucene throw an error on empty chain
        // The following prevents this error to be thrown
        _cui = cui != null && !cui.equals("")
                ? cui
                : null;
        _hpoId = hpoId;
        _name = name;
        _sideEffectOf = sideEffectOf;
        _indicationOf = indicationOf;
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
     * @see this._sideEffectOf
     * @return  a list of drug id
     */
    public List<String> getSideEffectOf() {
        return _sideEffectOf;
    }

    /**
     * @see this._indicationOf
     * @return a list of drug id
     */
    public List<String> getIndicationOf() {
        return _indicationOf;
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
     * @param hpoId the hpoId
     */
    public void setHpoId(String hpoId) {
        this._hpoId = hpoId;
    }

    /**
     * cui setter
     * @param cui the symptom cui
     */
    public void setCui(String cui) {
        this._cui = cui;
    }

    /**
     * setter for Side effect of list
     * @param stitchCompondList new list of drugs id
     */
    public void setSideEffectOf(List<String> stitchCompondList) {
        _sideEffectOf = stitchCompondList;
    }

    /**
     * setter for indication of list
     * @param stitchCompondList new list of drugs id
     */
    public void setIndicationOf(List<String> stitchCompondList) {
        _indicationOf = stitchCompondList;
    }

    /**
     * Default string representation
     * @return The symptom's name
     */
    @Override
    public String toString() {
        return getName();
    }

}

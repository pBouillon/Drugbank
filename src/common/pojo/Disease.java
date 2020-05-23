package common.pojo;

import diagnostic.response.IDiagnosableEntity;
import util.Lazy;

import java.io.Serializable;
import java.util.List;

/**
 * Basic POJO holding disease's data
 * POJO built according to https://stackoverflow.com/a/3527340
 */
public class Disease implements IDiagnosableEntity, Serializable {

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
     * Id in the database dbName
     */
    private String _hpoId;

    /**
     * name of the database where the Id have to comes (see HPO)
     */
    private String _hpoDbName;

    /**
     * The hpo sign linked to this disease
     */
    private String _hpoSignId;

    /**
     * Default constructor for parameter-less construction
     */
    public Disease() { }


    public Disease(String name) {
        _name = name;
    }

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


    public Disease(String name, String signId, String hpoID, String hpoDbName) {
        _name = name;
        _hpoSignId = signId;
        _hpoId = hpoID;
        _hpoDbName = hpoDbName;
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
     * getter for the HPOId
     * @return the hpoId
     */
    public String getHpoId() {
        return _hpoId;
    }

    /**
     * getter for the dbName
     * @return the dbName
     */
    public String getHpoDbName() {
        return _hpoDbName;
    }

    /**
     * getter for the hpo sign id
     * @return the sign id
     */
    public String getHpoSignId() {
        return _hpoSignId;
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

    /**
     * setter for the hpoId
     * @param hpoId new hpoId
     */
    public void setHpoId(String hpoId) {
        this._hpoId = hpoId;
    }

    /**
     * setter for the dbName
     * @param hpoDbName db name
     */
    public void setHpoDbName(String hpoDbName) {
        this._hpoDbName = hpoDbName;
    }

    /**
     * setter for the Hpo sign id
     * @param hpoSignId the new hpo sign id
     */
    public void setHpoSignId(String hpoSignId) {
        this._hpoSignId = hpoSignId;
    }

}

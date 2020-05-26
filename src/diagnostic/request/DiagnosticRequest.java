package diagnostic.request;

/**
 * Diagnostic request, holding information on which base the diagnostic
 * @see diagnostic.DiagnosticManager
 */
public class DiagnosticRequest {

    /**
     * Undesirable effect noticed
     */
    private String _undesirableEffect;

    /**
     * Default constructor
     * @param undesirableEffect Undesirable effect(s) to match
     */
    public DiagnosticRequest(String undesirableEffect) {
        _undesirableEffect = undesirableEffect;
    }

    /**
     * Getter for the undesirable effect
     * @return The undesirable effect
     */
    public String getUndesirableEffect() {
        return _undesirableEffect;
    }

    /**
     * Setter for the undesirable effect
     * @param undesirableEffect The new undesirable to set
     */
    public void setUndesirableEffect(String undesirableEffect) {
        _undesirableEffect = undesirableEffect;
    }

}

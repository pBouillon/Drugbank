package diagnostic;

/**
 * Diagnostic request, holding information on which base the diagnostic
 * @see DiagnosticManager
 */
public class DiagnosticRequest {

    /**
     * Undesirable effect noticed
     */
    private String _undesirableEffect;

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

package diagnostic.request;

import java.util.List;

/**
 * Diagnostic request, holding information on which base the diagnostic
 * @see diagnostic.DiagnosticManager
 */
public class DiagnosticRequest {

    /**
     * Undesirable effect noticed
     */
    private List<String> _undesirableEffect;

    /**
     * Getter for the undesirable effect
     * @return The undesirable effect
     */
    public List<String> getUndesirableEffect() {
        return _undesirableEffect;
    }

    /**
     * Setter for the undesirable effect
     * @param undesirableEffect The new undesirable to set
     */
    public void setUndesirableEffect(List<String> undesirableEffect) {
        _undesirableEffect = undesirableEffect;
    }

}

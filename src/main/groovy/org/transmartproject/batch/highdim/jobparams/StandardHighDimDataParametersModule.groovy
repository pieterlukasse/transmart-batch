package org.transmartproject.batch.highdim.jobparams

import com.google.common.collect.ImmutableSet
import org.transmartproject.batch.startup.ExternalJobParametersInternalInterface
import org.transmartproject.batch.startup.ExternalJobParametersModule
import org.transmartproject.batch.startup.InvalidParametersFileException

/**
 * Defines the parameters necessary for standard data file processing.
 */
class StandardHighDimDataParametersModule
        implements ExternalJobParametersModule {

    /* just for input; will be deleted after munging */
    public final static String DATA_FILE_PREFIX = 'DATA_FILE_PREFIX' /* not sure why it's called this */

    public final static String DATA_FILE = 'DATA_FILE' /* final destination for DATA_FILE_PREFIX */
    public final static String DATA_TYPE = 'DATA_TYPE'
    public final static String LOG_BASE = 'LOG_BASE'
    public final static String ALLOW_MISSING_ANNOTATIONS = 'ALLOW_MISSING_ANNOTATIONS'

    Set<String> supportedParameters = ImmutableSet.of(
            DATA_FILE_PREFIX,
            DATA_FILE,
            DATA_TYPE,
            LOG_BASE,
            ALLOW_MISSING_ANNOTATIONS,
    )

    void validate(ExternalJobParametersInternalInterface ejp)
            throws InvalidParametersFileException {
        if (ejp[LOG_BASE] == null) {
            ejp[LOG_BASE] = 2
        } else if (!ejp[LOG_BASE].isLong() || ejp[LOG_BASE] as Long != 2) {
            throw new InvalidParametersFileException("$LOG_BASE must be 2")
        }

        ejp.mandatory DATA_TYPE
        if (ejp[DATA_TYPE] != 'R') {
            throw new InvalidParametersFileException("$DATA_TYPE must be 'R'")
        }

        if (ejp[DATA_FILE_PREFIX] && ejp[DATA_FILE]) {
            throw new InvalidParametersFileException(
                    "Can't set $DATA_FILE_PREFIX and $DATA_FILE simultaneously")
        }
        if (ejp[DATA_FILE_PREFIX] == null && ejp[DATA_FILE] == null) {
            throw new InvalidParametersFileException(
                    "Either $DATA_FILE_PREFIX or $DATA_FILE must be set")
        }
    }

    void munge(ExternalJobParametersInternalInterface ejp)
            throws InvalidParametersFileException {
        if (ejp[DATA_FILE_PREFIX]) {
            ejp[DATA_FILE] = ejp[DATA_FILE_PREFIX]
            ejp[DATA_FILE_PREFIX] = null
        }

        ejp[DATA_FILE] = ejp.convertRelativePath DATA_FILE

        if (ejp[ALLOW_MISSING_ANNOTATIONS] != 'Y') {
            ejp[ALLOW_MISSING_ANNOTATIONS] = 'N'
        }
    }
}

package org.transmartproject.batch.highdim.mrna.data

import groovy.transform.TypeChecked
import org.transmartproject.batch.highdim.jobparams.StandardAssayParametersModule
import org.transmartproject.batch.highdim.jobparams.StandardHighDimDataParametersModule
import org.transmartproject.batch.startup.ExternalJobParametersModule
import org.transmartproject.batch.startup.JobSpecification
import org.transmartproject.batch.startup.StudyJobParametersModule

/**
 * External parameters for main mrna data.
 */
@TypeChecked
class MrnaDataJobSpecification implements JobSpecification {

    final List<? extends ExternalJobParametersModule> jobParametersModules = [
            new StudyJobParametersModule(),
            new StandardAssayParametersModule('MRNA'),
            new StandardHighDimDataParametersModule()
    ]

    final Class<?> jobPath = MrnaDataJobConfiguration
}

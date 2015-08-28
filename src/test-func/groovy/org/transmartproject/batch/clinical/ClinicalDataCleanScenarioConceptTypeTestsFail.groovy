package org.transmartproject.batch.clinical

import org.junit.AfterClass
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.batch.core.repository.JobRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.transmartproject.batch.beans.GenericFunctionalTestConfiguration
import org.transmartproject.batch.db.TableTruncator
import org.transmartproject.batch.startup.RunJob
import org.transmartproject.batch.support.TableLists
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.*

/**
 * Test loading non-numerical data that's mapped as numerical data.
 */
@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration(classes = GenericFunctionalTestConfiguration)
class ClinicalDataCleanScenarioConceptTypeTestsFail {

    public static final String STUDY_ID = 'GSE8581_CONCEPT_TYPES_FAIL'

    @Autowired
    JobRepository repository

    @AfterClass
    static void cleanDatabase() {
        new AnnotationConfigApplicationContext(
                GenericFunctionalTestConfiguration).getBean(TableTruncator).
                truncate(TableLists.CLINICAL_TABLES + 'ts_batch.batch_job_instance')
    }


    @Test
    void testFailLoadingData() {
        def params = ['-p', 'studies/' + STUDY_ID + '/clinical.params',]

        def runJob = RunJob.createInstance(*params)
        def intResult = runJob.run()
        assertThat 'execution is unsuccessful', intResult, is(1)

        String exitDescription = repository.getLastJobExecution(
                ClinicalDataLoadJobConfiguration.JOB_NAME,
                runJob.finalJobParameters).exitStatus.exitDescription

        assertThat exitDescription, containsString('numerical, but got value \'test1\'.')
    }
}

package tests;

import java.util.Collections;
import java.util.Map;

import org.ow2.parscript.PARScriptFactory;
import org.ow2.proactive.scheduler.task.SchedulerVars;
import org.ow2.proactive.scripting.SimpleScript;
import org.ow2.proactive.scripting.TaskScript;
import org.ow2.proactive.scripting.helper.progress.ProgressFile;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;


/**
 * Test set_progress(x) method in R script.
 *
 * @author Activeeon Team
 */
public class TestProgress {

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    @Test
    public void test() throws Exception {
        int expectedProgress = 50;
        String rScript = "set_progress(" + expectedProgress + "); zebi=10;";

        String progressFilePath = tmpFolder.newFile().getAbsolutePath();
        Map<String, Object> aBindings = Collections.singletonMap(
                SchedulerVars.PA_TASK_PROGRESS_FILE.toString(), (Object) progressFilePath);
        SimpleScript ss = new SimpleScript(rScript, PARScriptFactory.ENGINE_NAME);
        TaskScript taskScript = new TaskScript(ss);
        taskScript.execute(aBindings, System.out, System.err);

        assertEquals("The progress is incorrect, it seems the engine doesn't transmit " +
          " the progress to the script as expected", expectedProgress, ProgressFile.getProgress(progressFilePath));
    }
}

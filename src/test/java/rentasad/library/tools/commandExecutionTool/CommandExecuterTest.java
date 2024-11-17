package rentasad.library.tools.commandExecutionTool;

import org.junit.jupiter.api.Test;
import rentasad.library.tools.commandExecutionTool.utils.JavaPathUtil;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.HashMap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class CommandExecuterTest
{

	@Test
	public void testGetOutputStringFromOutputStream() throws IOException
	{
		String testString = "Dies ist ein Teststring";
		InputStream inputStream = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));

		String result = CommandExecuter.getOutputStringFromOutputStream(inputStream);

		assertEquals(testString, result);
	}
    @Test
    public void testStartJobName() throws IOException, InterruptedException {
        Map<String, String> jobConfigMap = new HashMap<>();
		String jdkExecutivePath = JavaPathUtil.getJavaExecutablePath() + ".exe";
        String relativePath= "src/test/java/rentasad/library/tools/commandExecutionTool";
		String absolutePath = Paths.get(relativePath).toAbsolutePath().toString();
		jobConfigMap.put(CommandExecuter.PARAMETER_JVM_PATH, jdkExecutivePath);
        jobConfigMap.put(CommandExecuter.PARAMETER_JAR_PATH, absolutePath+ "/HelloWorld.jar");
        jobConfigMap.put(CommandExecuter.PARAMETER_START_CLASS, "rentasad.library.tools.commandExecutionTool.HelloWorld");
        jobConfigMap.put(CommandExecuter.PARAMETER_START_DIR, absolutePath);
        jobConfigMap.put(CommandExecuter.PARAMETER_VM_ARGUMENTS, "-Dexample=true");
        jobConfigMap.put(CommandExecuter.PARAMETER_RUNNING_ARGUMENTS, "arg1 arg2");

        Process process = CommandExecuter.startJobName(jobConfigMap);

        assertNotNull(process);
    }

    @Test
    public void testStartJobNameFileNotFound() {
        Map<String, String> jobConfigMap = new HashMap<>();
        jobConfigMap.put(CommandExecuter.PARAMETER_JVM_PATH, JavaPathUtil.getJavaExecutablePath());
        jobConfigMap.put(CommandExecuter.PARAMETER_JAR_PATH, "C:\\path\\to\\app.jar");
        jobConfigMap.put(CommandExecuter.PARAMETER_START_CLASS, "com.example.Main");
        jobConfigMap.put(CommandExecuter.PARAMETER_START_DIR, "C:\\start\\dir");
        jobConfigMap.put(CommandExecuter.PARAMETER_VM_ARGUMENTS, "-Dexample=true");
        jobConfigMap.put(CommandExecuter.PARAMETER_RUNNING_ARGUMENTS, "arg1 arg2");

        assertThrows(FileNotFoundException.class, () -> {
            CommandExecuter.startJobName(jobConfigMap);
        });
    }
}
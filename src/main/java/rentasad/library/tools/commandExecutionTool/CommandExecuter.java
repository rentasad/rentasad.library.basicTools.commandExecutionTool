package rentasad.library.tools.commandExecutionTool;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * Gustini GmbH (2015)
 * Creation: 22.01.2015
 * Rentasad Library
 * rentasad.lib.tools
 *
 * @author Matthias Staud
 *
 *
 *         Description:
 *         fuehrt ein uebergebenes DOS-Komando aus
 *         Ohne Ausgabe wiederzugeben.
 */
public class CommandExecuter
{
    public static final String PARAMETER_JOB_NAME = "JOB_NAME";
    public static final String PARAMETER_START_DIR = "START_DIR";
    public static final String PARAMETER_JAR_PATH = "JAR_PATH";
    public static final String PARAMETER_JVM_PATH = "JVM_PATH";
    public static final String PARAMETER_VM_ARGUMENTS = "VM_ARGUMENTS";
    public static final String PARAMETER_START_CLASS = "START_CLASS";
    public static final String PARAMETER_CRON_EXPRESSION = "CRON_EXPRESSION";
    public static final String PARAMETER_RUNNING_ARGUMENTS = "RUNNING_ARGUMENTS";
    /**
     *
     * Description: Execute the given command
     *
     * @param command
     *            Das auszufuehrende CVS-Kommando
     *            Creation: 04.03.2016 by mst
     */
    public static synchronized void connect(String command)
    {
        try
        {
            System.out.println(command);
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            process.destroy();

        } catch (IOException e)
        {
            System.out.println("CVSCommandExecuter.java IOException :" + e);
            e.printStackTrace();
            // return null;
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Starts a job by executing a Java process using the provided job configuration.
     * This method expects specific parameters in the job configuration map, including
     * paths to the JVM, JAR file, starting class, and other optional arguments.
     *
     * @param jobConfigMap a map containing the job configuration parameters.
     *                     Expected keys are "JVM_PATH", "JAR_PATH", "START_CLASS",
     *                     "START_DIR", "VM_ARGUMENTS", and "RUNNING_ARGUMENTS".
     * @return the process object representing the executed job.
     * @throws IOException if an I/O error occurs during process execution.
     * @throws InterruptedException if the current thread is interrupted while waiting for the process.
     */

    public static Process startJobName(Map<String, String> jobConfigMap) throws IOException, InterruptedException {
        File javaRuntimeExecuteFile = new File(jobConfigMap.get(PARAMETER_JVM_PATH));
        if (!javaRuntimeExecuteFile.exists()) {
            throw new FileNotFoundException(jobConfigMap.get(PARAMETER_JVM_PATH));
        }

        String jvmPath = jobConfigMap.get(PARAMETER_JVM_PATH);
        String jarFilePath = jobConfigMap.get(PARAMETER_JAR_PATH);
        String startClass = jobConfigMap.get(PARAMETER_START_CLASS);
        String startDir = jobConfigMap.get(PARAMETER_START_DIR);
        String vmArguments = jobConfigMap.get(PARAMETER_VM_ARGUMENTS);
        String runningArguments = jobConfigMap.get(PARAMETER_RUNNING_ARGUMENTS);

        String command = "cmd /c \"" + jvmPath + "\" " + vmArguments + " -classpath " + jarFilePath + " " + startClass + " " + runningArguments;
        System.out.println(command);
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(command, null, new File(startDir));

        // Create threads to read the standard output and error streams
        Thread outputThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread errorThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.err.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Start the threads
        outputThread.start();
        errorThread.start();

        proc.waitFor();
        outputThread.join(); // Ensure the output thread is done
        errorThread.join(); // Ensure the error thread is done
        proc.destroy();
        return proc;
    }
    /**
     *
     * Description:Konvertiert einen InputStream zu einem String
     *
     * @param inputStream
     * @return
     * Creation: 10.03.2016 by mst
     * @throws IOException
     */
    public static String getOutputStringFromOutputStream(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
//    OLD IMPLEMENTATION
    //    public static String getOutputStringFromOutputStream(InputStream inputStream) throws IOException
//    {
//        StringWriter writer = new StringWriter();
//        IOUtils.copy(inputStream, writer);
//        return writer.toString();
//
//    }
}

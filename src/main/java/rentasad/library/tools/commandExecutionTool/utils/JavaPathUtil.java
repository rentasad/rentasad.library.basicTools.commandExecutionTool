package rentasad.library.tools.commandExecutionTool.utils;

import java.io.File;

/**
 * Utility class for retrieving the path to the Java executable.
 */
public class JavaPathUtil
{
	private JavaPathUtil(){};
	/**
	 * Retrieves the path to the Java executable.
	 *
	 * @return the full path to the Java executable as a String.
	 */
	public static String getJavaExecutablePath()

	{
		return System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
	}
}

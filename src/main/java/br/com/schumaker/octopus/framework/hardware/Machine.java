package br.com.schumaker.octopus.framework.hardware;

import br.com.schumaker.octopus.framework.exception.OctopusException;

import java.net.InetAddress;

/**
 * The Machine class provides utility methods to retrieve information about the machine and environment
 * where the application is running. It includes methods to get the number of processors, host name, IP address,
 * installation directory, operating system details, Java details, and the logged-in user_name.
 *
 * @see OctopusException
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class Machine {

    /**
     * Returns the number of available processors of the machine.
     *
     * @return the number of available processors.
     */
    public static int getNumberProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Returns the host name of the machine.
     *
     * @return the host name of the machine.
     * @throws OctopusException if an error occurs while retrieving the host name.
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            throw new OctopusException(e.getMessage());
        }
    }

    /**
     * Returns the IP address of the machine.
     *
     * @return the IP address of the machine.
     * @throws OctopusException if an error occurs while retrieving the IP address.
     */
    public static String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            throw new OctopusException(e.getMessage());
        }
    }

    /**
     * Returns the installation directory of the application.
     *
     * @return the installation directory of the application.
     */
    public static String getInstallDir() {
        return System.getProperty("user.dir");
    }

    /**
     * Returns the name of the operating system.
     *
     * @return the name of the operating system.
     */
    public static String getOsName() {
        return System.getProperty("os.name");
    }

    /**
     * Returns the version of the operating system.
     *
     * @return the version of the operating system.
     */
    public static String getOsVersion() {
        return System.getProperty("os.version");
    }

    /**
     * Returns the architecture of the operating system.
     *
     * @return the architecture of the operating system.
     */
    public static String getOsArch() {
        return System.getProperty("os.arch");
    }

    /**
     * Returns the version of Java.
     *
     * @return the version of Java.
     */
    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * Returns the vendor of the Java runtime.
     *
     * @return the vendor of the Java runtime.
     */
    public static String getJavaVendor() {
        return System.getProperty("java.vendor");
    }

    /**
     * Returns the name of the Java Virtual Machine.
     *
     * @return the name of the Java Virtual Machine.
     */
    public static String getJvmName() {
        return System.getProperty("java.vm.name");
    }

    /**
     * Returns the name of the logged-in user.
     *
     * @return the name of the logged-in user.
     */
    public static String getLoggedUserName() {
        return System.getProperty("user.name");
    }
}

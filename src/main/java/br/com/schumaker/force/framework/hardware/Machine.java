package br.com.schumaker.force.framework.hardware;

import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.AppProperties;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;

/**
 * The Machine class provides utility methods to retrieve information about the machine and environment
 * where the application is running. It includes methods to get the number of processors, host name, IP address,
 * installation directory, operating system details, Java details, and the logged-in user_name.
 *
 * @see ForceException
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class Machine {
    private static final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

    /**
     * Returns the number of available processors of the machine.
     *
     * @return the number of available processors.
     */
    public static Integer getNumberProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Returns the host name of the machine.
     *
     * @return the host name of the machine.
     * @throws ForceException if an error occurs while retrieving the host name.
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception ex) {
            throw new ForceException(ex.getMessage());
        }
    }

    /**
     * Returns the IP address of the machine.
     *
     * @return the IP address of the machine.
     * @throws ForceException if an error occurs while retrieving the IP address.
     */
    public static String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception ex) {
            throw new ForceException(ex.getMessage());
        }
    }

    /**
     * Returns the total memory of the machine.
     *
     * @return the total memory of the machine.
     */
    public static long getTotalMemory() {
        return ((com.sun.management.OperatingSystemMXBean) osBean).getTotalMemorySize();
    }

    /**
     * Returns the free memory of the machine.
     *
     * @return the free memory of the machine.
     */
    public static long getJvmTotalMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory(); // JVM total memory
    }

    /**
     * Returns the used memory of the machine.
     *
     * @return the used memory of the machine.
     */
    public static long getJvmUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory(); // JVM total memory
        long freeMemory = runtime.freeMemory();   // JVM free memory
        return totalMemory - freeMemory;          // JVM used memory
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

    /**
     * Returns the maximum number of threads that can be created.
     *
     * @return the maximum number of threads.
     */
    public static Integer defaultMaxThreads() {
        return Math.max(1, getNumberProcessors() * 2);
    }

    /**
     * Returns the version of Force.
     *
     * @return Force version.
     */
    public static String getForceVersion() {
        return AppProperties.FMK_VERSION;
    }
}

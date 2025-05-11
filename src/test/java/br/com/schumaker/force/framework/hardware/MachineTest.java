package br.com.schumaker.force.framework.hardware;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The MachineTest class is responsible for testing the Machine class.
 * It includes tests for various methods that retrieve information about the machine and environment.
 *
 * @see Machine
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class MachineTest {

    @Test
    public void testGetNumberProcessors() {
        int numberOfProcessors = Machine.getNumberProcessors();
        assertTrue(numberOfProcessors > 0, "Number of processors should be greater than 0");
    }

    @Test
    public void testGetHostName() {
        String hostName = Machine.getHostName();
        assertTrue(hostName != null && !hostName.isEmpty(), "Host name should not be null or empty");
    }

    @Test
    public void testGetIp() {
        String ip = Machine.getIp();
        assertTrue(ip != null && !ip.isEmpty(), "IP address should not be null or empty");
    }

    @Test
    public void testGetInstallDir() {
        String installDir = Machine.getInstallDir();
        assertTrue(installDir != null && !installDir.isEmpty(), "Installation directory should not be null or empty");
    }

    @Test
    public void testGetOsName() {
        String os = Machine.getOsName();
        assertTrue(os != null && !os.isEmpty(), "Operating system should not be null or empty");
    }

    @Test
    public void testGetOsVersion() {
        String osVersion = Machine.getOsVersion();
        assertTrue(osVersion != null && !osVersion.isEmpty(), "Operating system version should not be null or empty");
    }

    @Test
    public void testGetJavaVersion() {
        String javaVersion = Machine.getJavaVersion();
        assertTrue(javaVersion != null && !javaVersion.isEmpty(), "Java version should not be null or empty");
    }

    @Test
    public void testGetJavaVendor() {
        String javaVendor = Machine.getJavaVendor();
        assertTrue(javaVendor != null && !javaVendor.isEmpty(), "Java vendor should not be null or empty");
    }

    @Test
    public void testGetJvmName() {
        String javaVmName = Machine.getJvmName();
        assertTrue(javaVmName != null && !javaVmName.isEmpty(), "Java VM name should not be null or empty");
    }

    @Test
    public void testGetLoggedUserName() {
        String javaVmVersion = Machine.getLoggedUserName();
        assertTrue(javaVmVersion != null && !javaVmVersion.isEmpty(), "Logged user name should not be null or empty");
    }
}

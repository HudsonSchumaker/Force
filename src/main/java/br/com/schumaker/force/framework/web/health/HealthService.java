package br.com.schumaker.force.framework.web.health;

import br.com.schumaker.force.framework.hardware.Machine;
import br.com.schumaker.force.framework.ioc.annotations.bean.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Service class to provide health information about the application.
 *
 * @see HealthController
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Service
public class HealthService {

    /**
     * Returns the health status of the application.
     *
     * @return a string representing the health status.
     */
    public String getHealthStatus() {
        return "UP";
    }

    /**
     * Returns detailed health information about the application.
     *
     * @return a HealthInfoDTO object containing health information.
     */
    public HealthInfoDTO getHealthInfo() {
        return new HealthInfoDTO(
                Machine.getHostName(),
                Machine.getIp(),
                Machine.getForceVersion(),
                Machine.getJvmName(),
                Machine.getJavaVendor(),
                Machine.getJavaVersion(),
                Machine.getOsName(),
                Machine.getOsVersion(),
                Machine.getOsArch(),
                Machine.getNumberProcessors(),
                bytesToGigabytes(Machine.getTotalMemory()) + " GB",
                bytesToMegabytes(Machine.getJvmTotalMemory()) + " MB",
                bytesToMegabytes(Machine.getJvmUsedMemory()) + " MB"
        );
    }

    /**
     * Converts bytes to Megabytes.
     *
     * @param bytes the value in bytes.
     * @return the value in Megabytes.
     */
    private double bytesToMegabytes(long bytes) {
        BigDecimal gigabytes = new BigDecimal(bytes).divide(new BigDecimal(1024 * 1024), 2, RoundingMode.HALF_UP);
        return gigabytes.doubleValue();
    }

    /**
     * Converts bytes to Gigabytes.
     *
     * @param bytes the value in bytes.
     * @return the value in Gigabytes.
     */
    private double bytesToGigabytes(long bytes) {
        BigDecimal gigabytes = new BigDecimal(bytes).divide(new BigDecimal(1024 * 1024 * 1024), 2, RoundingMode.HALF_UP);
        return gigabytes.doubleValue();
    }
}
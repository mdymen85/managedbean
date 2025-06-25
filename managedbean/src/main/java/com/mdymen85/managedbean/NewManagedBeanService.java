package com.mdymen85.managedbean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

@Service
@ManagedResource(
    objectName = "com.mdymen85.managedbean:name=NewManagedBeanService,type=Service",
    description = "Manages and monitors key application metrics."
)
public class NewManagedBeanService {

    private final AtomicInteger processedEvents = new AtomicInteger(0);
    private String systemMode = "NORMAL"; // Can be "NORMAL", "MAINTENANCE", "READ_ONLY"
    private String lastConfigurationUpdate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    private boolean featureToggleEnabled = false;

    // --- Managed Attributes ---

    @ManagedAttribute(description = "The current number of events processed by the system.")
    public int getProcessedEvents() {
        return processedEvents.get();
    }

    // Note: No setter for processedEvents, so it's read-only in JConsole.

    @ManagedAttribute(description = "The current operating mode of the system (e.g., NORMAL, MAINTENANCE).")
    public String getSystemMode() {
        return systemMode;
    }

    @ManagedAttribute(description = "Set the operating mode of the system.")
    public void setSystemMode(String systemMode) {
        if (systemMode != null && (systemMode.equals("NORMAL") || systemMode.equals("MAINTENANCE") || systemMode.equals("READ_ONLY"))) {
            this.systemMode = systemMode;
            this.lastConfigurationUpdate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            System.out.println("System mode changed to: " + systemMode);
        } else {
            System.err.println("Invalid system mode: " + systemMode + ". Must be NORMAL, MAINTENANCE, or READ_ONLY.");
            // In a real app, you might throw an IllegalArgumentException
        }
    }

    @ManagedAttribute(description = "Timestamp of the last configuration change.")
    public String getLastConfigurationUpdate() {
        return lastConfigurationUpdate;
    }

    @ManagedAttribute(description = "Is Feature X currently enabled?")
    public boolean isFeatureToggleEnabled() {
        System.out.println("is feature toggle enabled " + featureToggleEnabled);
        return featureToggleEnabled;
    }

    @ManagedAttribute(description = "Toggle Feature X on/off.")
    public void setFeatureToggleEnabled(boolean featureToggleEnabled) {
        this.featureToggleEnabled = featureToggleEnabled;
        System.out.println("Feature Toggle X set to: " + featureToggleEnabled);
    }

    // --- Managed Operations ---

    @ManagedOperation(description = "Increment the processed event counter by a specified amount.")
    public int incrementProcessedEvents(int amount) {
        if (amount > 0) {
            System.out.println("amount " + amount);
            return processedEvents.addAndGet(amount);
        }
        System.out.println("amount " + amount);
        return processedEvents.get();
    }

    @ManagedOperation(description = "Reset the processed event counter to zero.")
    public void resetProcessedEvents() {
        processedEvents.set(0);
        System.out.println("Processed events counter reset.");
    }

    @ManagedOperation(description = "Perform a system health check and return a status report.")
    public String performHealthCheck() {
        // In a real application, this would run diagnostics
        boolean dbStatus = true; // simulate check
        boolean apiStatus = true; // simulate check
        String report = "Health Check Report:\n";
        report += "  Database Connection: " + (dbStatus ? "OK" : "FAILED") + "\n";
        report += "  External API Access: " + (apiStatus ? "OK" : "FAILED") + "\n";
        report += "  Current Mode: " + getSystemMode() + "\n";
        return report;
    }
}

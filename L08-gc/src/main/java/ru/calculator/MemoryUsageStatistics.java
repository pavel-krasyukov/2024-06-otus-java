/**
 * @author administrator on 15.10.2024.
 */
package ru.calculator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MemoryUsageStatistics {
    private String gcName;
    private long heapSize;
    private double timeProc;

    public MemoryUsageStatistics() {
	super();
    }
    @JsonCreator
    public MemoryUsageStatistics(
		    @JsonProperty("gcName")
		    String gcName,
		    @JsonProperty("heapSize")
		    long heapSize,
		    @JsonProperty("timeProc")
		    Double timeProc) {
	this.gcName = gcName;
	this.heapSize = heapSize;
	this.timeProc = timeProc;
    }

    public String getGcName() {
	return gcName;
    }

    public void setGcName(String gcName) {
	this.gcName = gcName;
    }

    public long getHeapSize() {
	return heapSize;
    }

    public void setHeapSize(long heapSize) {
	this.heapSize = heapSize;
    }

    public double getTimeProc() {
	return timeProc;
    }

    public void setTimeProc(double timeProc) {
	this.timeProc = timeProc;
    }

}

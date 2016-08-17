package org.baderlab.st.internal;


public class MemoryStatus {

	private static final Integer MB = 1024 * 1024;
	
	private final long usedMemory;
	private final long freeMemory;
	private final long maxMemory;
	private final long totalMemory;
	
	public MemoryStatus() {
	    Runtime runtime = Runtime.getRuntime();
	    freeMemory  = runtime.freeMemory()  / MB;
	    totalMemory = runtime.totalMemory() / MB;
	    maxMemory   = runtime.maxMemory()   / MB;
	    usedMemory  = (runtime.totalMemory() - runtime.freeMemory()) / MB;
	}

	public long getUsedMemory() {
		return usedMemory;
	}

	public long getFreeMemory() {
		return freeMemory;
	}

	public long getTotalMemory() {
		return totalMemory;
	}

	public long getMaxMemory() {
		return maxMemory;
	}

    @Override
    public String toString() {
        return "MemoryStatus [usedMemory=" + usedMemory + ", freeMemory=" + freeMemory + ", maxMemory=" + maxMemory
                + ", totalMemory=" + totalMemory + "]";
    }

	
}
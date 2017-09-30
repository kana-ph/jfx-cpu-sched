package ph.kana.cpusched.job;

public class CpuJob {

	private int jobNumber;
	private int arrivalTime;
	private int burstTime;

	public static CpuJob build(int jobNumber, int arrivalTime, int burstTime) {
		CpuJob job = new CpuJob();
		job.jobNumber = jobNumber;
		job.arrivalTime = arrivalTime;
		job.burstTime = burstTime;
		return job;
	}

	public int getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(int jobNumber) {
		this.jobNumber = jobNumber;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getBurstTime() {
		return burstTime;
	}

	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}
}

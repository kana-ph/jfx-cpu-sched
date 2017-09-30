package ph.kana.cpusched.algorithm;

import ph.kana.cpusched.job.CpuJob;

import java.util.List;

public abstract class AlgorithmSolver {

	protected final boolean preEmptive;

	protected AlgorithmSolver(boolean preEmptive) {
		this.preEmptive = preEmptive;
	}

	public abstract List<CpuJob> solve(List<CpuJob> jobs);

	protected CpuJob createJobSlice(CpuJob job, int currentTime, int burstAmount) {
		return CpuJob.build(job.getJobNumber(), currentTime, burstAmount);
	}
}

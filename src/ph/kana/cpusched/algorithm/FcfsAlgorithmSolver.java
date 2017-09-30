package ph.kana.cpusched.algorithm;

import ph.kana.cpusched.job.CpuJob;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FcfsAlgorithmSolver extends AlgorithmSolver {

	public FcfsAlgorithmSolver() {
		super(false);
	}

	@Override
	public List<CpuJob> solve(List<CpuJob> jobs) {
		List<CpuJob> scheduledJobs = new ArrayList<>();

		List<CpuJob> inputJobs = new ArrayList<>(jobs);
		Collections.sort(inputJobs, Comparator.comparingInt(CpuJob::getArrivalTime));

		int currentTime = 0;
		for (CpuJob job : inputJobs) {
			scheduledJobs.add(createJobSlice(job, currentTime, job.getBurstTime()));
			currentTime += job.getBurstTime();
		}


		return scheduledJobs;
	}
}

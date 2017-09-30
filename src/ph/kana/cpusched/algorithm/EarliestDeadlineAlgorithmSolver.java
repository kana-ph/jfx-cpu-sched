package ph.kana.cpusched.algorithm;

import ph.kana.cpusched.job.CpuJob;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EarliestDeadlineAlgorithmSolver extends AlgorithmSolver {

	public EarliestDeadlineAlgorithmSolver() {
		super(false);
	}

	@Override
	public List<CpuJob> solve(List<CpuJob> jobs) {
		List<CpuJob> scheduledJobs = new ArrayList<>();
		List<CpuJob> inputJobs = new ArrayList<>(jobs);

		int currentTime = 0;
		while (!inputJobs.isEmpty()) {
			CpuJob job = fetchEarliestDeadlineJob(currentTime, inputJobs);
			scheduledJobs.add(createJobSlice(job, currentTime, job.getBurstTime()));

			currentTime += job.getBurstTime();
			inputJobs.remove(job);
		}

		return scheduledJobs;
	}

	private CpuJob fetchEarliestDeadlineJob(int currentTime, List<CpuJob> jobs) {
		return jobs.stream()
			.filter(job -> job.getArrivalTime() <= currentTime)
			.sorted(Comparator.comparingInt(CpuJob::getBurstTime))
			.findFirst()
			.orElse(null);
	}
}

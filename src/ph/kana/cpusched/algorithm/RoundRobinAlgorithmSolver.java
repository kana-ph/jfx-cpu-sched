package ph.kana.cpusched.algorithm;

import ph.kana.cpusched.job.CpuJob;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RoundRobinAlgorithmSolver extends AlgorithmSolver {

	private int quantum = 3;

	RoundRobinAlgorithmSolver() {
		super(true);
	}

	@Override
	public List<CpuJob> solve(List<CpuJob> jobs) {
		List<CpuJob> scheduledJobs = new ArrayList<>();
		List<CpuJob> inputJobs = new ArrayList<>(jobs);

		int currentTime = 0;
		while(!inputJobs.isEmpty()) {
			List<CpuJob> arrivedJobs = pickArrivedJobs(currentTime, inputJobs);
			for (CpuJob job : arrivedJobs) {
				int jobBurst = job.getBurstTime();
				int currentBurst = (jobBurst <= quantum)? jobBurst : quantum;

				scheduledJobs.add(createJobSlice(job, currentTime, currentBurst));
				currentTime += currentBurst;
				job.setArrivalTime(currentTime);
				job.setBurstTime(jobBurst - currentBurst);
			}
			returnUnfinishedJobsToInput(arrivedJobs, inputJobs);
		}

		return scheduledJobs;
	}

	private List<CpuJob> pickArrivedJobs(int currentTime, List<CpuJob> jobs) {
		return jobs.stream()
			.filter(job -> job.getArrivalTime() <= currentTime)
			.sorted(Comparator.comparingInt(CpuJob::getArrivalTime))
			.collect(Collectors.toList());
	}

	private void returnUnfinishedJobsToInput(List<CpuJob> arrivedJobs, List<CpuJob> inputJobs) {
		List<CpuJob> unfinishedJobs = arrivedJobs.stream()
			.filter(job -> job.getBurstTime() > 0)
			.collect(Collectors.toList());
		inputJobs.removeAll(arrivedJobs);
		inputJobs.addAll(unfinishedJobs);
	}
}

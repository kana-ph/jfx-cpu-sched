package ph.kana.cpusched.algorithm;

public enum CpuSchedulingAlgorithm {
	FCFS("First Come First Serve", new FcfsAlgorithmSolver()),
	EDF("Earliest Deadline First", new EarliestDeadlineAlgorithmSolver()),
	ROUND_ROBIN("Round Robin (q=3)", new RoundRobinAlgorithmSolver());

	private String naturalName;
	private AlgorithmSolver solver;

	CpuSchedulingAlgorithm(String naturalName, AlgorithmSolver solver) {
		this.naturalName = naturalName;
		this.solver = solver;
	}

	public String getNaturalName() {
		return naturalName;
	}

	public AlgorithmSolver getSolver() {
		return solver;
	}

	@Override
	public String toString() {
		return naturalName;
	}
}

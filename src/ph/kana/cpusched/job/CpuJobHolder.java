package ph.kana.cpusched.job;

import javafx.collections.FXCollections;

import java.util.List;

public class CpuJobHolder {

	public static List<CpuJob> jobs = FXCollections.observableArrayList();

	public static void add(CpuJob job) {
		jobs.add(job);
	}
}
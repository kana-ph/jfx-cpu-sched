package ph.kana.cpusched;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import ph.kana.cpusched.algorithm.AlgorithmSolver;
import ph.kana.cpusched.algorithm.CpuSchedulingAlgorithm;
import ph.kana.cpusched.job.CpuJob;
import ph.kana.cpusched.job.CpuJobHolder;
import ph.kana.cpusched.job.JobNumberGenerator;

import java.util.List;

public class MainFormController {

	@FXML
	TextField arrivalTimeTextField;

	@FXML
	TextField burstTimeTextField;

	@FXML
	ComboBox<CpuSchedulingAlgorithm> algorithmChoice;

	@FXML
	TableView<CpuJob> jobsTable;

	@FXML
	Pane ganttChartPane;

	@FXML
	Label ganttChartMessage;

	public void initialize() {
		initializeAlgorithmChoices();
		initializeTableCellFactories();
	}

	@FXML
	public void addJobToTable() {
		int jobNumber = JobNumberGenerator.next();
		String arrivalTime = arrivalTimeTextField.getText();
		String burstTime = burstTimeTextField.getText();

		CpuJob newJob = CpuJob.build(jobNumber, Integer.parseInt(arrivalTime), Integer.parseInt(burstTime));
		CpuJobHolder.add(newJob);
	}

	@FXML
	public void calculateCpuSchedule() {
		AlgorithmSolver solver = algorithmChoice.getSelectionModel()
			.getSelectedItem()
			.getSolver();
		List<CpuJob> scheduledJobs = solver.solve(CpuJobHolder.jobs);
		displayToGanttChart(scheduledJobs);
	}

	private void initializeAlgorithmChoices() {
		algorithmChoice.getItems().addAll(CpuSchedulingAlgorithm.values());
		algorithmChoice.getSelectionModel()
			.select(CpuSchedulingAlgorithm.FCFS);
	}

	private void initializeTableCellFactories() {
		TableColumn<CpuJob, Integer> jobNumberColumn = new TableColumn<>("Job #");
		jobNumberColumn.setCellValueFactory(new PropertyValueFactory<>("jobNumber"));

		TableColumn<CpuJob, Integer> arrivalTimeColumn = new TableColumn<>("Arrival Time");
		arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

		TableColumn<CpuJob, Integer> burstTimeColumn = new TableColumn<>("Burst Time");
		burstTimeColumn.setCellValueFactory(new PropertyValueFactory<>("burstTime"));

		jobsTable.getColumns().addAll(jobNumberColumn, arrivalTimeColumn, burstTimeColumn);
		jobsTable.setItems((ObservableList<CpuJob>) CpuJobHolder.jobs);
	}

	private void displayToGanttChart(List<CpuJob> scheduledJobs) {
		if (scheduledJobs.isEmpty()) {
			ganttChartMessage.setVisible(true);
			return;
		}
		ganttChartMessage.setVisible(false);

		List<Node> blocks = ganttChartPane.getChildren();
		blocks.clear();

		for (CpuJob job : scheduledJobs) {
			AnchorPane jobPane = new AnchorPane();
			List<Node> elements = jobPane.getChildren();

			Label jobNumberLabel = new Label("Job #" + job.getJobNumber());
			Label arrivalTimeLabel = new Label("Arrival Time:");
			Label burstTimeLabel = new Label("Burst Time: ");

			Label arrivalTimeValueLabel = new Label(Integer.toString(job.getArrivalTime()));
			Label burstTimeValueLabel = new Label(Integer.toString(job.getBurstTime()));

			elements.add(jobNumberLabel);
			AnchorPane.setLeftAnchor(jobNumberLabel, 5.0);
			AnchorPane.setTopAnchor(jobNumberLabel, 5.0);

			elements.add(arrivalTimeLabel);
			AnchorPane.setLeftAnchor(arrivalTimeLabel, 5.0);
			AnchorPane.setTopAnchor(arrivalTimeLabel, 35.0);

			elements.add(arrivalTimeValueLabel);
			AnchorPane.setLeftAnchor(arrivalTimeValueLabel, 15.0);
			AnchorPane.setTopAnchor(arrivalTimeValueLabel, 50.0);

			elements.add(burstTimeLabel);
			AnchorPane.setLeftAnchor(burstTimeLabel, 5.0);
			AnchorPane.setTopAnchor(burstTimeLabel, 80.0);

			elements.add(burstTimeValueLabel);
			AnchorPane.setLeftAnchor(burstTimeValueLabel, 15.0);
			AnchorPane.setTopAnchor(burstTimeValueLabel, 95.0);

			jobPane.getStyleClass()
				.add("job-slice");
			blocks.add(jobPane);
		}
	}
}

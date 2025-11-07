package com.example.task_manager_beta;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class HelloController
{

    @FXML
    public AnchorPane root;
    @FXML
    public ComboBox<WorkTypes> taskComboBox;
    @FXML
    public ComboBox<Double>hoursRequiredComboBox;
    @FXML
    public CheckBox setupRequiredBox;
    @FXML
    public CheckBox cleanupRequiredBox;
    @FXML
    public TableView<MaintenanceTask> taskTable;
    @FXML
    public TableColumn<MaintenanceTask, String> typeColumn;
    @FXML
    public TableColumn<MaintenanceTask, Double> hoursRequiredColumn;
    @FXML
    public TableColumn<MaintenanceTask, Boolean> setupColumn;
    @FXML
    public TableColumn<MaintenanceTask, Boolean> cleanupColumn;
    @FXML
    public TableColumn<MaintenanceTask, Void> completeColumn;
    @FXML
    public Spinner<Integer> taskQuantitySpinner;
    @FXML
    public TableView<MaintenanceTask> completedTaskTable;
    @FXML
    public TableColumn<MaintenanceTask, String> dateTimeCompletedColumn;
    @FXML
    public TableColumn<MaintenanceTask, String> completedTypeColumn;
    @FXML
    public TextField timeToCompleteField;
    @FXML
    public TextField timeCompleted;
    @FXML
    public TextField mostCommonTask;
    @FXML
    public GridPane MainPane;

    public ObservableList<MaintenanceTask> taskList = FXCollections.observableArrayList();
    public ObservableList<MaintenanceTask> completedTaskList = FXCollections.observableArrayList();
    public ObservableList<WorkTypes> workTypes = FXCollections.observableArrayList(createDefaultWorkTypes());
    public ObservableList<Double> timeRequirements = FXCollections.observableArrayList(createDefaultTimeRequirements());

    private static List<WorkTypes> createDefaultWorkTypes(){
        return Arrays.stream(WorkTypes.values())
                .filter(w->w != WorkTypes.None)
                .collect(Collectors.toList());
    }

    private static Double[] createDefaultTimeRequirements(){
        int increments = 48;
        Double[] result = new Double[increments];
        for(int i = 0; i < increments; i++){
            result[i] = (i + 1) * .5;
        }
        return result;
    }

    @FXML
    public void initialize()
    {
        initializeTaskTable();
        initializeCompletedTaskTable();
        initializeWorkTypeComoboBox();
    }

    private void initializeWorkTypeComoboBox(){
        taskComboBox.setCellFactory(listView->new ListCell<WorkTypes>(){
            @Override
            protected void updateItem(WorkTypes item, boolean empty){
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getDisplayName());
            }
        });

        taskComboBox.setButtonCell(new ListCell<WorkTypes>(){
            @Override
            protected void updateItem(WorkTypes item, boolean empty){
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getDisplayName());
            }
        });
        taskComboBox.setItems(workTypes);
    }

    private void initializeCompletedTaskTable(){
        completedTaskTable.setItems(completedTaskList);
        dateTimeCompletedColumn.setCellValueFactory(cellData->cellData.getValue().dateTimeCompletedProperty());
        completedTypeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getWorkType().getDisplayName()));
        completedTaskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    private void initializeTaskTable(){
        taskTable.setItems(taskList);
        ClearButton_Click(null);
        typeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<String>(cellData.getValue().getWorkType().getDisplayName()));

        cleanupColumn.setCellValueFactory(cellData -> cellData.getValue().cleanUpRequiredProperty());
        cleanupColumn.setCellFactory(CheckBoxTableCell.forTableColumn(cleanupColumn));

        setupColumn.setCellValueFactory(cellData -> cellData.getValue().setUpRequiredProperty());
        setupColumn.setCellFactory(CheckBoxTableCell.forTableColumn(setupColumn));

        hoursRequiredColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getHoursRequired()));

        addDeactivateButtonToTable();

        taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    private void addDeactivateButtonToTable()
    {
        completeColumn.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Mark Complete");
            {
                btn.setOnMouseClicked(event -> Fireworks.launch(root, event));
                btn.setOnAction(event -> {
                    MaintenanceTask maintenanceTask = getTableView().getItems().get(getIndex());

                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");
                    String formattedNow = now.format(formatter);

                    maintenanceTask.dateTimeCompletedProperty().set(formattedNow);
                    taskList.remove(maintenanceTask);
                    completedTaskList.add(maintenanceTask);
                    updateGeneralInfo();

                    if(taskList.isEmpty()){
                        Fireworks.launchAllDone(root);
                    }

                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

    }
    public void SaveButton_Click(ActionEvent actionEvent)
    {
        Integer count = taskQuantitySpinner.getValue();
        for (int i = 0; i < count; i++) {
            taskList.add(new MaintenanceTask(
                    taskComboBox.getValue(),
                    hoursRequiredComboBox.getValue(),
                    setupRequiredBox.isSelected(),
                    cleanupRequiredBox.isSelected()
            ));
        }
        updateGeneralInfo();
        ClearButton_Click(actionEvent);
    }

    public void ClearButton_Click(ActionEvent actionEvent)
    {
        taskComboBox.setValue(WorkTypes.TNutReplacement);
        hoursRequiredComboBox.setItems(timeRequirements);
        hoursRequiredComboBox.setValue(.5);
        setupRequiredBox.setSelected(false);
        cleanupRequiredBox.setSelected(false);
        taskQuantitySpinner.getValueFactory().setValue(1);
    }

    private void updateGeneralInfo() {

        double timeToComplete = getTotalHoursRequired(taskList);
        double completedTime = getTotalHoursRequired(completedTaskList);
        WorkTypes nextPriorityTask = getNextHighestPriorityTask();

        timeToCompleteField.setText(timeToComplete + " hours remaining");
        timeCompleted.setText(completedTime + " hours completed");
        mostCommonTask.setText(nextPriorityTask.getDisplayName());

    }

    private WorkTypes getNextHighestPriorityTask() {
        if (taskList == null || taskList.isEmpty()) {
            return WorkTypes.None;
        }

        // Group all active tasks by work type
        Map<WorkTypes, List<MaintenanceTask>> groupedByWorkType = taskList.stream()
                .collect(Collectors.groupingBy(MaintenanceTask::getWorkType));

        // Find the maximum occurrence count
        int maxCount = groupedByWorkType.values().stream()
                .mapToInt(List::size)
                .max()
                .orElse(0);

        if (maxCount == 0) {
            return WorkTypes.None;
        }

        // Filter work types that have the max count
        List<WorkTypes> topWorkTypes = groupedByWorkType.entrySet().stream()
                .filter(e -> e.getValue().size() == maxCount)
                .map(Map.Entry::getKey)
                .toList();

        // Find the task with the highest hours among the top work types
        return topWorkTypes.stream()
                .flatMap(wt -> groupedByWorkType.get(wt).stream())
                .max(Comparator.comparingDouble(MaintenanceTask::getHoursRequired))
                .map(MaintenanceTask::getWorkType)
                .orElse(WorkTypes.None);
    }


    private static double getTotalHoursRequired(ObservableList<MaintenanceTask> list){
        double totalHours = 0.0;
        for (MaintenanceTask task : list) {
            totalHours += task.getHoursRequired();
        }
        return totalHours;
    }

}

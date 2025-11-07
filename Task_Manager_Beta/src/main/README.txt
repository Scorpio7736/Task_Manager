Task Manager (JavaFX Application Beta)
Overview

    This is a JavaFX-based Task Manager (Beta) designed to replace the earlier console-based version (Alpha).

    It provides a graphical interface to manage tasks more efficiently with tables, combo boxes, spinners, and dynamic analytics.

    Users Can...

Add tasks with:

    Work Type

    Hours Required

    Setup and Cleanup requirements

    Track task completion with automatic timestamp logging.

    View active and completed tasks in separate tables.

Monitor:

    Total time to complete remaining tasks

    Total time spent on completed tasks

    Most common task type (priority hint)

    Easily adjust task quantity and hours required with spinners and combo boxes.

    Enjoy visual feedback with fireworks and “All Done!” message when all tasks are completed.

Demonstrated Concepts

    JavaFX GUI programming

    Table management with TableView and custom TableCell actions

    Property binding (SimpleBooleanProperty, SimpleStringProperty)

    Event handling (button clicks, mouse events)

    Basic analytics and task prioritization

Prerequisites

    Java Development Kit (JDK) 17+ installed

    Check with:

        java -version


    A Java-compatible IDE (IntelliJ IDEA, Eclipse) or CLI tools (javac, java).

    JavaFX SDK installed and properly linked to your project.

    Project folder structure intact:

        src/com/example/task_manager_beta/

Setup Instructions

    Clone or download the project to your local machine.

    Ensure the folder structure matches:

        src/com/example/task_manager_beta/


    Compile Java classes with JavaFX included. Example (command line, replace PATH_TO_FX with your JavaFX SDK path):

        javac --module-path PATH_TO_FX --add-modules javafx.controls,javafx.fxml -d bin src/com/example/task_manager_beta/*.java


Run the application:

    java --module-path PATH_TO_FX --add-modules javafx.controls,javafx.fxml -cp bin com.example.task_manager_beta.Main

Features
    Add Task

        Select Work Type from a combo box.

        Choose task quantity with a spinner.

        Select hours required from increments (0.5 to 24).

        Mark if Setup or Cleanup is required.

        Click Add Task to insert into the task list.

    Mark Task Complete

        Each active task has a Mark Complete button.

        Clicking moves it to the completed task table and logs the date/time.

        Updates total hours remaining and completed automatically.

        Triggers fireworks animation, and if all tasks are done → “All Done!” message.

    View Tasks

        Tasks To Complete Table shows: Work Type, Hours Required, Setup, Cleanup, Actions.

        Completed Tasks Table shows: Task Type and Completion Timestamp.

        Both tables resize automatically and update live.

    Analytics

        Total hours remaining and hours completed are dynamically updated.

        Most common task type is identified and displayed.

        Prevents invalid input via combo boxes and spinners.

Known Issues

    None identified.

    Please report bugs or feature requests via Canvas comments.

Future Improvements

    Advanced analytics for smarter task prioritization.

    Drag-and-drop task reordering.

    Color-coded urgency indicators.

    Persistent storage (database or JSON integration).

Link to how to video

    https://youtu.be/bTlEzukutHc
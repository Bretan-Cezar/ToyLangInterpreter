package com.toylanggui;

import com.toylanggui.interpreter.model.ProgramState;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.statement.IStatement;
import com.toylanggui.interpreter.model.value.IValue;
import com.toylanggui.interpreter.model.value.StringValue;
import com.toylanggui.interpreter.view.RunExample;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;


public class MainController {

    private SelectorController selectorController;

    @FXML
    private TableView<HeapEntry> heapTableView;
    @FXML
    private TableColumn<HeapEntry, String> addressColumn;
    @FXML
    private TableColumn<HeapEntry, String> heapvalueColumn;

    @FXML
    private TableView<ProcTableEntry> procTableView;
    @FXML
    private TableColumn<ProcTableEntry, String> funcNameColumn;
    @FXML
    private TableColumn<ProcTableEntry, String> funcValueColumn;

    @FXML
    private ListView<IValue> outListView;
    @FXML
    private ListView<StringValue> filesListView;
    @FXML
    private TextField countTextField;
    @FXML
    private ListView<ProgramState> statesListView;
    @FXML
    private Button runBtn;

    @FXML
    private TableView<SymTableEntry> symTableView;
    @FXML
    private TableColumn<SymTableEntry, String> nameColumn;
    @FXML
    private TableColumn<SymTableEntry, String> symvalueColumn;

    @FXML
    private ListView<IStatement> stackListView;

    public void setSelectorController(SelectorController sc) {

        selectorController = sc;
        selectorController.getProgramList().getSelectionModel().selectedItemProperty().addListener((observableValue, runExample, ex) -> showSelectedExample(ex));
    }

    public MainController() {

        addressColumn = new TableColumn<>();
        heapvalueColumn = new TableColumn<>();
        heapTableView = new TableView<>();

        funcNameColumn = new TableColumn<>();
        funcValueColumn = new TableColumn<>();
        procTableView = new TableView<>();

        outListView = new ListView<>();
        filesListView = new ListView<>();

        countTextField = new TextField();
        statesListView = new ListView<>();
        runBtn = new Button();

        symTableView = new TableView<>();
        nameColumn = new TableColumn<>();
        symvalueColumn = new TableColumn<>();

        stackListView = new ListView<>();
    }

    @FXML
    public void initialize() {

        addressColumn.setCellValueFactory(new PropertyValueFactory<HeapEntry, String>("address"));

        heapvalueColumn.setCellValueFactory(new PropertyValueFactory<HeapEntry, String>("value"));

        funcNameColumn.setCellValueFactory(new PropertyValueFactory<ProcTableEntry, String>("name"));

        funcValueColumn.setCellValueFactory(new PropertyValueFactory<ProcTableEntry, String>("value"));

        outListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<>() {
            @Override
            public String toString(IValue iValue) {
                return iValue.toString();
            }

            @Override
            public IValue fromString(String s) {
                return null;
            }
        }));


        filesListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<>() {
            @Override
            public String toString(StringValue stringValue) {
                return stringValue.toString();
            }

            @Override
            public StringValue fromString(String s) {
                return null;
            }
        }));

        statesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        statesListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<ProgramState>() {
            @Override
            public String toString(ProgramState programState) {
                return Integer.toString(programState.getProgramID());
            }

            @Override
            public ProgramState fromString(String s) {
                return null;
            }
        }));

        statesListView.getSelectionModel().selectedItemProperty().addListener((observableValue, progSt, state) -> {

            if ( state != null )
                showSelectedState(state);
        });

        runBtn.setOnAction(actionEvent -> runOneStep(selectorController.getProgramList().getSelectionModel().getSelectedItems().get(0)));

        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setCellValueFactory(new PropertyValueFactory<SymTableEntry, String>("varName"));

        symvalueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        symvalueColumn.setCellValueFactory(new PropertyValueFactory<SymTableEntry, String>("value"));

        stackListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<IStatement>() {
            @Override
            public String toString(IStatement iStatement) {
                return iStatement.toString();
            }

            @Override
            public IStatement fromString(String s) {
                return null;
            }
        }));

    }


    public void showSelectedExample(RunExample ex) {

        statesListView.getSelectionModel().clearSelection();

        ObservableList<HeapEntry> heapData = heapTableView.getItems();
        ObservableList<ProcTableEntry> procTableData = procTableView.getItems();
        ObservableList<IValue> outData = outListView.getItems();
        ObservableList<StringValue> fileData = filesListView.getItems();
        ObservableList<ProgramState> stateIDs = statesListView.getItems();

        heapData.clear();
        procTableData.clear();
        outData.clear();
        fileData.clear();
        stateIDs.clear();
        symTableView.getItems().clear();
        stackListView.getItems().clear();

        ex.getController().getSharedHeap().getContent().forEach((addr, val) -> heapData.add(new HeapEntry(addr, val)));
        ex.getController().getSharedProcTable().getContent().forEach((name, value) -> procTableData.add(new ProcTableEntry(name, value)));
        ex.getController().getSharedOut().getContent().forEach((value) -> outData.add(value));
        ex.getController().getSharedFileTable().getContent().forEach((val, reader) -> fileData.add(val));

        countTextField.setText(Integer.toString(ex.getController().getRepo().size()));

        ex.getController().getRepo().getProgramList().getContent().forEach((programState -> stateIDs.add(programState)));

    }

    public void showSelectedState(ProgramState state) {

        ObservableList<SymTableEntry> symtableData = symTableView.getItems();
        ObservableList<IStatement> stackData = stackListView.getItems();

        symtableData.clear();
        stackData.clear();

        state.getSymTableStack().getContent().getFirst().getContent().forEach((name, val) -> symtableData.add(new SymTableEntry(name, val)));
        state.getExeStack().getContent().forEach(stmt -> stackData.add(stmt));
    }

    public void runOneStep(RunExample ex) {

        try {

            ex.getController().oneStepForAllPrograms();
        }
        catch (ToyLangException e) {

            showExceptionWindow(e.getMessage());
        }

        showSelectedExample(ex);
    }

    public void showExceptionWindow(String msg) {

        FXMLLoader fxmlLoaderException = new FXMLLoader(Application.class.getResource("exception-window.fxml"));

        Scene mainScene;

        try {

            mainScene = new Scene(fxmlLoaderException.load(), 300, 150);
        }
        catch (IOException exc) {

            exc.printStackTrace();
            return;
        }

        ExceptionController excController = fxmlLoaderException.getController();
        excController.setExceptionMessage(msg);

        Stage excWindow = new Stage();

        excWindow.setTitle("Exception occurred!");
        excWindow.setScene(mainScene);

        excWindow.show();
    }
}
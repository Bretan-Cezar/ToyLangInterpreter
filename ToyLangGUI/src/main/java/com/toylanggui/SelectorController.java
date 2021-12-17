package com.toylanggui;

import com.toylanggui.interpreter.controller.Controller;
import com.toylanggui.interpreter.model.ProgramState;
import com.toylanggui.interpreter.model.dictionary.Dictionary;
import com.toylanggui.interpreter.model.dictionary.IDictionary;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.expression.*;
import com.toylanggui.interpreter.model.heap.Heap;
import com.toylanggui.interpreter.model.heap.IHeap;
import com.toylanggui.interpreter.model.list.IList;
import com.toylanggui.interpreter.model.list.List;
import com.toylanggui.interpreter.model.stack.Stack;
import com.toylanggui.interpreter.model.statement.*;
import com.toylanggui.interpreter.model.type.BoolType;
import com.toylanggui.interpreter.model.type.IntType;
import com.toylanggui.interpreter.model.type.RefType;
import com.toylanggui.interpreter.model.value.BoolValue;
import com.toylanggui.interpreter.model.value.IValue;
import com.toylanggui.interpreter.model.value.IntValue;
import com.toylanggui.interpreter.model.value.StringValue;
import com.toylanggui.interpreter.repository.IRepository;
import com.toylanggui.interpreter.repository.Repository;
import com.toylanggui.interpreter.view.RunExample;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

import java.io.BufferedReader;

public class SelectorController {

    @FXML
    private ListView<RunExample> programListView;

    public SelectorController() {

        programListView = new ListView<>();
    }

    public ListView<RunExample> getProgramList() {

        return programListView;
    }

    public void initialize() throws ToyLangException {

        ObservableList<RunExample> data = programListView.getItems();

        /*
         *  Example1:
         *
         *  int v; v = 2; print(v) is represented as:
         */

        IList<IValue> out1 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable1 = new Dictionary<>();
        IHeap heap1 = new Heap();

        IStatement ex1 = new CompStatement( new VarDeclStatement("v", new IntType()),
                new CompStatement( new AssignStatement("v", new ValueExpression( new IntValue(2))),
                        new PrintStatement( new VariableExpression("v"))));

        ex1.typecheck(new Dictionary<>());

        ProgramState state1 = new ProgramState(new Stack<>(), new Dictionary<>(), out1, fileTable1, heap1, ex1);

        IRepository repository1 = new Repository(50, "log1.txt");

        repository1.addProgram(state1);

        Controller controller1 = new Controller(repository1, out1, fileTable1, heap1);

        /*
         *  Example2:
         *
         *  int a; int b; a = 2 + 3*5; b = a + 1; print(b) is represented as:
         */

        IList<IValue> out2 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable2 = new Dictionary<>();
        IHeap heap2 = new Heap();

        IStatement ex2 = new CompStatement( new VarDeclStatement("a", new IntType()),
                new CompStatement( new VarDeclStatement("b", new IntType()),
                        new CompStatement( new AssignStatement("a", new ArithmeticExpression( new ValueExpression( new IntValue(2)), new ArithmeticExpression( new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(0)), '/'), '+')),
                                new CompStatement( new AssignStatement("b", new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)), '+')), new PrintStatement(new VariableExpression("b"))))));

        ex2.typecheck(new Dictionary<>());

        ProgramState state2 = new ProgramState(new Stack<>(), new Dictionary<>(), out2, fileTable2,  heap2, ex2);

        IRepository repository2 = new Repository(50, "log2.txt");

        repository2.addProgram(state2);

        Controller controller2 = new Controller(repository2, out2, fileTable2, heap2);

        /*
         *  Example3:
         *
         *  bool a; int v; a = true; (IF a THEN v = 2 ELSE v = 3); print(v) is represented as:
         */

        IList<IValue> out3 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable3 = new Dictionary<>();
        IHeap heap3 = new Heap();

        IStatement ex3 = new CompStatement( new VarDeclStatement("a", new BoolType()),
                new CompStatement( new VarDeclStatement("v", new IntType()),
                        new CompStatement( new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(new CondStatement(new VariableExpression("a"), new AssignStatement("v",new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        ex3.typecheck(new Dictionary<>());

        ProgramState state3 = new ProgramState(new Stack<>(), new Dictionary<>(), out3, fileTable3, heap3, ex3);

        IRepository repository3 = new Repository(50, "log3.txt");

        repository3.addProgram(state3);

        Controller controller3 = new Controller(repository3, out3, fileTable3, heap3);

        /*
         *  Example4:
         *
         *  openRFile("test.txt"); int n; readFile("test.txt", n); print(n); readFile("test.txt", n); print(n); closeRFile("test.txt");
         */

        IList<IValue> out4 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable4 = new Dictionary<>();
        IHeap heap4 = new Heap();

        String filename = "test.txt";

        IStatement open = new OpenRFile(new ValueExpression(new StringValue(filename)));
        IStatement decl_n = new VarDeclStatement("n", new IntType());
        IStatement read = new ReadFile(new ValueExpression(new StringValue(filename)), "n");
        IStatement close = new CloseRFile(new ValueExpression(new StringValue(filename)));
        IStatement print = new PrintStatement(new VariableExpression("n"));

        IStatement ex4 = new CompStatement(open, new CompStatement(decl_n, new CompStatement(read,
                new CompStatement(print, new CompStatement(read, new CompStatement(print, close))))));

        ex4.typecheck(new Dictionary<>());

        ProgramState state4 = new ProgramState(new Stack<>(), new Dictionary<>(), out4, fileTable4, heap4, ex4);

        IRepository repository4 = new Repository(50, "log4.txt");

        repository4.addProgram(state4);

        Controller controller4 = new Controller(repository4, out4, fileTable4, heap4);

        /*
         *  Example5:
         *
         *  int a; a = 3; int b; b = 4; IF (a > b) THEN print(a) ELSE print(b)
         */

        IList<IValue> out5 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable5 = new Dictionary<>();
        IHeap heap5 = new Heap();

        IStatement decl_a = new VarDeclStatement("a", new IntType());
        IStatement assign_a = new AssignStatement("a", new ValueExpression(new IntValue(3)));
        IStatement decl_b = new VarDeclStatement("b", new IntType());
        IStatement assign_b = new AssignStatement("b", new ValueExpression(new IntValue(4)));
        IExpression comp = new RelationalExpression(new VariableExpression("a"), new VariableExpression("b"), ">");
        IStatement print_a = new PrintStatement(new VariableExpression("a"));
        IStatement print_b = new PrintStatement(new VariableExpression("b"));
        IStatement cond = new CondStatement(comp, print_a, print_b);

        IStatement ex5 = new CompStatement(decl_a, new CompStatement(assign_a, new CompStatement(decl_b, new CompStatement(assign_b, cond))));

        ex5.typecheck(new Dictionary<>());

        ProgramState state5 = new ProgramState(new Stack<>(), new Dictionary<>(), out5, fileTable5, heap5, ex5);

        IRepository repository5 = new Repository(50, "log5.txt");

        repository5.addProgram(state5);

        Controller controller5 = new Controller(repository5, out5, fileTable5, heap5);

        /*
         *  Example6:
         *
         *  Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5);
         */

        IList<IValue> out6 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable6 = new Dictionary<>();
        IHeap heap6 = new Heap();

        IStatement decl_refv = new VarDeclStatement("v", new RefType(new IntType()));
        IStatement alloc1_v = new Alloc("v", new ValueExpression(new IntValue(20)));
        IExpression read_v = new ReadHeap(new VariableExpression("v"));
        IStatement print1_v = new PrintStatement(read_v);
        IStatement write_v = new WriteHeap("v", new ValueExpression(new IntValue(30)));
        IStatement print2_v = new PrintStatement(new ArithmeticExpression(read_v, new ValueExpression(new IntValue(5)), '+'));

        IStatement ex6 = new CompStatement(decl_refv, new CompStatement(alloc1_v, new CompStatement(print1_v, new CompStatement(write_v, print2_v))));

        ex6.typecheck(new Dictionary<>());

        ProgramState state6 = new ProgramState(new Stack<>(), new Dictionary<>(), out6, fileTable6, heap6, ex6);

        IRepository repository6 = new Repository(50, "log6.txt");

        repository6.addProgram(state6);

        Controller controller6 = new Controller(repository6, out6, fileTable6, heap6);

        /*
         *  Example7:
         *
         *  int v; v = 4; ( while (v > 0) print(v); v = v-1 ); print(v)
         */

        IList<IValue> out7 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable7 = new Dictionary<>();
        IHeap heap7 = new Heap();

        IStatement decl_v = new VarDeclStatement("v", new IntType());
        IStatement assign_v = new AssignStatement("v", new ValueExpression(new IntValue(4)));
        IStatement print3_v = new PrintStatement(new VariableExpression("v"));
        IStatement while_body1 = new CompStatement(print3_v,
                new AssignStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), '-')));

        IStatement while1 = new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"), while_body1);

        IStatement ex7 = new CompStatement(decl_v, new CompStatement(assign_v, new CompStatement(while1, print3_v)));

        ex7.typecheck(new Dictionary<>());

        ProgramState state7 = new ProgramState(new Stack<>(), new Dictionary<>(), out7, fileTable7, heap7, ex7);

        IRepository repository7 = new Repository(50, "log7.txt");

        repository7.addProgram(state7);

        Controller controller7 = new Controller(repository7, out7, fileTable7, heap7);

        /*
         *  Example8:
         *
         *  Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))
         */

        IList<IValue> out8 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable8 = new Dictionary<>();
        IHeap heap8 = new Heap();

        IStatement decl_refrefa = new VarDeclStatement("a", new RefType(new RefType(new IntType())));
        IStatement alloc_a_v = new Alloc("a", new VariableExpression("v"));
        IStatement alloc2_v = new Alloc("v", new ValueExpression(new IntValue(30)));
        IExpression read_refrefa = new ReadHeap(new ReadHeap(new VariableExpression("a")));

        IStatement ex8 = new CompStatement(decl_refv, new CompStatement(alloc1_v,
                new CompStatement(decl_refrefa, new CompStatement(alloc_a_v, new CompStatement(alloc2_v, new PrintStatement(read_refrefa))))));

        ex8.typecheck(new Dictionary<>());

        ProgramState state8 = new ProgramState(new Stack<>(), new Dictionary<>(), out8, fileTable8, heap8, ex8);

        IRepository repository8 = new Repository(50, "log8.txt");

        repository8.addProgram(state8);

        Controller controller8 = new Controller(repository8, out8, fileTable8, heap8);

        /*
         *  Example9:
         *  int v; Ref int a; v = 10; new(a,22); fork(wH(a,30); v = 32; print(v); print(rH(a))); print(v); print(rH(a))
         */

        IList<IValue> out9 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable9 = new Dictionary<>();
        IHeap heap9 = new Heap();

        IStatement decl_refa = new VarDeclStatement("a", new RefType(new IntType()));
        IStatement assign2_v = new AssignStatement("v", new ValueExpression(new IntValue(10)));
        IStatement alloc1_a = new Alloc("a", new ValueExpression(new IntValue(22)));
        IStatement write1_a = new WriteHeap("a", new ValueExpression(new IntValue(30)));
        IStatement assign3_v = new AssignStatement("v", new ValueExpression(new IntValue(32)));
        IStatement print_read_a = new PrintStatement(new ReadHeap(new VariableExpression("a")));

        IStatement fork1 = new ForkStatement(new CompStatement(write1_a,
                new CompStatement(assign3_v,
                        new CompStatement(print3_v, print_read_a))));

        IStatement ex9 = new CompStatement(decl_v,
                new CompStatement(decl_refa,
                        new CompStatement(assign2_v,
                                new CompStatement(alloc1_a,
                                        new CompStatement(fork1,
                                                new CompStatement(print3_v, print_read_a))))));

        ex9.typecheck(new Dictionary<>());

        ProgramState state9 = new ProgramState(new Stack<>(), new Dictionary<>(), out9, fileTable9, heap9, ex9);

        IRepository repository9 = new Repository(50, "log9.txt");

        repository9.addProgram(state9);

        Controller controller9 = new Controller(repository9, out9, fileTable9, heap9);

        /*
         *  Example10:
         *  Ref int a; new(a, 22); fork( print(rH(a)); wH(a,24); nop; print(rH(a)); wH(a,28) ); fork(nop; print(rH(a)); wH(a,26); nop; nop; print(rH(a)))
         */

        IList<IValue> out10 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable10 = new Dictionary<>();
        IHeap heap10 = new Heap();

        IStatement nop = new NoOpStatement();
        IStatement write2_a = new WriteHeap("a", new ValueExpression(new IntValue(24)));
        IStatement write3_a = new WriteHeap("a", new ValueExpression(new IntValue(26)));
        IStatement write4_a = new WriteHeap("a", new ValueExpression(new IntValue(28)));

        IStatement fork2 = new ForkStatement(
                new CompStatement(print_read_a,
                        new CompStatement(write2_a,
                                new CompStatement(nop,
                                        new CompStatement(print_read_a, write4_a)))));

        IStatement fork3 = new ForkStatement(
                new CompStatement(nop,
                        new CompStatement(print_read_a,
                                new CompStatement(write3_a,
                                        new CompStatement(nop,
                                                new CompStatement(nop, print_read_a))))));

        IStatement ex10 = new CompStatement(decl_refa,
                new CompStatement(alloc1_a,
                        new CompStatement(fork2, fork3)));

        ex10.typecheck(new Dictionary<>());

        ProgramState state10 = new ProgramState(new Stack<>(), new Dictionary<>(), out10, fileTable10, heap10, ex10);

        IRepository repository10 = new Repository(50, "log10.txt");

        repository10.addProgram(state10);

        Controller controller10 = new Controller(repository10, out10, fileTable10, heap10);

        data.add(new RunExample("1", ex1.toString(), controller1));
        data.add(new RunExample("2", ex2.toString(), controller2));
        data.add(new RunExample("3", ex3.toString(), controller3));
        data.add(new RunExample("4", ex4.toString(), controller4));
        data.add(new RunExample("5", ex5.toString(), controller5));
        data.add(new RunExample("6", ex6.toString(), controller6));
        data.add(new RunExample("7", ex7.toString(), controller7));
        data.add(new RunExample("8", ex8.toString(), controller8));
        data.add(new RunExample("9", ex9.toString(), controller9));
        data.add(new RunExample("10", ex10.toString(), controller10));

        programListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<>() {
            @Override
            public String toString(RunExample runExample) {
                return String.format("%s : %s", runExample.getKey(), runExample.getDescription());
            }

            @Override
            public RunExample fromString(String s) {
                return null;
            }
        }));

        programListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

}

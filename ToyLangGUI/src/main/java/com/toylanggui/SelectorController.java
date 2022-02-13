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
import com.toylanggui.interpreter.model.proc_table.IProcTable;
import com.toylanggui.interpreter.model.proc_table.ProcTable;
import com.toylanggui.interpreter.model.stack.IStack;
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
import java.util.ArrayList;

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

        IStack<IDictionary<String, IValue>> s_stack1 = new Stack<>();
        s_stack1.push(new Dictionary<>());

        IProcTable p_table1 = new ProcTable();

        IStatement ex1 = new CompStatement( new VarDeclStatement("v", new IntType()),
                new CompStatement( new AssignStatement("v", new ValueExpression( new IntValue(2))),
                        new PrintStatement( new VariableExpression("v"))));

        ex1.typecheck(new Dictionary<>());

        ProgramState state1 = new ProgramState(new Stack<>(), s_stack1, out1, fileTable1, heap1, p_table1, ex1);

        IRepository repository1 = new Repository(50, "log1.txt");

        repository1.addProgram(state1);

        Controller controller1 = new Controller(repository1, out1, fileTable1, heap1, p_table1);


        /*
         *  Example2:
         *
         *  int a; int b; a = 2 + 3*5; b = a + 1; print(b) is represented as:
         */


        IList<IValue> out2 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable2 = new Dictionary<>();
        IHeap heap2 = new Heap();

        IStack<IDictionary<String, IValue>> s_stack2 = new Stack<>();
        s_stack2.push(new Dictionary<>());

        IProcTable p_table2 = new ProcTable();

        IStatement ex2 = new CompStatement( new VarDeclStatement("a", new IntType()),
                new CompStatement( new VarDeclStatement("b", new IntType()),
                        new CompStatement( new AssignStatement("a", new ArithmeticExpression( new ValueExpression( new IntValue(2)), new ArithmeticExpression( new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(0)), '/'), '+')),
                                new CompStatement( new AssignStatement("b", new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)), '+')), new PrintStatement(new VariableExpression("b"))))));

        ex2.typecheck(new Dictionary<>());

        ProgramState state2 = new ProgramState(new Stack<>(), s_stack2, out2, fileTable2, heap2, p_table2, ex2);

        IRepository repository2 = new Repository(50, "log2.txt");

        repository2.addProgram(state2);

        Controller controller2 = new Controller(repository2, out2, fileTable2, heap2, p_table2);


        /*
         *  Example3:
         *
         *  bool a; int v; a = true; (IF a THEN v = 2 ELSE v = 3); print(v) is represented as:
         */

        IList<IValue> out3 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable3 = new Dictionary<>();
        IHeap heap3 = new Heap();

        IStack<IDictionary<String, IValue>> s_stack3 = new Stack<>();
        s_stack3.push(new Dictionary<>());

        IProcTable p_table3 = new ProcTable();

        IStatement ex3 = new CompStatement( new VarDeclStatement("a", new BoolType()),
                new CompStatement( new VarDeclStatement("v", new IntType()),
                        new CompStatement( new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(new CondStatement(new VariableExpression("a"), new AssignStatement("v",new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        ex3.typecheck(new Dictionary<>());

        ProgramState state3 = new ProgramState(new Stack<>(), s_stack3, out3, fileTable3, heap3, p_table3, ex3);

        IRepository repository3 = new Repository(50, "log3.txt");

        repository3.addProgram(state3);

        Controller controller3 = new Controller(repository3, out3, fileTable3, heap3, p_table3);


        /*
         *  Example4:
         *
         *  openRFile("test.txt"); int n; readFile("test.txt", n); print(n); readFile("test.txt", n); print(n); closeRFile("test.txt");
         */


        IList<IValue> out4 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable4 = new Dictionary<>();
        IHeap heap4 = new Heap();

        IStack<IDictionary<String, IValue>> s_stack4 = new Stack<>();
        s_stack4.push(new Dictionary<>());

        IProcTable p_table4 = new ProcTable();

        String filename = "test.txt";

        IStatement open = new OpenRFile(new ValueExpression(new StringValue(filename)));
        IStatement decl_n = new VarDeclStatement("n", new IntType());
        IStatement read = new ReadFile(new ValueExpression(new StringValue(filename)), "n");
        IStatement close = new CloseRFile(new ValueExpression(new StringValue(filename)));
        IStatement print = new PrintStatement(new VariableExpression("n"));

        IStatement ex4 = new CompStatement(open, new CompStatement(decl_n, new CompStatement(read,
                new CompStatement(print, new CompStatement(read, new CompStatement(print, close))))));

        ex4.typecheck(new Dictionary<>());

        ProgramState state4 = new ProgramState(new Stack<>(), s_stack4, out4, fileTable4, heap4, p_table4, ex4);

        IRepository repository4 = new Repository(50, "log4.txt");

        repository4.addProgram(state4);

        Controller controller4 = new Controller(repository4, out4, fileTable4, heap4, p_table4);


        /*
         *  Example5:
         *
         *  int a; a = 3; int b; b = 4; IF (a > b) THEN print(a) ELSE print(b)
         */


        IList<IValue> out5 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable5 = new Dictionary<>();
        IHeap heap5 = new Heap();

        IStack<IDictionary<String, IValue>> s_stack5 = new Stack<>();
        s_stack5.push(new Dictionary<>());

        IProcTable p_table5 = new ProcTable();

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

        ProgramState state5 = new ProgramState(new Stack<>(), s_stack5, out5, fileTable5, heap5, p_table5, ex5);

        IRepository repository5 = new Repository(50, "log5.txt");

        repository5.addProgram(state5);

        Controller controller5 = new Controller(repository5, out5, fileTable5, heap5, p_table5);


        /*
         *  Example6:
         *
         *  Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5);
         */


        IList<IValue> out6 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable6 = new Dictionary<>();
        IHeap heap6 = new Heap();

        IStack<IDictionary<String, IValue>> s_stack6 = new Stack<>();
        s_stack6.push(new Dictionary<>());

        IProcTable p_table6 = new ProcTable();

        IStatement decl_refv = new VarDeclStatement("v", new RefType(new IntType()));
        IStatement alloc1_v = new Alloc("v", new ValueExpression(new IntValue(20)));
        IExpression read_v = new ReadHeap(new VariableExpression("v"));
        IStatement print1_v = new PrintStatement(read_v);
        IStatement write_v = new WriteHeap("v", new ValueExpression(new IntValue(30)));
        IStatement print2_v = new PrintStatement(new ArithmeticExpression(read_v, new ValueExpression(new IntValue(5)), '+'));

        IStatement ex6 = new CompStatement(decl_refv, new CompStatement(alloc1_v, new CompStatement(print1_v, new CompStatement(write_v, print2_v))));

        ex6.typecheck(new Dictionary<>());

        ProgramState state6 = new ProgramState(new Stack<>(), s_stack6, out6, fileTable6, heap6, p_table6, ex6);

        IRepository repository6 = new Repository(50, "log6.txt");

        repository6.addProgram(state6);

        Controller controller6 = new Controller(repository6, out6, fileTable6, heap6, p_table6);


        /*
         *  Example7:
         *
         *  int v; v = 4; ( while (v > 0) print(v); v = v-1 ); print(v)
         */


        IList<IValue> out7 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable7 = new Dictionary<>();
        IHeap heap7 = new Heap();

        IStack<IDictionary<String, IValue>> s_stack7 = new Stack<>();
        s_stack7.push(new Dictionary<>());

        IProcTable p_table7 = new ProcTable();

        IStatement decl_v = new VarDeclStatement("v", new IntType());
        IStatement assign_v = new AssignStatement("v", new ValueExpression(new IntValue(4)));
        IStatement print3_v = new PrintStatement(new VariableExpression("v"));
        IStatement while_body1 = new CompStatement(print3_v,
                new AssignStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), '-')));

        IStatement while1 = new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"), while_body1);

        IStatement ex7 = new CompStatement(decl_v, new CompStatement(assign_v, new CompStatement(while1, print3_v)));

        ex7.typecheck(new Dictionary<>());

        ProgramState state7 = new ProgramState(new Stack<>(), s_stack7, out7, fileTable7, heap7, p_table7, ex7);

        IRepository repository7 = new Repository(50, "log7.txt");

        repository7.addProgram(state7);

        Controller controller7 = new Controller(repository7, out7, fileTable7, heap7, p_table7);


        /*
         *  Example8:
         *
         *  Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))
         */


        IList<IValue> out8 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable8 = new Dictionary<>();
        IHeap heap8 = new Heap();

        IStack<IDictionary<String, IValue>> s_stack8 = new Stack<>();
        s_stack8.push(new Dictionary<>());

        IProcTable p_table8 = new ProcTable();

        IStatement decl_refrefa = new VarDeclStatement("a", new RefType(new RefType(new IntType())));
        IStatement alloc_a_v = new Alloc("a", new VariableExpression("v"));
        IStatement alloc2_v = new Alloc("v", new ValueExpression(new IntValue(30)));
        IExpression read_refrefa = new ReadHeap(new ReadHeap(new VariableExpression("a")));

        IStatement ex8 = new CompStatement(decl_refv, new CompStatement(alloc1_v,
                new CompStatement(decl_refrefa, new CompStatement(alloc_a_v, new CompStatement(alloc2_v, new PrintStatement(read_refrefa))))));

        ex8.typecheck(new Dictionary<>());

        ProgramState state8 = new ProgramState(new Stack<>(), s_stack8, out8, fileTable8, heap8, p_table8, ex8);

        IRepository repository8 = new Repository(50, "log8.txt");

        repository8.addProgram(state8);

        Controller controller8 = new Controller(repository8, out8, fileTable8, heap8, p_table8);


        /*
         *  Example9:
         *  int v; Ref int a; v = 10; new(a,22); fork(wH(a,30); v = 32; print(v); print(rH(a))); print(v); print(rH(a))
         */


        IList<IValue> out9 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable9 = new Dictionary<>();
        IHeap heap9 = new Heap();

        IStack<IDictionary<String, IValue>> s_stack9 = new Stack<>();
        s_stack9.push(new Dictionary<>());

        IProcTable p_table9 = new ProcTable();

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

        ProgramState state9 = new ProgramState(new Stack<>(), s_stack9, out9, fileTable9, heap9, p_table9, ex9);

        IRepository repository9 = new Repository(50, "log9.txt");

        repository9.addProgram(state9);

        Controller controller9 = new Controller(repository9, out9, fileTable9, heap9, p_table9);


        /*
         *  Example10:
         *  Ref int a; new(a, 22); fork( print(rH(a)); wH(a,24); nop; print(rH(a)); wH(a,28) ); fork(nop; print(rH(a)); wH(a,26); nop; nop; print(rH(a)))
         */


        IList<IValue> out10 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable10 = new Dictionary<>();
        IHeap heap10 = new Heap();

        IStack<IDictionary<String, IValue>> s_stack10 = new Stack<>();
        s_stack10.push(new Dictionary<>());

        IProcTable p_table10 = new ProcTable();

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

        ProgramState state10 = new ProgramState(new Stack<>(), s_stack10, out10, fileTable10, heap10, p_table10, ex10);

        IRepository repository10 = new Repository(50, "log10.txt");

        repository10.addProgram(state10);

        Controller controller10 = new Controller(repository10, out10, fileTable10, heap10, p_table10);

        /*
        procedure sum(a,b) v=a+b;print(v)
        procedure product(a,b) v=a*b;print(v)

        Main program:
        v=2;w=5;call sum(v*10,w);print(v);
        fork(call product(v,w);
            fork(call sum(v,w)))

        The final Out should be {25,2,10,7}
        */

        IStack<IDictionary<String, IValue>> s_stack11 = new Stack<>();
        s_stack11.push(new Dictionary<>());

        IList<IValue> out11 = new List<>(100);
        IDictionary<StringValue, BufferedReader> fileTable11 = new Dictionary<>();
        IHeap heap11 = new Heap();
        IProcTable p_table11 = new ProcTable();

        IStatement print_v = new PrintStatement(new VariableExpression("v"));
        IStatement init_v = new CompStatement(new VarDeclStatement("v", new IntType()), new AssignStatement("v", new ValueExpression(new IntValue(2))));
        IStatement init_w = new CompStatement(new VarDeclStatement("w", new IntType()), new AssignStatement("w", new ValueExpression(new IntValue(5))));

        java.util.List<String> header_sum = new ArrayList<String>();
        header_sum.add("a");
        header_sum.add("b");

        IStatement body_sum = new CompStatement(
                new AssignStatement("v", new ArithmeticExpression(new VariableExpression("a"), new VariableExpression("b"), '+')),
                print_v);

        p_table11.modify("sum", header_sum, body_sum);



        java.util.List<String> header_product = new ArrayList<String>();
        header_product.add("a");
        header_product.add("b");

        IStatement body_product = new CompStatement(
                new AssignStatement("v", new ArithmeticExpression(new VariableExpression("a"), new VariableExpression("b"), '*')),
                print_v);

        p_table11.modify("product", header_product, body_product);



        java.util.List<IExpression> call_params1 = new ArrayList<>();
        call_params1.add(new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(10)), '*'));
        call_params1.add(new VariableExpression("w"));

        IStatement call_sum1 = new CallStatement("sum", call_params1);

        java.util.List<IExpression> call_params2 = new ArrayList<>();
        call_params2.add(new VariableExpression("v"));
        call_params2.add(new VariableExpression("w"));

        IStatement call_sum2 = new CallStatement("sum", call_params2);
        IStatement call_product = new CallStatement("product", call_params2);


        IStatement ex11 = new CompStatement(init_v,
                new CompStatement(init_w,
                        new CompStatement(call_sum1,
                                new CompStatement(print_v,
                                    new ForkStatement(new CompStatement(call_product, new ForkStatement(call_sum2)))))));

        ex11.typecheck(new Dictionary<>());

        ProgramState state11 = new ProgramState(new Stack<>(), s_stack11, out11, fileTable11, heap11, p_table11, ex11);

        IRepository repository11 = new Repository(50, "log11.txt");

        repository11.addProgram(state11);

        Controller controller11 = new Controller(repository11, out11, fileTable11, heap11, p_table11);

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
        data.add(new RunExample("11", ex11.toString(), controller11));

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

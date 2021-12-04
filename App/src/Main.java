import controller.Controller;
import model.ProgramState;
import model.dictionary.Dictionary;
import model.exceptions.ToyLangException;
import model.expression.*;
import model.heap.Heap;
import model.heap.IHeap;
import model.list.List;
import model.stack.Stack;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.*;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;

public class Main {

    public static void main(String[] args) throws ToyLangException {


        /*
         *  Example1:
         *
         *  int v; v = 2; print(v) is represented as:
         */

        IHeap heap1 = new Heap();
        IStatement ex1 = new CompStatement( new VarDeclStatement("v", new IntType()),
                new CompStatement( new AssignStatement("v", new ValueExpression( new IntValue(2))),
                        new PrintStatement( new VariableExpression("v"))));

        ex1.typecheck(new Dictionary<>());

        ProgramState state1 = new ProgramState(new Stack<>(), new Dictionary<>(), new List<>(50), new Dictionary<>(), heap1, ex1);

        IRepository repository1 = new Repository(50, "log1.txt");

        repository1.addProgram(state1);

        Controller controller1 = new Controller(repository1, heap1);


        /*
         *  Example2:
         *
         *  int a; int b; a = 2 + 3*5; b = a + 1; print(b) is represented as:
         */

        IHeap heap2 = new Heap();
        IStatement ex2 = new CompStatement( new VarDeclStatement("a", new IntType()),
                new CompStatement( new VarDeclStatement("b", new IntType()),
                        new CompStatement( new AssignStatement("a", new ArithmeticExpression( new ValueExpression( new IntValue(2)), new ArithmeticExpression( new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(0)), '/'), '+')),
                                new CompStatement( new AssignStatement("b", new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)), '+')), new PrintStatement(new VariableExpression("b"))))));

        ex2.typecheck(new Dictionary<>());

        ProgramState state2 = new ProgramState(new Stack<>(), new Dictionary<>(), new List<>(50), new Dictionary<>(), heap2, ex2);

        IRepository repository2 = new Repository(50, "log2.txt");

        repository2.addProgram(state2);

        Controller controller2 = new Controller(repository2, heap2);

        /*
         *  Example3:
         *
         *  bool a; int v; a = true; (IF a THEN v = 2 ELSE v = 3); print(v) is represented as:
         */

        IHeap heap3 = new Heap();
        IStatement ex3 = new CompStatement( new VarDeclStatement("a", new BoolType()),
                new CompStatement( new VarDeclStatement("v", new IntType()),
                        new CompStatement( new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(new CondStatement(new VariableExpression("a"), new AssignStatement("v",new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        ex3.typecheck(new Dictionary<>());

        ProgramState state3 = new ProgramState(new Stack<>(), new Dictionary<>(), new List<>(50), new Dictionary<>(), heap3, ex3);

        IRepository repository3 = new Repository(50, "log3.txt");

        repository3.addProgram(state3);

        Controller controller3 = new Controller(repository3, heap3);

        /*
         *  Example4:
         *
         *  openRFile("test.txt"); int n; readFile("test.txt", n); print(n); readFile("test.txt", n); print(n); closeRFile("test.txt");
         */

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

        ProgramState state4 = new ProgramState(new Stack<>(), new Dictionary<>(), new List<>(50), new Dictionary<>(), heap4, ex4);

        IRepository repository4 = new Repository(50, "log4.txt");

        repository4.addProgram(state4);

        Controller controller4 = new Controller(repository4, heap4);

        /*
         *  Example5:
         *
         *  int a; a = 3; int b; b = 4; IF (a > b) THEN print(a) ELSE print(b)
         */

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

        ProgramState state5 = new ProgramState(new Stack<>(), new Dictionary<>(), new List<>(50), new Dictionary<>(), heap5, ex5);

        IRepository repository5 = new Repository(50, "log5.txt");

        repository5.addProgram(state5);

        Controller controller5 = new Controller(repository5, heap5);

        /*
         *  Example6:
         *
         *  Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5);
         */

        IHeap heap6 = new Heap();

        IStatement decl_refv = new VarDeclStatement("v", new RefType(new IntType()));
        IStatement alloc1_v = new Alloc("v", new ValueExpression(new IntValue(20)));
        IExpression read_v = new ReadHeap(new VariableExpression("v"));
        IStatement print1_v = new PrintStatement(read_v);
        IStatement write_v = new WriteHeap("v", new ValueExpression(new IntValue(30)));
        IStatement print2_v = new PrintStatement(new ArithmeticExpression(read_v, new ValueExpression(new IntValue(5)), '+'));

        IStatement ex6 = new CompStatement(decl_refv, new CompStatement(alloc1_v, new CompStatement(print1_v, new CompStatement(write_v, print2_v))));

        ex6.typecheck(new Dictionary<>());

        ProgramState state6 = new ProgramState(new Stack<>(), new Dictionary<>(), new List<>(50), new Dictionary<>(), heap6, ex6);

        IRepository repository6 = new Repository(50, "log6.txt");

        repository6.addProgram(state6);

        Controller controller6 = new Controller(repository6, heap6);

        /*
         *  Example7:
         *
         *  int v; v = 4; ( while (v > 0) print(v); v = v-1 ); print(v)
         */

        IHeap heap7 = new Heap();

        IStatement decl_v = new VarDeclStatement("v", new IntType());
        IStatement assign_v = new AssignStatement("v", new ValueExpression(new IntValue(4)));
        IStatement print3_v = new PrintStatement(new VariableExpression("v"));
        IStatement while_body1 = new CompStatement(print3_v,
                new AssignStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), '-')));

        IStatement while1 = new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"), while_body1);

        IStatement ex7 = new CompStatement(decl_v, new CompStatement(assign_v, new CompStatement(while1, print3_v)));

        ex7.typecheck(new Dictionary<>());

        ProgramState state7 = new ProgramState(new Stack<>(), new Dictionary<>(), new List<>(50), new Dictionary<>(), heap7, ex7);

        IRepository repository7 = new Repository(50, "log7.txt");

        repository7.addProgram(state7);

        Controller controller7 = new Controller(repository7, heap7);

        /*
         *  Example8:
         *
         *  Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))
         */

        IHeap heap8 = new Heap();

        IStatement decl_refrefa = new VarDeclStatement("a", new RefType(new RefType(new IntType())));
        IStatement alloc_a_v = new Alloc("a", new VariableExpression("v"));
        IStatement alloc2_v = new Alloc("v", new ValueExpression(new IntValue(30)));
        IExpression read_refrefa = new ReadHeap(new ReadHeap(new VariableExpression("a")));

        IStatement ex8 = new CompStatement(decl_refv, new CompStatement(alloc1_v,
                new CompStatement(decl_refrefa, new CompStatement(alloc_a_v, new CompStatement(alloc2_v, new PrintStatement(read_refrefa))))));

        ex8.typecheck(new Dictionary<>());

        ProgramState state8 = new ProgramState(new Stack<>(), new Dictionary<>(), new List<>(50), new Dictionary<>(), heap8, ex8);

        IRepository repository8 = new Repository(50, "log8.txt");

        repository8.addProgram(state8);

        Controller controller8 = new Controller(repository8, heap8);

        /*
         *  Example9:
         *  int v; Ref int a; v = 10; new(a,22); fork(wH(a,30); v = 32; print(v); print(rH(a))); print(v); print(rH(a))
         */

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
        
        ProgramState state9 = new ProgramState(new Stack<>(), new Dictionary<>(), new List<>(50), new Dictionary<>(), heap9, ex9);


        IRepository repository9 = new Repository(50, "log9.txt");

        repository9.addProgram(state9);

        Controller controller9 = new Controller(repository9, heap9);

        TextMenu menu = new TextMenu();

        menu.addCommand(new ExitCommand("0","exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), controller1));
        menu.addCommand(new RunExample("2", ex2.toString(), controller2));
        menu.addCommand(new RunExample("3", ex3.toString(), controller3));
        menu.addCommand(new RunExample("4", ex4.toString(), controller4));
        menu.addCommand(new RunExample("5", ex5.toString(), controller5));
        menu.addCommand(new RunExample("6", ex6.toString(), controller6));
        menu.addCommand(new RunExample("7", ex7.toString(), controller7));
        menu.addCommand(new RunExample("8", ex8.toString(), controller8));
        menu.addCommand(new RunExample("9", ex9.toString(), controller9));

        menu.start();
    }

}

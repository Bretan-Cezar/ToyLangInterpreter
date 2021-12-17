package com.toylanggui.interpreter.view;

/*
import controller.Controller;
import model.*;
import model.dictionary.Dictionary;
import model.exceptions.ToyLangException;
import model.expression.ArithmeticExpression;
import model.expression.ValueExpression;
import model.expression.VariableExpression;
import model.list.List;
import model.stack.Stack;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;

import java.util.Scanner;
*/

public class View {

    /*
    private Controller controller;

    public View(Controller ctrl) {

        controller = ctrl;
    }

    public void start() {




        Scanner sc = new Scanner(System.in);

        ProgramState state;

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Select a program:\n");
        System.out.println("1. int v; v = 2; print(v)");
        System.out.println("2. int a; int b; a = 2 + 3*5; b = a + 1; print(b)");
        System.out.println("3. bool a; int v; a = true; (IF a THEN v = 2 ELSE v = 3); print(v)");
        System.out.println("0. exit interface\n");

        int program = -1;

        while (program != 0) {

            System.out.print("ToyLang>> ");
            program = sc.nextInt();

            switch (program) {

                case 1:

                    try {

                        state = new ProgramState(new Stack<IStatement>(), new Dictionary<String, IValue>(), new List<IValue>(50), ex1);
                        controller.getRepo().addProgram(state);
                        controller.allStep();
                    }
                    catch (ToyLangException tlre) {
                        System.out.println(tlre.getMessage());
                    }

                    break;

                case 2:

                    try {
                        state = new ProgramState(new Stack<IStatement>(), new Dictionary<String, IValue>(), new List<IValue>(50), ex2);
                        controller.getRepo().addProgram(state);
                        controller.allStep();
                    }
                    catch (ToyLangException tlre) {
                        System.out.println(tlre.getMessage());
                    }

                    break;

                case 3:

                    try {
                        state = new ProgramState(new Stack<IStatement>(), new Dictionary<String, IValue>(), new List<IValue>(50), ex3);
                        controller.getRepo().addProgram(state);
                        controller.allStep();
                    }
                    catch (ToyLangException tlre) {
                        System.out.println(tlre.getMessage());
                    }

                    break;

                case 0:

                    break;

                default:
                    System.out.println("Invalid program number!");
            }
        }

        System.out.println("\nToyLang terminated.\n");

    }
    */

}

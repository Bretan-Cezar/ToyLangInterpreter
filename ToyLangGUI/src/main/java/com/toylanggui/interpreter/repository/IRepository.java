package com.toylanggui.interpreter.repository;


import com.toylanggui.interpreter.model.ProgramState;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.list.List;

public interface IRepository {

    List<ProgramState> getProgramList();

    void setProgramList(java.util.List<ProgramState> list);

    void logProgramStateExecution(ProgramState state) throws ToyLangException;

    int size();

    void addProgram(ProgramState state);
}

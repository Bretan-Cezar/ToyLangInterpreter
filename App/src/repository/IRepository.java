package repository;

import model.ProgramState;
import model.exceptions.ToyLangException;
import model.list.List;


public interface IRepository {

    List<ProgramState> getProgramList();

    void setProgramList(java.util.List<ProgramState> list);

    void logProgramStateExecution(ProgramState state) throws ToyLangException;

    void addProgram(ProgramState state);
}

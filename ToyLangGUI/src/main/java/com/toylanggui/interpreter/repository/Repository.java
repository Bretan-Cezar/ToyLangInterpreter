package com.toylanggui.interpreter.repository;

import com.toylanggui.interpreter.model.ProgramState;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.list.List;

import java.io.*;

public class Repository implements IRepository {

    private List<ProgramState> programs;
    private String logFilePath;

    public Repository(int length, String path) {

        programs = new List<>(length);
        logFilePath = path;
    }

    @Override
    public List<ProgramState> getProgramList() {
        return programs;
    }

    @Override
    public void setProgramList(java.util.List<ProgramState> list) {

        programs.setContent(list);
    }

    @Override
    public void logProgramStateExecution(ProgramState state) throws ToyLangException {

        BufferedWriter fd;
        try {

            fd = new BufferedWriter(new FileWriter(logFilePath, true));
        }
        catch (IOException ioe) {

            throw new ToyLangException(ioe.getMessage());
        }
        PrintWriter logFile = new PrintWriter(fd);

        for (ProgramState prg : programs.getContent()) {

            logFile.print("\n" + prg.toLogString());
        }

        logFile.close();
    }

    @Override
    public int size() {
        return programs.length();
    }

    public void addProgram(ProgramState state) {

        programs.append(state);
    }

}

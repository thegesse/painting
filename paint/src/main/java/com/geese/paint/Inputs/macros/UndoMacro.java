package com.geese.paint.Inputs.macros;

public class UndoMacro implements Macro{
    private final HistoryManager historyManager;

    public UndoMacro(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @Override
    public void execute() {
        historyManager.undo();
        System.out.println("undone");
    }
}

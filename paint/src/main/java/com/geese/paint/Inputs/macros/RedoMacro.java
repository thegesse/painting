package com.geese.paint.Inputs.macros;

public class RedoMacro implements Macro {
    private final HistoryManager historyManager;

    public RedoMacro(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @Override
    public void execute() {
        historyManager.redo();
        System.out.println("redone");
    }
}

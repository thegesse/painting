package com.geese.paint.Inputs.macros;

import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

public class MacroHandler {
    private final Map<KeyCode, Macro> controlMacros = new HashMap<KeyCode, Macro>();

    public void registerMacros(KeyCode keyCode, Macro macro) {
        controlMacros.put(keyCode, macro);
    }

    public void handleKey(KeyCode keyCode, boolean isControlHeld) {
        if (isControlHeld && controlMacros.containsKey(keyCode)) {
            controlMacros.get(keyCode).execute();
        }
    }
}

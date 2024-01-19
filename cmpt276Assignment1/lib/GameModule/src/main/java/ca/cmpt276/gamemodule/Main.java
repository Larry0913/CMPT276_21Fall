package ca.cmpt276.gamemodule;

import ca.cmpt276.gamemodule.Model.*;
import ca.cmpt276.gamemodule.UI.*;

public class Main {
    public static void main(String[] args) {
        GameManager manager = new GameManager();
        TextUI ui = new TextUI(manager);
        while(true) {
            ui.showMenu();
        }
    }
}
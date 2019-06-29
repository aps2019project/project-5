package client.views;

import client.controllers.ClientManager;

public class InputAI extends Input {

    private static InputAI instance = new InputAI();

    private InputAI() {
        super();
    }

    @Override
    public String getCommand() {
        return ClientManager.getAIMove();
    }

    public static InputAI getInstance() {
        return instance;
    }

}

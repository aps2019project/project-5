package views;

public class InputAI extends Input {

    private static InputAI instance = new InputAI();

    private InputAI() {
        super();
    }

    @Override
    public String getCommand() {
        return "";
    }

    public static InputAI getInstance() {
        return instance;
    }

}

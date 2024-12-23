package puyopuyo.client.panel.map.subpanel.socket_command;

import puyopuyo.client.frame.Frame;
import puyopuyo.client.panel.start.StartPanel;

import static java.lang.Thread.sleep;

public class EndCommand implements SocketCommand {
    private static EndCommand instance;

    private EndCommand() {}

    public static synchronized EndCommand getInstance() {
        if(instance == null) {
            instance = new EndCommand();
        }
        return instance;
    }

    @Override
    public void execute(String json) {
        try {
            sleep(3000);
        } catch (InterruptedException _) {}
        Frame.getInstance().changePanel(StartPanel.getInstance());
    }
}

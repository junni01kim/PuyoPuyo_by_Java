package puyopuyo.client.panel.map.subpanel.socket_command;

import puyopuyo.client.ClientProcess;

public class MessageCommand implements SocketCommand {
    private static MessageCommand instance;

    private MessageCommand() {}

    public static synchronized MessageCommand getInstance() {
        if(instance == null) {
            instance = new MessageCommand();
        }
        return instance;
    }

    @Override
    public void execute(String json) {
        ClientProcess.getInstance().setPlayer(Integer.parseInt(json));
    }
}

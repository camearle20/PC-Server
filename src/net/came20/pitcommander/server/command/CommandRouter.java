package net.came20.pitcommander.server.command;

import net.came20.pitcommander.server.container.ChecklistContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cameronearle on 4/30/17.
 */
public class CommandRouter {
    public static Reply route(Command command) {
        Reply reply = null;
        List<HashMap<String, Object>> payload = new ArrayList<>();
        boolean value;
        String name;

        switch (command.getCommand()) {
            case FETCH_CHECKLIST:
                Map<String, Boolean> boxes = ChecklistContainer.getInstance().getMap();
                payload.add(new HashMap<>());
                payload.get(0).put("boxes", boxes);
                reply = new Reply(Replies.CHECKLIST_DATA, payload);
                break;
            case CHECKLIST_GET:
                value = ChecklistContainer.getInstance().getChecked((String) command.getPayload().get(0).get("name"));
                payload.add(new HashMap<>());
                payload.get(0).put("value", value);
                reply = new Reply(Replies.CHECKLIST_VALUE, payload);
                break;
            case CHECKLIST_ADD:
                name = (String) command.getPayload().get(0).get("name");
                value = (Boolean) command.getPayload().get(0).get("value");
                ChecklistContainer.getInstance().addCheckbox(name, value);
                reply = new Reply(Replies.GENERAL_ACK, null);
                break;
            case CHECKLIST_REMOVE:
                name = (String) command.getPayload().get(0).get("name");
                ChecklistContainer.getInstance().removeCheckbox(name);
                reply = new Reply(Replies.GENERAL_ACK, null);
                break;
            case CHECKLIST_SET:
                name = (String) command.getPayload().get(0).get("name");
                value = (Boolean) command.getPayload().get(0).get("value");
                ChecklistContainer.getInstance().setChecked(name, value);
                reply = new Reply(Replies.GENERAL_ACK, null);
                break;

        }
        return reply;

    }
}

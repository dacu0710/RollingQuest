package me.ranol.rollingquest.quest.commands;

import java.util.HashMap;
import java.util.List;

import me.ranol.rollingquest.quest.QuestMenu;
import me.ranol.rollingquest.util.StringParser;

public interface QuestCommand {
	static final HashMap<String, Class<? extends QuestCommand>> commands = new HashMap<>();

	public static void initialize() {
		register("show", CmdShow.class);
		register("hide", CmdHide.class);
		register("close", CmdCloseInv.class);
		register("chat", CmdChatMessage.class);
		register("cmd", CmdIssueCommand.class);
		register("cmdop", CmdIssueCommandOp.class);
		register("cmdcon", CmdIssueCommandConsole.class);
		register("tp", CmdTeleport.class);
	}

	public static void register(String name,
			Class<? extends QuestCommand> command) {
		commands.put(name, command);
	}

	public static QuestCommand createCommand(String args) {
		List<String> data = StringParser.parse(args);
		if (data.size() == 0)
			return null;
		QuestCommand command = getCommand(data.get(0));
		command.apply(data.subList(1, data.size()));
		return command;
	}

	public static QuestCommand getCommand(String name) {
		try {
			return commands.get(name).newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	public void apply(List<String> args);

	public void activate(QuestMenu menu);
}

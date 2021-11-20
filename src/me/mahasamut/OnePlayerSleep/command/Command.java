package me.mahasamut.OnePlayerSleep.command;

public class Command {

	private String command;
	private String permission;
	private CommandInterface commandInterface;

	public Command(String command, String permission, CommandInterface commandInterface) {
		this.command = command;
		this.permission = permission;
		this.commandInterface = commandInterface;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public CommandInterface getCommandInterface() {
		return commandInterface;
	}

	public void setCommandInterface(CommandInterface commandInterface) {
		this.commandInterface = commandInterface;
	}

}

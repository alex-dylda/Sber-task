package com.example.task.utils;

public class Parser {

    private Parser() {}

    public static String getSourceTableNameFromCommand(final String command) {
        final String[] splitCommand = splitCommand(command);
        return splitCommand[3].trim();
    }

    public static String getDestinationTableNameFromCommand(final String command) {
        final String[] splitCommand = splitCommand(command);

        return splitCommand[1].substring(0,splitCommand[1].indexOf('(')).trim();
    }

    public static String getColumnsFromCommand(final String command) {
        final String[] splitCommand = splitCommand(command);
        return splitCommand[2].trim();
    }

    private static String[] splitCommand(final String command) {
        final String[] splitCommand = command.split("insert into|select|from");
        if(splitCommand.length < 4) {
            throw new IllegalArgumentException("Command must have 'select' and 'from' sections");
        }
        return splitCommand;
    }
}

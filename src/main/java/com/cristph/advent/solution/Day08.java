package com.cristph.advent.solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.cristph.advent.utils.Pair;

/**
 * @author cristph
 * @date 2020/12/9 4:25 下午
 */
public class Day08 extends AbstractDaySolve {

    @Override
    protected String solvePuzzle1(String[] args) {
        Command[] commands = buildCommand(args);

        int value = 0;
        int pos = 0;
        Set<Integer> executedPos = new HashSet<>();

        while (!executedPos.contains(pos)) {
            Pair<Integer, Integer> res = executeAndGetNext(commands, pos, value);
            executedPos.add(pos);
            pos = res.getLeft();
            value = res.getRight();
        }
        return String.valueOf(value);
    }

    @Override
    protected String solvePuzzle2(String[] args) {
        Command[] commands = buildCommand(args);
        List<Integer> specialList = new ArrayList<>();
        for (int i = 0; i < commands.length; i++) {
            Command command = commands[i];
            if (CommandType.JMP == command.commandType || CommandType.NOP == command.commandType) {
                specialList.add(i);
            }
        }

        for (Integer specialPos : specialList) {
            Command[] commandsCopy = Command.copyOf(commands);
            commandsCopy[specialPos].commandType = commands[specialPos].commandType == CommandType.JMP ?
                CommandType.NOP : CommandType.JMP;

            int value = 0;
            int pos = 0;
            Set<Integer> executedPos = new HashSet<>();

            while (!executedPos.contains(pos) && pos < commands.length) {
                Pair<Integer, Integer> res = executeAndGetNext(commandsCopy, pos, value);
                executedPos.add(pos);
                pos = res.getLeft();
                value = res.getRight();
            }

            if (!executedPos.contains(pos)) {
                return String.valueOf(value);
            }

        }
        return null;
    }

    private Pair<Integer, Integer> executeAndGetNext(Command[] commands, int pos, int value) {
        Command command = commands[pos];
        switch (command.getCommandType()) {
            case NOP:
                return new Pair<>(pos + 1, value);
            case ACC:
                value = value + command.getValue();
                return new Pair<>(pos + 1, value);
            case JMP:
                return new Pair<>(pos + command.getValue(), value);
            default:
                throw new RuntimeException("command: " + command.toString() + " not exist.");
        }
    }

    public Command[] buildCommand(String[] args) {
        String fileName = args[0];
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            return br.lines().map(Command::build).collect(Collectors.toList()).toArray(new Command[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class Command implements Cloneable {

        private CommandType commandType;

        private int value;

        public static Command build(String line) {
            Command command = new Command();
            String[] tmp = line.split("\\s");
            CommandType commandType = CommandType.of(tmp[0]);
            Integer value = Integer.parseInt(tmp[1]);
            command.setCommandType(commandType);
            command.setValue(value);
            return command;
        }

        public static Command[] copyOf(Command[] commands) {
            Command[] commandsCopy = new Command[commands.length];
            for (int i = 0; i < commands.length; i++) {
                Command command = new Command();
                command.setCommandType(commands[i].commandType);
                command.setValue(commands[i].value);
                commandsCopy[i] = command;
            }
            return commandsCopy;
        }

        public CommandType getCommandType() {
            return commandType;
        }

        public void setCommandType(CommandType commandType) {
            this.commandType = commandType;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Command{" +
                "commandType=" + commandType +
                ", value=" + value +
                '}';
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            Command command = new Command();
            command.setCommandType(this.commandType);
            command.setValue(this.value);
            return command;
        }
    }

    public enum CommandType {

        NOP("nop"),

        ACC("acc"),

        JMP("jmp");

        private String value;

        private CommandType(String value) {
            this.value = value;
        }

        public static CommandType of(String value) {
            for (CommandType command : values()) {
                if (command.value.equals(value)) {
                    return command;
                }
            }
            return null;
        }
    }
}

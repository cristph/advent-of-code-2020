package com.cristph.advent.solution;

public abstract class AbstractDaySolve implements Solve {

    @Override
    public String solve(String[] args) {
        PuzzleResult puzzleResult = PuzzleResultBuilder.newBuilder()
                .append("puzzle1", solvePuzzle1(args))
                .append("puzzle2", solvePuzzle2(args))
                .build();
        return puzzleResult.toString();
    }

    /**
     * solve puzzle1
     *
     * @param args
     * @return
     */
    protected abstract String solvePuzzle1(String[] args);

    /**
     * solve puzzle2
     *
     * @param args
     * @return
     */
    protected abstract String solvePuzzle2(String[] args);

}

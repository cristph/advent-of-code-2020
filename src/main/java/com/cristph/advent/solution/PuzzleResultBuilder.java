package com.cristph.advent.solution;

import java.util.HashMap;

public class PuzzleResultBuilder {

    private PuzzleResult puzzleResult;

    private PuzzleResultBuilder() {
        puzzleResult = new PuzzleResult();
        puzzleResult.setPuzzleResults(new HashMap<>());
    }

    public static PuzzleResultBuilder newBuilder() {
        PuzzleResultBuilder puzzleResultBuilder = new PuzzleResultBuilder();
        return puzzleResultBuilder;
    }

    public PuzzleResultBuilder append(String key, String value) {
        puzzleResult.getPuzzleResults().put(key, value);
        return this;
    }

    public PuzzleResult build() {
        return this.puzzleResult;
    }
}

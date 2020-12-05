package com.cristph.advent.solution;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;


@Data
public class PuzzleResult implements Serializable {

    /**
     * key: No. of puzzle
     * value: result of puzzle
     */
    private Map<String, String> puzzleResults;

}

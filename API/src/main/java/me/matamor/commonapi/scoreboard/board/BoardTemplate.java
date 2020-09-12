package me.matamor.commonapi.scoreboard.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class BoardTemplate {

    @Getter
    private final BoardLine displayName;

    @Getter
    private final Map<Integer, BoardLine> lines;

}

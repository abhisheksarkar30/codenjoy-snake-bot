package com.codenjoy.dojo;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 - 2021 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.client.WebSocketRunner;

import static com.codenjoy.dojo.client.runner.ReflectLoader.loadJavaBoard;
import static com.codenjoy.dojo.client.runner.ReflectLoader.loadJavaSolver;

public class Runner {

    // Select your game
    public static String GAME = "snake";

    // Paste here board page url from browser after registration,
    // or put it as command line parameter.
    public static String URL =
            "http://127.0.0.1:8080/codenjoy-contest/board/player/qb7wyx9qv69ybrn5odoi?code=5864812366821104205&gameName=snake";

    public static void main(String[] args) {
        if (args != null && args.length == 2) {
            GAME = args[0];
            URL = args[1];
        }
        WebSocketRunner.runClient(URL, loadJavaSolver(GAME), loadJavaBoard(GAME));
    }
}

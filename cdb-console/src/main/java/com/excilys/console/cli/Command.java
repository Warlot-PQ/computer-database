package com.excilys.console.cli;

import java.util.Scanner;

/**
 * Interface used for command pattern.
 * @author pqwarlot
 *
 */
public interface Command {
	public void execute(Scanner input);
}
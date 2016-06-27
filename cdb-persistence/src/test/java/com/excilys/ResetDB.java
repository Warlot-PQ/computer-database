package com.excilys;

import java.io.IOException;

public class ResetDB {
	final static String RESET_DB_PATH_FILE = "../cdb-persistence/src/main/resources/DB/resetDB.sh";
	
	public static void setup() throws IOException, InterruptedException {
		String target = new String(RESET_DB_PATH_FILE);
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(target);
        proc.waitFor();
	}
	
	public static void setupTest() throws IOException, InterruptedException {
		String target = new String(RESET_DB_PATH_FILE);
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(target);
        proc.waitFor();
	}
}

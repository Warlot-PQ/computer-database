package com.excilys;

import java.io.IOException;

public class ResetDB {
	public static void setup() throws IOException, InterruptedException {
		String target = new String("src/main/resources/DB/resetDB.sh");
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(target);
        proc.waitFor();
	}
	
	public static void setupTest() throws IOException, InterruptedException {
		String target = new String("src/test/resources/DB/resetDB.sh");
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(target);
        proc.waitFor();
	}
}

package com.excilys;

import java.io.IOException;

public class ResetDB {
	public static void setup() throws IOException, InterruptedException {
		String target = new String("./src/test/resources/DB/resetTestDB.sh");
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(target);
        proc.waitFor();
	}
}

package com.excilys;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.excilys.beans.TestCompany;
import com.excilys.beans.TestComputer;
import com.excilys.service.TestCompanyService;
import com.excilys.service.TestComputerService;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestComputer.class, TestCompany.class, 
	TestComputerService.class, TestCompanyService.class })
public final class AllTestsRunner {
}
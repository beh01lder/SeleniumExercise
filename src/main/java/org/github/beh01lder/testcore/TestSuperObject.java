package org.github.beh01lder.testcore;

public class TestSuperObject {
	
	TestPageObject testPageObject;
	
	public TestPageObject testPageObject() {
		if (testPageObject == null) {
			testPageObject = new TestPageObject();
		}
		return testPageObject;
	}

}

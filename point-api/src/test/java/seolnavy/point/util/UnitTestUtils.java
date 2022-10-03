package seolnavy.point.util;

import org.springframework.test.context.transaction.TestTransaction;

public class UnitTestUtils {

	public static void endTransaction() {
		TestTransaction.flagForCommit(); // @TransactionalEventListener 의 동작까지 수행시킨다.
		TestTransaction.end();
	}
}

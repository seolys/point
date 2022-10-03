package seolnavy.point.util;

import org.springframework.test.context.transaction.TestTransaction;

public class TestTransactionUtils {

	public static void end() {
		TestTransaction.flagForCommit(); // @TransactionalEventListener 실행
		TestTransaction.end();
	}

	public static void endAndStart() {
		end();
		TestTransaction.start();
	}

}

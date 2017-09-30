package ph.kana.cpusched.job;

public final class JobNumberGenerator {

	private static int jobNumber = 0;

	public static int next() {
		return ++jobNumber;
	}
}

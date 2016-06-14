package stage;

public class TestClass {

	public static void main(String[] args) {
		MonThread t = new MonThread("A");
		MonThread t2 = new MonThread("  B", t);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("statut du thread " + t.getName() + " = " + t.getState());
		System.out.println("statut du thread " + t2.getName() + " = " +t2.getState());                
	}


}



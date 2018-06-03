package screenLocker.question;

public class MainTest {

	public static void main(String[] args) {
		Question q = new Question();
		System.out.println("Q:"+q.getqn());
		System.out.println("(A) "+q.getA());
		System.out.println("(B) "+q.getB());
		System.out.println("(C) "+q.getC());
		System.out.println("(D) "+q.getD());
		System.out.println();
		System.out.println("Answer:"+q.getans());
	
	}
}

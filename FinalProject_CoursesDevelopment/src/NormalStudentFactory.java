
public class NormalStudentFactory implements StudentFactory {
	public NormalStd createStudent(String studentName, int studentID, String major, int maxCredits, int majorNow, int mustCredit) {
        return new NormalStd(studentName, studentID, major, maxCredits, majorNow, mustCredit);
    }
}

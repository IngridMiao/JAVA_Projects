// ADStudentFactory.java
public class ADStudentFactory implements StudentFactory {
    @Override
    public NormalStd createStudent(String studentName, int studentID, String major, int maxCredits, int majorNow, int mustCredit) {
        return new ADStd(studentName, studentID, major, maxCredits, majorNow, mustCredit);
    }
}
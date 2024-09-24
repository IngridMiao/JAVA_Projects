// DMStudentFactory.java
public class DMStudentFactory implements StudentFactory {
    @Override
    public NormalStd createStudent(String studentName, int studentID, String major, int maxCredits, int majorNow, int mustCredit) {
        return new DMStd(studentName, studentID, major, maxCredits, majorNow, mustCredit);
    }
}
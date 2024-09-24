// StudentFactory.java
public interface StudentFactory {
    NormalStd createStudent(String studentName, int studentID, String major, int maxCredits, int majorNow, int mustCredit);
}

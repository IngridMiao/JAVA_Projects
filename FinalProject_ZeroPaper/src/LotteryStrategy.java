import java.sql.SQLException;
import java.util.ArrayList;

public interface LotteryStrategy {
	void lottery() throws SQLException;
    ArrayList<String> getArr();

}

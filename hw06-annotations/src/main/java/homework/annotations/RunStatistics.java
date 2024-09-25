/**
 * @author administrator on 25.09.2024.
 */
package homework.annotations;

public class RunStatistics {
    public int getCountSucess() {
	return countSucess;
    }

    public void setCountSucess(int countSucess) {
	this.countSucess = countSucess;
    }

    public int getCountError() {
	return countError;
    }

    public void setCountError(int countError) {
	this.countError = countError;
    }

    private int countSucess = 0;
    private int countError = 0;
}

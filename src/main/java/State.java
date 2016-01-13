/**
 * Created by josepher on 1/12/16.
 */
public class State {
    private Word[] columns;

    public State() {
        columns = new Word[4];
        columns[0] = new Word();
        columns[1] = new Word();
        columns[2] = new Word();
        columns[3] = new Word();
    }

    public Word getColumn(int idx) {
        return columns[idx];
    }

    public void setColumn(int idx, Word column) {
        columns[idx] = column;
    }

    public String toString() {
        return columns[0].toString() +
               columns[1].toString() +
               columns[2].toString() +
               columns[3].toString();
    }
}

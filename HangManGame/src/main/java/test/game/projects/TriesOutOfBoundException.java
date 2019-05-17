package test.game.projects;

public class TriesOutOfBoundException extends Exception {

    private int attempts;
    public TriesOutOfBoundException(int attempts)
    {
        this.attempts = attempts;
    }

    public TriesOutOfBoundException(int attempts,Throwable errMsg)
    {
        super(String.valueOf(attempts), errMsg);
    }
    public double getAttempts()
    {
        return attempts;
    }

}

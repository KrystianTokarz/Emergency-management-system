package server.message.command;

/**
 * Interface belonging to the command pattern with 1 method - execute
 * @param <T> Returned result execution
 */
public interface Command<T>{

    public <S> T execute(S param);
}

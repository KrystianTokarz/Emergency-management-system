package server.message.command;

public interface Command{

    public <S> void execute(S param);


}

package learn.gamer.domain;


import java.util.ArrayList;
import java.util.List;

public class Result<T> {
    private final List<String> messages = new ArrayList<>();
    private T payload;
    private ResultType resultType = ResultType.SUCCESS;

    public ResultType getResultType() {
        return resultType;
    }

    public boolean isSuccess(){
        return resultType == ResultType.SUCCESS;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public List<String> getMessages(){
        return new ArrayList<>(messages);
    }

    public void addMessage(String message, ResultType resultType){
        messages.add(message);
        this.resultType = resultType;
    }





}
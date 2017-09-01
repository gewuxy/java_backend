package cn.medcn.common.excptions;

/**
 * Created by lixuan on 2017/5/17.
 */
public class NotEnoughCreditsException extends Exception {

    public NotEnoughCreditsException(){

    }

    public NotEnoughCreditsException(String message){
        super(message);
    }
}

package cn.medcn.common.excptions;

/**
 * Created by Liuchangling on 2017/9/27.
 */

public class PasswordErrorException extends Exception{

    public PasswordErrorException(){

    }

    public PasswordErrorException(String message){
        super(message);
    }
}

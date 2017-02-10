package MyHttp.Request;

import MyHttp.Exception.MethodNotAllowException;


/**
 * Created by wyy on 17-2-9.
 */
public enum HttpMethod {
    /*
     todo
    先完成get
     */
    GET, POST;//将来可以扩充更多的类型

    public static HttpMethod Parse(String method) throws MethodNotAllowException {
        switch (method.toUpperCase()) {
            case "GET":
                return GET;
            case "POST":
                return POST;

        }
        throw new MethodNotAllowException();
    }

}

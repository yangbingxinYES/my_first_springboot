package exception;

import lombok.Data;

/**
 *自定义异常类 .
 *
 * @author 杨冰鑫
 * @since 2019/8/11 14:21
 */
@Data
public class MyException extends RuntimeException{

        public MyException(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        private String code;
        private String msg;

}

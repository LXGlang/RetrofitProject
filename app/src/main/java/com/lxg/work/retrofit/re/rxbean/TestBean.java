package com.lxg.work.retrofit.re.rxbean;

/**
 * @ProjectName: RetrofitProject
 * @Package: com.lxg.work.retrofit.re.rxbean
 * @ClassName: TestBean
 * @Description:
 * @Author: lxg
 * @CreateDate: 2020/5/22 10:29
 */
public class TestBean {
    public String message;

    public TestBean(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

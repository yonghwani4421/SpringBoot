package me.yonghwan.jwt.dto;

import lombok.Getter;
import lombok.Setter;
import me.yonghwan.jwt.domain.User;

@Getter
@Setter
public class AddUserResponse {
    private int code;
    private User resBody;

    public AddUserResponse(int code, User resBody) {
        this.code = code;
        this.resBody = resBody;
    }
}

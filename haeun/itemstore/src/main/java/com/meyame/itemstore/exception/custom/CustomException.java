package com.meyame.itemstore.exception.custom;

import com.meyame.itemstore.exception.ErrorInterface;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorInterface errorInterface;

    public CustomException(ErrorInterface errorInterface) {
        super(errorInterface.getMessage());
        this.errorInterface = errorInterface;
    }
}

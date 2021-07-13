package com.scut.library.utils;

import lombok.Data;

@Data
public class RequestBookID {
    private String book_id;

    public String getBook_id() {
        return book_id;
    }
}

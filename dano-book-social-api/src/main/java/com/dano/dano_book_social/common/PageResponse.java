package com.dano.dano_book_social.common;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse <T> {
    
    private List<T> data;
    private int pageNumber;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
}

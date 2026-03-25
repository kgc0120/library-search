package com.library.controller.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchRequest {
    @NotBlank(message = "입력은 비어있을 수 없습니다.")
    @Size(max = 50, message = "query는 최대 50자까지 입력 가능합니다.")
    private String query;

    @NotNull(message = "페이지 번호는 필수입니다.")
    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
    @Max(value = 10000, message = "페이지 번호는 최대 50까지 입력 가능합니다.")
    private Integer page;

    @NotNull(message = "페이지 번호는 필수입니다.")
    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
    @Max(value = 50, message = "페이지 번호는 최대 50까지 입력 가능합니다.")
    private Integer size;
}

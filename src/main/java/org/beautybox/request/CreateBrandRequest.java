package org.beautybox.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateBrandRequest {
    @JsonProperty(value = "brandName")
            @NotBlank(message = "Brand name field is not blank")
    String name;
}

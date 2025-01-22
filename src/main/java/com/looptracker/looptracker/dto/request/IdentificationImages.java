package com.looptracker.looptracker.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class IdentificationImages {
    private MultipartFile cccdFrontImage;
    private MultipartFile cccdBackImage;
    private MultipartFile gplxFrontImage;
    private MultipartFile gplxBackImage;
}

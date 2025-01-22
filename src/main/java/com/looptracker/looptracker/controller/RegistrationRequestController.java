package com.looptracker.looptracker.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.looptracker.looptracker.dto.RegistrationRequestDto;
import com.looptracker.looptracker.dto.request.IdentificationImages;
import com.looptracker.looptracker.dto.request.SigninForm;
import com.looptracker.looptracker.service.IRegistrationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/registration-request")
public class RegistrationRequestController {

    @Autowired
    private IRegistrationRequestService registrationRequestService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("easy-rider")
    public ResponseEntity<?> registrationRequest(@RequestPart("registrationRequestJson") String registrationRequestJson,
                                                 @RequestPart("fileCCCDInputFront") MultipartFile fileCCCDInputFront,
                                                 @RequestPart("fileCCCDInputBack") MultipartFile fileCCCDInputBack,
                                                 @RequestPart("fileGPLXInputFront") MultipartFile fileGPLXInputFront,
                                                 @RequestPart("fileGPLXInputBack") MultipartFile fileGPLXInputBack) throws Exception {

        IdentificationImages identificationImages = new IdentificationImages();
        identificationImages.setCccdBackImage(fileCCCDInputBack);
        identificationImages.setCccdFrontImage(fileCCCDInputFront);
        identificationImages.setGplxBackImage(fileGPLXInputBack);
        identificationImages.setGplxFrontImage(fileGPLXInputFront);

        RegistrationRequestDto registrationRequestDto = objectMapper.readValue(registrationRequestJson, RegistrationRequestDto.class);
        registrationRequestService.registrationRequest(registrationRequestDto,identificationImages);
        return ResponseEntity.ok("Gửi yêu cầu thành công");
    }

    @GetMapping("get-all")
    public ResponseEntity<?> getAllRegistrationRequests(Pageable pageable,
                                                        @RequestParam(required = false) String search,
                                                        @RequestParam(required = false) String status) {
        return ResponseEntity.ok(registrationRequestService.getRegistrationRequests(search,status,pageable));
    }

    @GetMapping("validate-form")
    public ResponseEntity<?> validateForm(@RequestParam(required = false) String email,
                                          @RequestParam(required = false) String phoneNumber,
                                          @RequestParam(required = false) String cccdNumber,
                                          @RequestParam(required = false) String gplxNumber) {
        registrationRequestService.validateForm(email,phoneNumber,cccdNumber,gplxNumber);
        return ResponseEntity.ok(true);

    }

    @GetMapping("total")
    public ResponseEntity<?> getAllRegistrationRequests() {
        return ResponseEntity.ok(registrationRequestService.getTotal());
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody SigninForm login) {
        return null;
    }

    @PostMapping("confirm")
    public ResponseEntity<?> confirm(@RequestPart("requestId") String requestId) {
        registrationRequestService.confirm(requestId);
        return ResponseEntity.ok("ok");
    }

    @PutMapping("reject")
    public ResponseEntity<?> reject(@RequestPart("requestId") String requestId) {
        registrationRequestService.reject(requestId);
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam(value = "id") String requestId) throws Exception {
        registrationRequestService.delete(requestId);
        return ResponseEntity.ok("ok");
    }
}

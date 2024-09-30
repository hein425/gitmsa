package com.pmh.ex10.freeboard;

import com.pmh.ex10.error.BizException;
import com.pmh.ex10.error.ErrorCode;
import com.pmh.ex10.file.FileEntity;
import com.pmh.ex10.file.FileRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("freeboard")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class FreeBoardController {

    private final FreeBoardRepository freeBoardRepository;
    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;

    @Value("${my.value}")
    private String welcome;

    @GetMapping("test")
    public String test() {
        return welcome;
    }

    @GetMapping
    public ResponseEntity<FreeBoardResponsePageDto> findALl(@RequestParam(name = "pageNum", defaultValue = "0") int pageNum
            , @RequestParam(name = "size", defaultValue = "5") int size) {
        // select * from freeboard oder by idx desc, name desc,
        Sort sort = Sort.by(Sort.Direction.DESC, "idx");

        Pageable pageable = PageRequest.of(pageNum, size, sort);

        // Page List
        Page<FreeBoard> page = freeBoardRepository.findAll(pageable);

        //System.out.println("elements = " + page.getTotalElements());
        //System.out.println("pages = " + page.getTotalPages());

        FreeBoardResponsePageDto freeBoardResponsePageDto = modelMapper.map(page, FreeBoardResponsePageDto.class);

        List<FreeBoardResponseDto> list = new ArrayList<>();

        for (FreeBoard freeBoard : freeBoardResponsePageDto.getContent()) {
            FreeBoardResponseDto freeBoardResponseDto
                    = new ModelMapper().map(freeBoard, FreeBoardResponseDto.class);

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy/MM/dd hh:mm");

            freeBoardResponseDto.setRegDate(dateTimeFormatter.format(freeBoard.getRegDate()));
            freeBoardResponseDto.setModDate(dateTimeFormatter.format(freeBoard.getModDate()));

            list.add(freeBoardResponseDto);
        }

        freeBoardResponsePageDto.setList(list);

        return ResponseEntity.ok(freeBoardResponsePageDto);
    }

    @GetMapping("view/{idx}") // 뷰 하는 뷰분
    public ResponseEntity<FreeBoardResponseDto> findOne(@PathVariable(name = "idx") long idx) {
        //
        FreeBoard freeBoard = freeBoardRepository.findById(idx).orElseThrow(() -> new BizException(ErrorCode.NOT_FOUND));
        freeBoard.setViewCount(freeBoard.getViewCount()+1);
        freeBoardRepository.save(freeBoard);


        FreeBoardResponseDto freeBoardResponseDto = modelMapper.map(freeBoard, FreeBoardResponseDto.class);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy/MM/dd hh:mm");
        freeBoardResponseDto.setRegDate(dateTimeFormatter.format(freeBoard.getRegDate()));
        freeBoardResponseDto.setModDate(dateTimeFormatter.format(freeBoard.getModDate()));

        return ResponseEntity.ok(freeBoardResponseDto);
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<FreeBoard> save(
            @Valid @RequestPart(name = "data") FreeBoardReqDto freeBoardReqDto,
            @RequestPart(name = "file", required = false) MultipartFile file) {

        FreeBoard freeBoard = new ModelMapper().map(freeBoardReqDto, FreeBoard.class);
        freeBoardRepository.save(freeBoard);  // 글 수정하는 로직

        if (file != null) {
            String myFilePath = Paths.get("images/file/").toAbsolutePath() + "\\" + file.getOriginalFilename();
            try {
                File destFile = new File(myFilePath);
                file.transferTo(destFile);
            }catch (Exception e){
                e.printStackTrace();
            } // 파일 저장하는 로직
            FileEntity fileEntity = new FileEntity();
            fileEntity.setName(file.getOriginalFilename());
            fileEntity.setPath(Paths.get("images/file/").toAbsolutePath().toString());
            fileEntity.setFreeBoard(freeBoard);
            fileRepository.save(fileEntity); // 파일 행 삽입 하는 로직
        }
        return ResponseEntity.status(200).body(freeBoard);

    }


    @DeleteMapping("delete/{idx}")
    public ResponseEntity<String> deleteById(@PathVariable(name = "idx") long idx) {
        freeBoardRepository.findById(idx).orElseThrow(() -> new BizException(ErrorCode.NOT_FOUND));
        freeBoardRepository.deleteById(idx);
        return ResponseEntity.ok("삭제되었습니다.");
    }


}

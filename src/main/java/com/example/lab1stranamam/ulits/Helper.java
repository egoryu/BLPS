package com.example.lab1stranamam.ulits;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.example.lab1stranamam.dto.ResponseRecordDto;
import com.example.lab1stranamam.entity.Record;

public class Helper {
    public static List<ResponseRecordDto> sortRecords(List<ResponseRecordDto> records, String sortBy, String sortOrder) {
        Comparator<ResponseRecordDto> comparator = createComparator(sortBy, sortOrder);
        return records.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private static Comparator<ResponseRecordDto> createComparator(String sortBy, String sortOrder) {
        Comparator<ResponseRecordDto> comparator;
        switch (sortBy) {
            case "createTime":
                comparator = Comparator.comparing(ResponseRecordDto::getTime);
                break;
            case "rating":
                comparator = Comparator.comparing(ResponseRecordDto::getRating);
                break;
            case "header":
                comparator = Comparator.comparing(ResponseRecordDto::getHeader);
                break;
            default:
                comparator = Comparator.comparing(ResponseRecordDto::getUserId);
                break;
        }
        if (sortOrder.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
}

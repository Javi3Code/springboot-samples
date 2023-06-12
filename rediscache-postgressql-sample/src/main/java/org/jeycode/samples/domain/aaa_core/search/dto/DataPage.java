package org.jeycode.samples.domain.aaa_core.search.dto;

public record DataPage<T>(int page, int total, boolean hasPrevious, boolean hasNext, T data) {

}

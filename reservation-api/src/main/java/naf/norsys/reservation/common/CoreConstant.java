package naf.norsys.reservation.common;

import org.springframework.data.domain.Sort;

public class CoreConstant {

    public static class Exception {
        public static final String DEFAULT = "message.exception.default";
        public static final String NOT_FOUND = "message.exception.not.found.element";
        public static final String ALREADY_EXISTS = "message.exception.already.exists.element";
        public static final String DELETE_ELEMENT = "message.exception.delete.element";
        public static final String FIND_ELEMENTS = "message.exception.find.elements";
        public static final String NOT_AVAILABLE = "message.exception.element.availability";
        public static final String INVALID_STATUS = "message.exception.element.availability.invalid-status";
        public static final String PAGINATION_PAGE_NUMBER = "message.exception.pagination.page.min";
        public static final String PAGINATION_PAGE_SIZE_MIN = "message.exception.pagination.size.min";
        public static final String PAGINATION_PAGE_SIZE_MAX = "message.exception.pagination.size.max";


    }

    public static class Pagination {
        public static final int DEFAULT_PAGE_NUMBER = 0;
        public static final int DEFAULT_PAGE_SIZE = 20;
        public static final int MAX_PAGE_SIZE = 20;
        public static final String DEFAULT_SORT_PROPERTY = "createdAt";
        public static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.DESC;
    }

}

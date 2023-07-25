package naf.norsys.reservation.dto;

import lombok.*;
import naf.norsys.reservation.exception.BadRequestException;
import naf.norsys.reservation.model.GenericEnum.ItemCategory;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import static naf.norsys.reservation.common.CoreConstant.Exception.*;
import static naf.norsys.reservation.common.CoreConstant.Pagination.*;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchItemDto {

    private String keyword;
    @Getter
    private ItemCategory category;

    private int page;
    private int size;

    public Integer getPage() {
        return (Objects.isNull(page) ? DEFAULT_PAGE_NUMBER : page);
    }

    public Integer getSize() {
        return (Objects.isNull(size) ? DEFAULT_PAGE_SIZE : size);
    }

    public String getKeyword() {
        return (Objects.isNull(keyword) ? StringUtils.EMPTY : keyword);
    }

    public void validate() throws BadRequestException {


        if (Objects.nonNull(page))
            if (this.page < 0)
                throw new BadRequestException(null, new BadRequestException(), PAGINATION_PAGE_NUMBER, null);

        if (Objects.nonNull(size)) {
            if (this.size <= 0)
                throw new BadRequestException(null, new BadRequestException(), PAGINATION_PAGE_SIZE_MIN, null);

            if (this.size > MAX_PAGE_SIZE)
                throw new BadRequestException(null, new BadRequestException(), PAGINATION_PAGE_SIZE_MAX, new Object[]{MAX_PAGE_SIZE});
        }
    }



}

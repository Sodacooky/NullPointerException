package soda.npe.servicemiscellaneous.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class HotCategoriesVO implements Serializable {

    private Long id;

    private String category;

    private Long amount;

}

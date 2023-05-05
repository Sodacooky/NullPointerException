package soda.npe.servicearticle.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModifyArticleVO implements Serializable {
    private Long id;
    private String title;
    private String category;
    private String text;
}

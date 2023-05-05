package soda.npe.servicearticle.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModifyReplyVO implements Serializable {
    private Long id;
    private String text;
}

package soda.npe.servicequestion.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModifyQuestionInfoVO implements Serializable {

    private Long id;

    private String title;

    private String category;

    private String text;


}

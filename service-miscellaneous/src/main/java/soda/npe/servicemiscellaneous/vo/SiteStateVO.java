package soda.npe.servicemiscellaneous.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SiteStateVO implements Serializable {

    private Long todayQuestionAmount;

    private Long todayArticleAmount;

    private Long totalUserAmount;

}

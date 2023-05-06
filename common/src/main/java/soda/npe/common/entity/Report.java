package soda.npe.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户举报
 *
 * @TableName report
 */
@TableName(value = "report")
@Data
public class Report implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 举报记录ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 被举报的用户的ID
     */
    private Long goalUserId;
    /**
     * 被举报的文章ID
     */
    private Long goalArticleId;
    /**
     * 被举报的文章回复ID
     */
    private Long goalArticleReplyId;
    /**
     * 被举报的问题ID
     */
    private Long goalQuestionId;
    /**
     * 被举报的回答ID
     */
    private Long goalQuestionAnswerId;
    /**
     * 举报时间
     */
    private Date time;
    /**
     * 举报附加信息
     */
    private String comment;
    /**
     * 是否已经处理
     */
    private Integer isProcessed;
    /**
     * 举报者的用户ID
     */
    private Long reporterId;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Report other = (Report) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getGoalUserId() == null ? other.getGoalUserId() == null : this.getGoalUserId().equals(other.getGoalUserId()))
                && (this.getGoalArticleId() == null ? other.getGoalArticleId() == null : this.getGoalArticleId().equals(other.getGoalArticleId()))
                && (this.getGoalArticleReplyId() == null ? other.getGoalArticleReplyId() == null : this.getGoalArticleReplyId().equals(other.getGoalArticleReplyId()))
                && (this.getGoalQuestionId() == null ? other.getGoalQuestionId() == null : this.getGoalQuestionId().equals(other.getGoalQuestionId()))
                && (this.getGoalQuestionAnswerId() == null ? other.getGoalQuestionAnswerId() == null : this.getGoalQuestionAnswerId().equals(other.getGoalQuestionAnswerId()))
                && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
                && (this.getComment() == null ? other.getComment() == null : this.getComment().equals(other.getComment()))
                && (this.getIsProcessed() == null ? other.getIsProcessed() == null : this.getIsProcessed().equals(other.getIsProcessed()))
                && (this.getReporterId() == null ? other.getReporterId() == null : this.getReporterId().equals(other.getReporterId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGoalUserId() == null) ? 0 : getGoalUserId().hashCode());
        result = prime * result + ((getGoalArticleId() == null) ? 0 : getGoalArticleId().hashCode());
        result = prime * result + ((getGoalArticleReplyId() == null) ? 0 : getGoalArticleReplyId().hashCode());
        result = prime * result + ((getGoalQuestionId() == null) ? 0 : getGoalQuestionId().hashCode());
        result = prime * result + ((getGoalQuestionAnswerId() == null) ? 0 : getGoalQuestionAnswerId().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getComment() == null) ? 0 : getComment().hashCode());
        result = prime * result + ((getIsProcessed() == null) ? 0 : getIsProcessed().hashCode());
        result = prime * result + ((getReporterId() == null) ? 0 : getReporterId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", goalUserId=").append(goalUserId);
        sb.append(", goalArticleId=").append(goalArticleId);
        sb.append(", goalArticleReplyId=").append(goalArticleReplyId);
        sb.append(", goalQuestionId=").append(goalQuestionId);
        sb.append(", goalQuestionAnswerId=").append(goalQuestionAnswerId);
        sb.append(", time=").append(time);
        sb.append(", comment=").append(comment);
        sb.append(", isProcessed=").append(isProcessed);
        sb.append(", reporterId=").append(reporterId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
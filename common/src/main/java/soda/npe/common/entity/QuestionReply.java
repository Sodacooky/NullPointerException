package soda.npe.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 对问题中的回答或其他回复的回复
 *
 * @TableName question_reply
 */
@TableName(value = "question_reply")
@Data
public class QuestionReply implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 回复的ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 目标问题ID，如果回复的是其他回复请保持为NULL
     */
    private Long goalAnswerId;
    /**
     * 回复内容正文
     */
    private String text;
    /**
     * 回复发布者ID
     */
    private Long publisherId;
    /**
     * 回复发布时间
     */
    private Date publishTime;

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
        QuestionReply other = (QuestionReply) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getGoalAnswerId() == null ? other.getGoalAnswerId() == null : this.getGoalAnswerId().equals(other.getGoalAnswerId()))
                && (this.getText() == null ? other.getText() == null : this.getText().equals(other.getText()))
                && (this.getPublisherId() == null ? other.getPublisherId() == null : this.getPublisherId().equals(other.getPublisherId()))
                && (this.getPublishTime() == null ? other.getPublishTime() == null : this.getPublishTime().equals(other.getPublishTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGoalAnswerId() == null) ? 0 : getGoalAnswerId().hashCode());
        result = prime * result + ((getText() == null) ? 0 : getText().hashCode());
        result = prime * result + ((getPublisherId() == null) ? 0 : getPublisherId().hashCode());
        result = prime * result + ((getPublishTime() == null) ? 0 : getPublishTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", goalAnswerId=").append(goalAnswerId);
        sb.append(", text=").append(text);
        sb.append(", publisherId=").append(publisherId);
        sb.append(", publishTime=").append(publishTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
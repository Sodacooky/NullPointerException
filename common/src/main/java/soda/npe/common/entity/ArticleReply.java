package soda.npe.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 文章回复
 * @TableName article_reply
 */
@TableName(value ="article_reply")
@Data
public class ArticleReply implements Serializable {
    /**
     * 回复ID
     */
    @TableId
    private Long id;

    /**
     * 回复的目标文章ID，如果回复的是其他回复请保持NULL
     */
    private Long goalArticleId;

    /**
     * 回复的内容正文
     */
    private String text;

    /**
     * 回复者ID
     */
    private Long publisherId;

    /**
     * 发布时间
     */
    private Date publishTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
        ArticleReply other = (ArticleReply) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGoalArticleId() == null ? other.getGoalArticleId() == null : this.getGoalArticleId().equals(other.getGoalArticleId()))
            && (this.getText() == null ? other.getText() == null : this.getText().equals(other.getText()))
            && (this.getPublisherId() == null ? other.getPublisherId() == null : this.getPublisherId().equals(other.getPublisherId()))
            && (this.getPublishTime() == null ? other.getPublishTime() == null : this.getPublishTime().equals(other.getPublishTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGoalArticleId() == null) ? 0 : getGoalArticleId().hashCode());
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
        sb.append(", goalArticleId=").append(goalArticleId);
        sb.append(", text=").append(text);
        sb.append(", publisherId=").append(publisherId);
        sb.append(", publishTime=").append(publishTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
package soda.npe.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户消息
 * @TableName user_notice
 */
@TableName(value ="user_notice")
@Data
public class UserNotice implements Serializable {
    /**
     * 消息id
     */
    @TableId
    private String id;

    /**
     * 接收消息的用户的ID
     */
    private String goalUserId;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 正文
     */
    private String text;

    /**
     * 通知时间
     */
    private Date time;

    /**
     * 是否已读消息
     */
    private Integer isRead;

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
        UserNotice other = (UserNotice) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGoalUserId() == null ? other.getGoalUserId() == null : this.getGoalUserId().equals(other.getGoalUserId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getText() == null ? other.getText() == null : this.getText().equals(other.getText()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getIsRead() == null ? other.getIsRead() == null : this.getIsRead().equals(other.getIsRead()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGoalUserId() == null) ? 0 : getGoalUserId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getText() == null) ? 0 : getText().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getIsRead() == null) ? 0 : getIsRead().hashCode());
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
        sb.append(", title=").append(title);
        sb.append(", text=").append(text);
        sb.append(", time=").append(time);
        sb.append(", isRead=").append(isRead);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
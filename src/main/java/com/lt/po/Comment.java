package com.lt.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    @Temporal(TemporalType.TIMESTAMP)   //因为字段对应的是Date类型的时间想要在数据库生成 需要加上这个注解
    private Date createTime;

    @ManyToOne
    private Blog blog;

    /**
     * 配置自动关联关系  一个评论 可以被多个人回复
     */
    //一个父   多个子类
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments=new ArrayList<>();
    //多个 子 只有一个父类
    @ManyToOne
    private Comment parentComment;

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}

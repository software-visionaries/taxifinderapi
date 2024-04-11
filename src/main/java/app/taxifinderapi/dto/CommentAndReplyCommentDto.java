package app.taxifinderapi.dto;

import java.util.List;

public class CommentAndReplyCommentDto {
    private CommentDto commentDto;

    private List<ReplyCommentDto> commentDtoList;

    public CommentAndReplyCommentDto(CommentDto commentDto, List<ReplyCommentDto> commentDtoList) {
        this.commentDto = commentDto;
        this.commentDtoList = commentDtoList;
    }

    public CommentDto getCommentDto() {
        return commentDto;
    }

    public void setCommentDto(CommentDto commentDto) {
        this.commentDto = commentDto;
    }

    public List<ReplyCommentDto> getCommentDtoList() {
        return commentDtoList;
    }

    public void setCommentDtoList(List<ReplyCommentDto> commentDtoList) {
        this.commentDtoList = commentDtoList;
    }
}

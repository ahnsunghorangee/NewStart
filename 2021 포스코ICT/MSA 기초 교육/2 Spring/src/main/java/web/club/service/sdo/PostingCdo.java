package web.club.service.sdo;

import web.club.entity.board.Posting;
import web.club.entity.board.SocialBoard;
import web.club.util.helper.DateUtil;

public class PostingCdo {

    private String usid;
    private String title;
    private String writerEmail;
    private String contents;
    private String writtenDate;
    private int readCount;

    public PostingCdo() {
        //
        this.readCount = 0;
    }

    public PostingCdo(String title, String writerEmail, String contents) {
        //
        this();
        this.title = title;
        this.writerEmail = writerEmail;
        this.contents = contents;
        this.writtenDate = DateUtil.today();
    }

    public PostingCdo(Posting posting) {
        //
        this.usid = posting.getUsid();
        this.title = posting.getTitle();
        this.writerEmail = posting.getWriterEmail();
        this.contents = posting.getContents();
        this.writtenDate = posting.getWrittenDate();
        this.readCount = posting.getReadCount();
    }

    public Posting toPosting(){
        Posting posting = new Posting();
        posting.setBoardId(this.getUsid());
        posting.setTitle(this.getTitle());
        posting.setContents(this.getContents());
        posting.setWriterEmail(this.getWriterEmail());

        posting.setWrittenDate(DateUtil.today());

        return posting;
    }

    @Override
    public String toString() {
        //
        StringBuilder builder = new StringBuilder();
        builder.append("Posting id: " + usid);
        builder.append(", title: " + title);
        builder.append(", writer email: " + writerEmail);
        builder.append(", read count: " + readCount);
        builder.append(", written date: " + writtenDate);
        builder.append(", contents: " + contents);

        return builder.toString();
    }

    public Posting toPostingIn(SocialBoard board) {
        //
        Posting posting = new Posting(board, title, writerEmail, contents);
        posting.setWrittenDate(writtenDate);
        posting.setReadCount(readCount);
        return posting;
    }

    public Posting toPostingIn(String postingId, String boardId) {
        //
        Posting posting = new Posting(postingId,boardId,writerEmail, title, contents);
        posting.setWrittenDate(writtenDate);
        posting.setReadCount(readCount);
        return posting;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriterEmail() {
        return writerEmail;
    }

    public void setWriterEmail(String writerEmail) {
        this.writerEmail = writerEmail;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        //
        this.contents = contents;
    }

    public String getWrittenDate() {
        return writtenDate;
    }

    public void setWrittenDate(String writtenDate) {
        this.writtenDate = writtenDate;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }
}

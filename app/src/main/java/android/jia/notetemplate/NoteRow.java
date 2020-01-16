package android.jia.notetemplate;

public class NoteRow {
    public String title;
    public String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public NoteRow(String title, String body) {
        this.title = title;
        this.body = body;
    }
}


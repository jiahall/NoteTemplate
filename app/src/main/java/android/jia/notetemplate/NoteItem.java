package android.jia.notetemplate;

public class NoteItem {
    private String name;
    private String body;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void changeName(String text){
        name = text;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public NoteItem(String name, String body) {
        this.name = name;
        this.body = body;
    }
}

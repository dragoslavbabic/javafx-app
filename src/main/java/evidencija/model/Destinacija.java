package evidencija.model;

public class Destinacija {

    private int id;
    private String grad;

    public Destinacija() {
    }

    public Destinacija(int id, String grad) {
        this.id = id;
        this.grad = grad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }
}

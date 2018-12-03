package py.com.test.nurseweb.model.res;

/**
 * @author mlopez
 * @fecha 11/29/18,4:54 PM
 */


public class RangoPresionArterial {
    private Integer id;
    private String categoria;
    private String sistolica;
    private String diastolica;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSistolica() {
        return sistolica;
    }

    public void setSistolica(String sistolica) {
        this.sistolica = sistolica;
    }

    public String getDiastolica() {
        return diastolica;
    }

    public void setDiastolica(String diastolica) {
        this.diastolica = diastolica;
    }

}

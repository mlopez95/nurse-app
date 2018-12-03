package py.com.test.nurseweb.model.res;

import java.util.List;

/**
 * @author mlopez
 * @fecha 11/29/18,4:35 PM
 */


public class ListaSignosVitales {

    private List<SignosVitalesDetails> signosVitales;

    public List<SignosVitalesDetails> getSignosVitales() {
        return signosVitales;
    }

    public void setSignosVitales(List<SignosVitalesDetails> signosVitales) {
        this.signosVitales = signosVitales;
    }
}

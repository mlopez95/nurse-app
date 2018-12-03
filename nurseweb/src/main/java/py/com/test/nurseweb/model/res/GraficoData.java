package py.com.test.nurseweb.model.res;

/**
 * @author mlopez
 * @fecha 12/1/18,3:18 PM
 */


public class GraficoData {

    private Integer y;
    private String indexLabel;
    private String markerColor;
    private String markerType ;

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getIndexLabel() {
        return indexLabel;
    }

    public void setIndexLabel(String indexLabel) {
        this.indexLabel = indexLabel;
    }

    public String getMarkerColor() {
        return markerColor;
    }

    public void setMarkerColor(String markerColor) {
        this.markerColor = markerColor;
    }

    public String getMarkerType() {
        return markerType;
    }

    public void setMarkerType(String markerType) {
        this.markerType = markerType;
    }
}

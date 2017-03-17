package main.java.core.vo;

import java.util.Date;
import java.util.List;

/**
 * Created by digvijaysharma on 18/12/16.
 */
public class PrescriptionVO {

    public enum Type {
        PDF,
        IMG,
        DOC,
        LIST
    };

    List<MedicineVO> medicines;

    private String type;

    private String notes;

    private String fileLink;

    private Date created;

    private Date updated;

    public List<MedicineVO> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<MedicineVO> medicines) {
        this.medicines = medicines;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}

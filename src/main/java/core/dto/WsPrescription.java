package main.java.core.dto;

import java.util.List;
import main.java.core.vo.MedicineVO;
import main.java.core.vo.PrescriptionVO;

/**
 * Created by digvijaysharma on 15/01/17.
 */
public class WsPrescription {

    List<WsMedicine> medicines;

    private String type;

    private String notes;

    private String fileLink;

    public WsPrescription() {

    }

    public WsPrescription(PrescriptionVO prescriptionVO) {
        this.type = prescriptionVO.getType();
        this.notes = prescriptionVO.getNotes();
        this.fileLink = prescriptionVO.getFileLink();
        for(MedicineVO medicineVO : prescriptionVO.getMedicines()) {
            this.medicines.add(new WsMedicine(medicineVO));
        }
    }

    public List<WsMedicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<WsMedicine> medicines) {
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
}

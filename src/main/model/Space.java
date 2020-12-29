package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a space with a number.
public class Space implements Writable {

    private Boolean isVacancy;
    private int num;

    // Constructor
    // REQUIRES: num must be >=1
    // EFFECTS: Create a space with num and isVacancy
    //          And isVacancy set to be true;
    public Space(int num) {
        isVacancy = true;
        this.num = num;
    }

    public void setIsVacancy(Boolean iv) {
        isVacancy = iv;
    }

    public Boolean getIsVacancy() {
        return isVacancy;
    }

    public int getNum() {
        return num;
    }

    // Follow the format in JsonSerializationDemo
    // EFFECTS: return this space as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("isVacancy", isVacancy);
        json.put("num", num);
        return json;
    }
}

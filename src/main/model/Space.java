package model;

import org.json.JSONObject;

// Represents a space with a number.
public class Space {

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

    // MODIFIES: this
    // EFFECTS: Set isVacancy to be iv.
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
    public JSONObject spaceToJson() {
        JSONObject json = new JSONObject();
        json.put("isVacancy", isVacancy);
        json.put("num", num);
        return json;
    }
}

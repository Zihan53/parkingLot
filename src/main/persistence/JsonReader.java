package persistence;

import exception.NoSpaceException;
import model.ParkingLot;
import model.Space;
import model.Vehicle;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

// Code based on JsonSerializationDemo
// Represents a reader that reads parkingLot from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads parkingLot from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ParkingLot read() throws IOException, NoSpaceException, ParseException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseParkingLot(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses parkingLot from JSON object and returns it
    private ParkingLot parseParkingLot(JSONObject jsonObject) throws NoSpaceException, ParseException {
        ParkingLot pl = new ParkingLot();
        addSpaces(pl, jsonObject);
        addVehicles(pl, jsonObject);
        pl.setBalance(jsonObject.getInt("balance"));
        return pl;
    }

    // MODIFIES: pl
    // EFFECTS: parses spaces from JSON object and adds them to parkingLot
    private void addSpaces(ParkingLot pl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("spaces");
        for (Object json : jsonArray) {
            JSONObject nextSpace = (JSONObject) json;
            addSpace(pl, nextSpace);
        }
    }

    // MODIFIES: pl
    // EFFECTS: parses space from JSON object and adds it to parkingLot
    private void addSpace(ParkingLot pl, JSONObject jsonObject) {
        Boolean isVacancy = jsonObject.getBoolean("isVacancy");
        int num = jsonObject.getInt("num");
        Space space = new Space(num);
        space.setIsVacancy(isVacancy);
        pl.addSpace(space);
    }

    // MODIFIES: pl
    // EFFECTS: parses vehicles from JSON object and adds them to parkingLot
    private void addVehicles(ParkingLot pl, JSONObject jsonObject) throws NoSpaceException, ParseException {
        JSONArray jsonArray = jsonObject.getJSONArray("vehicles");
        for (Object json : jsonArray) {
            JSONObject nextVehicle = (JSONObject) json;
            addVehicle(pl, nextVehicle);
        }
    }

    // MODIFIES: pl
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addVehicle(ParkingLot pl, JSONObject jsonObject) throws NoSpaceException, ParseException {
        String licensePlateNum = jsonObject.getString("licensePlateNum");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        Date startTime = sdf.parse(jsonObject.getString("startTime"));
        int pn = jsonObject.getInt("parkingNum");
        Vehicle vehicle = new Vehicle(licensePlateNum);
        pl.addVehicle(vehicle, pn);
        vehicle.setStartTime(startTime);
    }
}

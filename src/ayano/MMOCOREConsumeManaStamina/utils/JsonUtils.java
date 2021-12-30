package ayano.MMOCOREConsumeManaStamina.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonUtils {
  private final Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
  
  public JsonElement readFromFile(File file) {
    try {
      return (JsonElement)this.gson.fromJson(new FileReader(file), JsonElement.class);
    } catch (IOException e) {
      e.printStackTrace();
      return (JsonElement)new JsonObject();
    } 
  }
  
  public void writeToFile(File file, JsonElement json) {
    try {
      FileWriter writer = new FileWriter(file);
      writer.write(this.gson.toJson(json));
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
}
package ayano.MMOCOREConsumeManaStamina.utils.chests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import ayano.MMOCOREConsumeManaStamina.MMOCOREConsumeManaStamina;
import ayano.MMOCOREConsumeManaStamina.utils.SimpleBlockLocation;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Location;

public class ChestCollection {
  private final Set<SimpleBlockLocation> chests = new HashSet<>();
  
  private final File file;
  
  public ChestCollection(File file) {
    this.file = file;
  }
  
  public void add(Location loc) {
    this.chests.add(new SimpleBlockLocation(loc));
  }
  
  public boolean has(Location loc) {
    return this.chests.contains(new SimpleBlockLocation(loc));
  }
  
  public void load() {
    this.chests.clear();
    JsonElement json = MMOCOREConsumeManaStamina.getPlugin().getJson().readFromFile(this.file);
    JsonArray array = json.isJsonArray() ? json.getAsJsonArray() : new JsonArray();
    for (JsonElement element : array) {
      if (element.isJsonObject())
        this.chests.add(new SimpleBlockLocation(element.getAsJsonObject())); 
    } 
  }
  
  public void save() {
    JsonArray array = new JsonArray();
    for (SimpleBlockLocation loc : this.chests)
      array.add(loc.toJson()); 
    MMOCOREConsumeManaStamina.getPlugin().getJson().writeToFile(this.file, (JsonElement)array);
  }
  
  public void reload() {
    save();
    load();
  }
}

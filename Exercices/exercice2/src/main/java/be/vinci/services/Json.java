package be.vinci.services;

import be.vinci.utils.Config;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Json<T> {
    private static final String DB_FILE_PATH = Config.getProperty("DatabaseFilePath");
    private static final Path pathToDb = Paths.get(DB_FILE_PATH);
    private final static ObjectMapper jsonMapper = new ObjectMapper();
    private Class<T> type;

    /**
     * Constructor of the Json class to specify the class of the items to be serialized
     * @param type the class of the items to be serialized
     */
    public Json(Class<T> type){
        this.type = type;
    }

    /**
     * Serialize a list of items in the DB file
     * @param items list of items to be serialized
     */
    public void serialize(List<T> items, String collectionName) {
        System.out.println(collectionName);
        System.out.println(pathToDb);
        try {
            // if no DB file, write a new collection to a new db file
            if (!Files.exists(pathToDb)) {
                // Create an object and add a JSON array as POJO, e.g. { items:[...]}
                ObjectNode newCollection = jsonMapper.createObjectNode().putPOJO(collectionName, items);
                jsonMapper.writeValue(pathToDb.toFile(), newCollection); // write the JSON Object in the DB file
                return;
            }
            // get all collections : can be read as generic JsonNode, if it can be Object or Array;
            JsonNode allCollections = jsonMapper.readTree(pathToDb.toFile()); // e.g. { users:[...], items:[...]}
            // remove current collection, e.g. remove the array of items
            if (allCollections.has(collectionName)) {
                ((ObjectNode) allCollections).remove(collectionName); //e.g. it leaves { users:[...]}
            }
            // Prepare a JSON array from the list of POJOs for the collection to be updated, e.g. [{"film1",...}, ...]
            ArrayNode updatedCollection = jsonMapper.valueToTree(items);
            // Add the JSON array in allCollections, e.g. : { users:[...], items:[...]}
            ((ObjectNode) allCollections).putArray(collectionName).addAll(updatedCollection);
            // write to the db file allCollections
            jsonMapper.writeValue(pathToDb.toFile(), allCollections);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse the DB file and return a list of films
     * @return list of items
     */
    public List<T> parse(String collectionName) {
        try {
            // get allCollections
            JsonNode node = jsonMapper.readTree(pathToDb.toFile());
            // accessing value of the specified field of an object node,
            // e.g. the JSON array within "items" field of { users:[...], items:[...]}
            JsonNode collection = node.get(collectionName);
            if (collection == null) // Send an empty list if there is not the requested collection
                return new ArrayList<T>();
            // convert the JsonNode to a List of POJOs & return it
            return jsonMapper.readerForListOf(type).readValue(collection);
        } catch (FileNotFoundException e) {
            return new ArrayList<T>(); // send an empty list if there is no db file
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<T>();
        }
    }

}

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Scanner;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonReader {

	public void start() throws IOException {

		JsonObject theWholeFile = readFile();
		if (theWholeFile != null) {
			System.out.println(theWholeFile.toString());
		} else {
			System.out.println("Could not read the file!");
		}

		// System.out.println(theWholeFile.toString());

		/*
		 * Scanner sc = new Scanner(System.in); String filename = sc.nextLine();
		 * 
		 * String content = fileToString(filename); // JsonElement je = new
		 * JsonParser().parse(content); // JsonObject jo = je.getAsJsonObject();
		 * 
		 * JsonObject fileAsJson = (new
		 * JsonParser().parse(content)).getAsJsonObject();
		 * 
		 * sc.close(); // return fileAsJson; return;
		 */
	}

	public JsonObject readFile() throws IOException {
				
		Scanner sc = new Scanner(System.in);
		String filename = sc.nextLine();
		
		//String content = new String(Files.readAllBytes(Paths.get(filename, null)));
		
		
		
		
		JsonParser parser = new JsonParser();
		 
        try {
 
            Object obj = parser.parse(new FileReader(filename));
 
            JsonObject jsonObject = (JsonObject) obj;
 
           // String content = jsonObject.toString();
            sc.close();
            return jsonObject;
            
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
		
		
		
		
		// String content = Files.readAllBytes(Paths.get(j, null)).toString();
		
		// String content = fileToString(filename);
		// JsonElement je = new JsonParser().parse(content);
		// JsonObject jo = je.getAsJsonObject();
		
		//JsonObject fileAsJson = (new JsonParser().parse(content)).getAsJsonObject();
        sc.close();
		return null;
		
	}

	private static Object getLeft(JsonObject j) {

		JsonObject left = j.get("left").getAsJsonObject();

		return left;
	}

	private static Object getRight(JsonObject j) {

		JsonObject right = j.get("right").getAsJsonObject();

		return right;
	}

	private static Object getWhat(JsonObject j) {

		JsonObject what = j.get("what").getAsJsonObject();

		return what;
	}

	private static Object getOffset(JsonObject j) {

		JsonObject offset = j.get("offset").getAsJsonObject();

		return offset;
	}

	private static Object getValueObject(JsonObject j) {

		JsonObject offset = j.get("value").getAsJsonObject();

		return offset;
	}

	private static String getValue(JsonObject j) {

		String value = j.get("value").getAsString();

		return value;
	}

	private static String getOperator(JsonObject j) {

		String operator = j.get("operator").getAsString();

		return operator;
	}

	private static String getName(JsonObject j) {

		String name = j.get("name").getAsString();

		return name;
	}

	private static String getKind(JsonObject j) {

		String kind = j.get("kind").getAsString();

		return kind;
	}

	public static void main(String[] args) throws IOException {
		JsonReader myProgram = new JsonReader();

		myProgram.start();

	}

}

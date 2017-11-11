import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Scanner;





import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// TEST COMMAND FOR GIT PUSH

public class JsonReader {

	public void start() throws IOException {

		JsonObject theWholeFile = readFile();
		if (theWholeFile != null) {
		
			//System.out.println(theWholeFile.toString());
			//System.out.print(getChildren(theWholeFile));			
						
			JsonArray children = getChildren(theWholeFile);
			JsonObject child0 =  (JsonObject) children.get(0);
			//System.out.print(getKind(child1));
			//System.out.println(getRight(child0).toString());
			System.out.println(getName(getWhat(getRight(child0))).toString());
			
		
			
			
		} else {
			System.out.println("Could not read the file!");
		}

		
	}

	public JsonObject readFile() throws IOException {
				
		Scanner sc = new Scanner(System.in);
		String filename = sc.nextLine();
		
		//String content = new String(Files.readAllBytes(Paths.get(filename)));
		
		String content = "";
		
		try
	    {
			content = new String(Files.readAllBytes(Paths.get("C:/Users/test/workspace/ReadJson/src/"+filename)));
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	
		
		
		
		
		
		JsonObject jo;
        JsonElement je = new JsonParser().parse(content);
        jo = je.getAsJsonObject();
		
		
        sc.close();
		return jo;
		
	}
	private  JsonArray getChildren(JsonObject j) {

		JsonArray children = j.get("children").getAsJsonArray();

		return children;
	}

	private  Object getLeft(JsonObject j) {

		JsonObject left = j.get("left").getAsJsonObject();

		return left;
	}

	private  JsonObject getRight(JsonObject j) {

		JsonObject right = j.get("right").getAsJsonObject();

		return right;
	}

	private  JsonObject getWhat(JsonObject j) {

		JsonObject what = j.get("what").getAsJsonObject();

		return what;
	}

	private  Object getOffset(JsonObject j) {

		JsonObject offset = j.get("offset").getAsJsonObject();

		return offset;
	}

	private  Object getValueObject(JsonObject j) {

		JsonObject offset = j.get("value").getAsJsonObject();

		return offset;
	}

	private  String getValue(JsonObject j) {

		String value = j.get("value").getAsString();

		return value;
	}

	private  String getOperator(JsonObject j) {

		String operator = j.get("operator").getAsString();

		return operator;
	}

	private  String getName(JsonObject j) {

		String name = j.get("name").getAsString();

		return name;
	}

	private  String getKind(JsonObject j) {

		String kind = j.get("kind").getAsString();

		return kind;
	}

	public static void main(String[] args) throws IOException {
		JsonReader myProgram = new JsonReader();

		myProgram.start();

	}

}

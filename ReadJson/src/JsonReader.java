import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonReader {

	public static void startProgramm(String filepath) throws IOException {

		JsonObject theWholeFile = readFile(filepath);
		if (theWholeFile == null) {
			System.out.println("Could not read the file! File might not exist. Check and try again.");
			return;
		}

		// System.out.println("The whole File:\n" + theWholeFile.toString());
		// System.out.print("The whole File CHILDREN:\n" + getChildren(theWholeFile));

		JsonArray children = getChildren(theWholeFile);
		JsonObject child0 = (JsonObject) children.get(0);
		// System.out.print("Child0:\n" + getKind(child0));
		// System.out.println("Child.getRight:\n" + getRight(child0).toString());
		System.out.println("Child.getName:\n" + getName(getWhat(getRight(child0))).toString());

	}

	public static JsonObject readFile(String filepath) throws IOException {
		String content = "";

		try {
			content = new String(Files.readAllBytes(Paths.get(filepath)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		JsonObject jsonObject;
		JsonElement jsonElement = new JsonParser().parse(content);
		jsonObject = jsonElement.getAsJsonObject();
		return jsonObject;

	}

	private static JsonArray getChildren(JsonObject j) {
		JsonArray children = j.get("children").getAsJsonArray();
		return children;
	}

	private static Object getLeft(JsonObject j) {
		JsonObject left = j.get("left").getAsJsonObject();
		return left;
	}

	private static JsonObject getRight(JsonObject j) {
		JsonObject right = j.get("right").getAsJsonObject();
		return right;
	}

	private static JsonObject getWhat(JsonObject j) {
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
		System.out.println("Current Working Directory = " + System.getProperty("user.dir"));

		String filename;
		if (args.length <= 0) {
			System.out.println("No filename passed as parameter. Default filename 'slice1.json' got used.");
			filename = "slice1.json";
		} else {
			filename = args[0];
		}
		// HERE input validation and sanitation of the filename !!!

		String filepath = System.getProperty("user.dir").toString() + "/ReadJson/src/" + filename;
		startProgramm(filepath);
	}

}

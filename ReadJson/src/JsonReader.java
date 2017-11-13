import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonReader {

	// GLOBAL STATIC VARIABLE where the whole file gets stored as a JSON object, so
	// it can be accessed from everywhere
	private static JsonObject fileAsJsonObject;

	public static void startProgramm(String filepath) throws IOException {

		JsonObject theWholeFile = readFile(filepath);

		if (theWholeFile == null) {
			System.out.println("Could not read the file! File might not exist. Check and try again.");
			// returns if the read file could not be found
			return;
		}
		setFileAsJsonObject(theWholeFile);

		// System.out.println(theWholeFile.toString());
		// System.out.print(getChildren(theWholeFile));

		// JsonArray children = getChildren(theWholeFile);
		// JsonObject child0 = (JsonObject) children.get(0);
		// System.out.println(getRight(child0).toString());
		// System.out.println(getName(getWhat(getRight(child0))).toString());
		// System.out.println(findExpression(theWholeFile));
		// System.out.println(findEnteryPoint(theWholeFile));

		JsonArray children = getChildren(theWholeFile);
		JsonObject child0 = (JsonObject) children.get(0);
		// System.out.print("Child0:\n" + getKind(child0));
		// System.out.println("Child.getRight:\n" + getRight(child0).toString());
		String childName = getName(getWhat(getRight(child0)));
		System.out.println("childName: " + childName + "\n");

		System.out.println(
				determineSanitization(theWholeFile) + " and the entery point is " + findEnteryPoint(theWholeFile));

	}

	public static String findExpression(JsonObject file) throws IOException {

		JsonArray children = getChildren(file);
		String expression = "";
		int size = children.size();
		int currentChild = 0;
		do {
			JsonObject child = (JsonObject) children.get(currentChild);
			if (getKind(child).equals("assign")) {

				if (getKind(getRight(child)).equals("call")) {
					expression = getName(getWhat(getRight(child)));

				}
			} else if (getKind(child).equals("call")) {
				expression = getName(getWhat(child));
			} else if (getKind(child).equals("echo")) {
				expression = getKind(child);
			}

			currentChild++;

		} while (currentChild < size);

		return expression;

	}

	public static String findEnteryPoint(JsonObject file) throws IOException {

		JsonArray children = getChildren(file);
		String entryPoint = "";
		int size = children.size();
		int currentChild = 0;
		do {
			JsonObject child = (JsonObject) children.get(currentChild);
			if (getKind(child).equals("assign")) {

				if (getKind(getRight(child)).equals("offsetlookup")) {
					entryPoint = getName(getWhat(getRight(child)));

				}
			} else if (getKind(child).equals("echo")) {
				JsonArray arguments = getArguments(child);
				int currentArgument = 0;
				do {
					JsonObject argument = (JsonObject) arguments.get(currentArgument);
					if (getKind(argument).equals("offsetlookup")) {
						entryPoint = getName(getWhat(argument));
					}

				} while (currentArgument < arguments.size());
			}

			currentChild++;

		} while (currentChild < size);

		return entryPoint;

	}

	public static String determineSanitization(JsonObject file) throws IOException {
		String exeption = findExpression(file);
		String sanitization = "";
		String injectionType = "";

		// SQL
		if (exeption.equals("mysql_query") || exeption.equals("mysql_unbuffered_query")
				|| exeption.equals("mysql_db_query")) {
			sanitization = "mysql_escape_string or mysql_real_escape_string";
			injectionType = "SQL Injection";
		} else if (exeption.equals("mysqli_query") || exeption.equals("mysqli_real_query")
				|| exeption.equals("mysqli_master_query") || exeption.equals("mysqli_multi_query")) {
			sanitization = "mysqli_escape_string or mysqli_real_escape_string";
			injectionType = "SQL Injection";
		} else if (exeption.equals("mysqli_stmt_execute") || exeption.equals("mysqli_execute")) {
			sanitization = "mysqli_stmt_bind_param";
			injectionType = "SQL Injection";
		} else if (exeption.equals("mysqli::query") || exeption.equals("mysqli::multi_query")
				|| exeption.equals("mysqli::real_query")) {
			sanitization = "mysqli::escape_string or mysqli::real_escape_string";
			injectionType = "SQL Injection";
		}
		// XSS
		else if (exeption.equals("echo") || exeption.equals("print") || exeption.equals("printf")
				|| exeption.equals("die") || exeption.equals("die")) {
			sanitization = "htmlentities or htmlspecialchars or strip_tags or urlencode";
			injectionType = "Cross site scripting";
		}

		return "Injection type is " + injectionType + " Functions for sanitizations are " + sanitization;
	}

	public static JsonObject readFile(String filepath) throws IOException {

		String content = "";

		try {
			content = new String(Files.readAllBytes(Paths.get(filepath)));
		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("FAIL");
			return null;
		}

		JsonElement jsonElement = new JsonParser().parse(content);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		return jsonObject;
	}

	private static JsonObject getFileAsJsonObject() {
		return fileAsJsonObject;
	}

	private static void setFileAsJsonObject(JsonObject fileAsJsonObject) {
		JsonReader.fileAsJsonObject = fileAsJsonObject;
	}

	private static JsonArray getChildren(JsonObject j) {

		JsonArray children = j.get("children").getAsJsonArray();

		return children;
	}

	private static JsonArray getArguments(JsonObject j) {

		JsonArray arguments = j.get("arguments").getAsJsonArray();

		return arguments;
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

	/*
	 * Filename has to start with a letter, either lowercase or uppercase. Filename
	 * has to end with '.json' and can contain any combination of lowercase letters,
	 * uppercase letters, numbers and '.', '-' and '_'.
	 */
	private static boolean inputValidation(String filename) {
		final String REGEX_FOR_FILENAMES = "^([a-zA-Z])+(\\w|\\.|\\-)*(.json)$";
		return filename.matches(REGEX_FOR_FILENAMES);
	}

	public static void main(String[] args) throws IOException {
		System.out.println("Current Working Directory = " + System.getProperty("user.dir"));
		String filename;
		// every additional argument except the filename passed on to the java call gets
		// ignored
		if (args.length <= 0) {
			System.out.println("No filename passed as parameter. Default filename 'slice1.json' got used.");
			// default value. is used when there is no additional parameter with the java
			// call
			filename = "slice1.json";
		} else if (args.length == 1) {
			filename = args[0];
		} else {
			filename = args[0];
			System.out.println("Just the filename gets processed. Every additional argument gets ignored.");
		}

		// HERE input validation and sanitation of the filename !!!
		// MAYBE we need more, maybe not. this is just a quick sanitization
		if (inputValidation(filename)) {
			String filepath = System.getProperty("user.dir").toString() + "/ReadJson/src/" + filename;
			startProgramm(filepath);
		} else {
			System.out.println("The entered filename is not valid.");
			System.out.println(
					"It has to start with a letter a-z or A-Z and can only contain capital and lowercase letters, as well as numbers, '.', '_' and '-'.");
		}
		
	}

}

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonReader {
	
	// ATTENTION: exclusively reading from this attribute, no writing or changing
	private static JsonObject fileAsJsonObject;

	private static void startProgramm(String filepath) throws IOException {

		JsonObject theWholeFile = readFile(filepath);
		fileAsJsonObject = theWholeFile;
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
		String childName = getName(getWhat(getRight(child0)));
		System.out.println("childName: " + childName + "\n");
		String vulnerabilityPattern = checkVulnerabilities(childName);
		System.out.println(vulnerabilityPattern);
	}

	/**
	 * Checks the Vulnerabilities one after another
	 * @param childName
	 * @return
	 */
	private static String checkVulnerabilities(String childName) {
		StringBuilder result = new StringBuilder();
		if(isSQLInjection(childName)) {
			result.append("SQL Injection\n" + childName + "\n");
			result.append(sanitizedFunctions());
			result.append("\n\n");
		}
		
		if(isFileInclusion(childName)) {
			result.append("Local or Remote File Inclusion / Path Traversal\n" + childName);
			result.append("\n\n");
		}
		
		if(isCrossSiteScripting(childName)) {
			result.append("Cross Site Scripting\n" + childName);
			result.append("\n\n");
		}
		
		if(isPHPCodeInjection(childName)) {
			result.append("PHP Code Injection\n" + childName);
			result.append("\n\n");
		}
		
		return result.toString();
	}
	
	/*
	 * The structure looks like the following:
	 * First Line the sanitized Functions, which are NOT in the Json object.
	 * Second Line the unsanitized Functions, which ARE in the Json object.
	 */
	private static String sanitizedFunctions() {
		String line1 = "";
		String line2 = "";
		
		// ALL the expressions should be added
		// IF I UNDERSTOOD OUR PROJECT INSTRUCTIONS RIGHT
		if(checkForExpression("mysql_query")) {
			line1 += "mysql_real_escape_string, ";
			line2 += "mysql_query, ";
		}
		
		// HERE ALL THE OTHER EXPRESSIONS
		
		// DONT CHANGE THE RETURN STATEMENT
		// it removes the last space and the comma from line1 and line2
		return line1.substring(0, line1.length() - 2) + "\n" + line2.substring(0, line2.length() - 2);
	}
	
	// THIS FUNCTION HAS TO WALK THROUGH RECURSIVELY THE JSON OBJECT AND LOOK FOR THE GIVEN EXPRESSION
	private static boolean checkForExpression(String expression) {
		// actual recursive part and looking part is still missing
		return true;
	}
	
	private static boolean isSQLInjection(String childName) {
		if(childName.equals("$_GET")) {
			return true;
		} else if(childName.equals("$_GET")) {
			return true;
		} else if(childName.equals("$_POST")) {
			return true;
		} else if(childName.equals("$_COOKIE")) {
			return true;
		} else if(childName.equals("$_REQUEST")) {
			return true;
		} else if(childName.equals("HTTP_GET_VARS")) {
			return true;
		} else if(childName.equals("HTTP_POST_VARS")) {
			return true;
		} else if(childName.equals("HTTP_COOKIE_VARS")) {
			return true;
		} else if(childName.equals("HTTP_REQUEST_VARS")) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean isFileInclusion(String childName) {
		if(childName.equals("$_GET")) {
			return true;
		} else if(childName.equals("$_GET")) {
			return true;
		} else if(childName.equals("$_POST")) {
			return true;
		} else if(childName.equals("$_COOKIE")) {
			return true;
		} else if(childName.equals("$_REQUEST")) {
			return true;
		} else if(childName.equals("HTTP_GET_VARS")) {
			return true;
		} else if(childName.equals("HTTP_POST_VARS")) {
			return true;
		} else if(childName.equals("HTTP_COOKIE_VARS")) {
			return true;
		} else if(childName.equals("HTTP_REQUEST_VARS")) {
			return true;
		} else if(childName.equals("$_FILES")) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean isCrossSiteScripting(String childName) {
		if(childName.equals("$_GET")) {
			return true;
		} else if(childName.equals("$_GET")) {
			return true;
		} else if(childName.equals("$_POST")) {
			return true;
		} else if(childName.equals("$_COOKIE")) {
			return true;
		} else if(childName.equals("$_REQUEST")) {
			return true;
		} else if(childName.equals("HTTP_GET_VARS")) {
			return true;
		} else if(childName.equals("HTTP_POST_VARS")) {
			return true;
		} else if(childName.equals("HTTP_COOKIE_VARS")) {
			return true;
		} else if(childName.equals("HTTP_REQUEST_VARS")) {
			return true;
		} else if(childName.equals("$_FILES")) {
			return true;
		} else if(childName.equals("$_SERVERS")) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean isPHPCodeInjection(String childName) {
		if(childName.equals("$_GET")) {
			return true;
		} else if(childName.equals("$_GET")) {
			return true;
		} else if(childName.equals("$_POST")) {
			return true;
		} else if(childName.equals("$_COOKIE")) {
			return true;
		} else if(childName.equals("$_REQUEST")) {
			return true;
		} else if(childName.equals("HTTP_GET_VARS")) {
			return true;
		} else if(childName.equals("HTTP_POST_VARS")) {
			return true;
		} else if(childName.equals("HTTP_COOKIE_VARS")) {
			return true;
		} else if(childName.equals("HTTP_REQUEST_VARS")) {
			return true;
		} else if(childName.equals("$_FILES")) {
			return true;
		} else if(childName.equals("$_SERVERS")) {
			return true;
		} else {
			return false;
		}
	}
	
	private static JsonObject readFile(String filepath) throws IOException {
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
		if(name.charAt(0) == '_') {
			name  = "$" + name;
		}
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

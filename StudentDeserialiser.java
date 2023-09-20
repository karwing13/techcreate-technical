public class StudentDeserialiser {
  public Student parse(String lineFeed) {
    Student record = new Student();
    if (lineFeed != null) {
      int length = lineFeed.length();
      record.firstName = extractSubstring(lineFeed, 0, 20, length);
      record.lastName = extractSubstring(lineFeed, 20, 40, length);
      record.level = extractSubstring(lineFeed, 40, 50, length);
      record.studentClass = extractSubstring(lineFeed, 50, 60, length);
    } else {
      // Handle cases where the input is null.
      // Set default values or leave them as null as needed.
      record.firstName = "";
      record.lastName = "";
      record.level = "";
      record.studentClass = "";
    }
    return record;
  }

  private String extractSubstring(String input, int startIndex, int endIndex, int length) {
    if (startIndex >= length) {
      return "";
    }
    int actualEndIndex = Math.min(endIndex, length);
    return input.substring(startIndex, actualEndIndex).trim();
  }
}
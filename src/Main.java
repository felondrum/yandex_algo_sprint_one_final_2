import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** @github <a href="https://github.com/felondrum/yandex_algo_sprint_one_final_2">...</a> */
public class Main {
  public static void main(String[] args) {
    makeTest(); //comment row to skip tests
    readFromConsole();
  }
  public static int exTask(Integer maxFingersPerPlayer, Integer playersCount, List<List<String>> fieldRows) {
    List<Integer> field = readRowOfFieldAndCountValues(fieldRows);
    return getPossibleScore(maxFingersPerPlayer, playersCount, field);
  }

  public static List<String> readFieldRow(String rowAsString) {
    return Arrays.asList(rowAsString.split(""));
  }

  public static List<Integer> readRowOfFieldAndCountValues(List<List<String>> fieldRows) {
    int valAsNumber;
    List<Integer> resultList = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0);
    for (List<String> fieldRow : fieldRows) {
      for (String s : fieldRow) {
        try {
          valAsNumber = Integer.parseInt(s);
          Integer modifiedValue = resultList.get(valAsNumber - 1) + 1;
          int searchIndex = valAsNumber - 1;
          resultList.set(searchIndex, modifiedValue);
        } catch (NumberFormatException e) {
//          System.out.println("Value " + fieldRow.get(i) + " in row is not a number");
//          if not a number just skipping
        } catch (ArrayIndexOutOfBoundsException e) {
          System.out.println("Array size or index problem, probably");
        } catch (Exception e) {
          System.out.println("Undocumented problem. Text: " + e.getLocalizedMessage());
        }
      }
    }

    return resultList;
  }

  /** O(N) success (YContext ID: 83970077)
   @param maxFingersPerPlayer - available fingers for one player, 1 < n < 5
   @param playersCount - players count
   @param countedFieldsValues - game field by row
   @return possible game score by all players*/
  //
  public static Integer getPossibleScore(Integer maxFingersPerPlayer, Integer playersCount, List<Integer> countedFieldsValues) {
    int maxPossibleResult = 0;
    for(Integer val:countedFieldsValues) {
      if (val > 0 && val <= maxFingersPerPlayer * playersCount) maxPossibleResult++;
    }
    return maxPossibleResult;
  }

  public static void readFromConsole() {
    List<List<String>> field = new ArrayList<>();
    try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      int maxFingersPerPlayer = Integer.parseInt(reader.readLine());
      for (int i = 0; i<4; i++) {
        field.add(readFieldRow(reader.readLine()));
      }
      writeNumber(exTask(maxFingersPerPlayer, 2, field));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T extends Number> void writeNumber(T number) throws IOException {
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    writer.write(String.valueOf(number));
    writer.flush();
  }

  public static List<List<String>> fieldStringArrayUtil(String rowOne, String rowTwo, String rowThree, String rowFour) {
    return Arrays.asList(readFieldRow(rowOne), readFieldRow(rowTwo), readFieldRow(rowThree), readFieldRow(rowFour));
  }

  public static void makeTest() {
    getTestResult(3 , 2,
            fieldStringArrayUtil("1231", "2..2", "2..2", "2..2"),
            2, 1);
    getTestResult(4 , 2,
            fieldStringArrayUtil("1111", "9999", "1111", "9911"),
            1, 2);
    getTestResult(4 , 2,
            fieldStringArrayUtil("1111", "1111", "1111", "1111"),
            0, 3);
    getTestResult(3 , 2,
            fieldStringArrayUtil("....", "....", "....", "...."),
            0, 4);
    getTestResult(1 , 2,
            fieldStringArrayUtil("8332", "2211", "23..", "...."),
            2, 5);
  }

  public static void getTestResult(Integer maxFingersPerPlayer,
                                   Integer playersCount,
                                   List<List<String>> fieldRows,
                                   Integer expectedValue, Integer testCount) {
    int resultValue = exTask(maxFingersPerPlayer, playersCount, fieldRows);
    if (resultValue != expectedValue) {
      System.out.println("Error in test: " + testCount
              + ", has: " + resultValue + " expected: " + expectedValue);
      return;
    }
    System.out.println("Test #" + testCount + " correct!"
            + ", has: " + resultValue + " expected: " + expectedValue);
  }

}
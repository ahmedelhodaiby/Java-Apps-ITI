import tech.tablesaw.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TitanicDataAnalysisTablesaw {

    public static void main(String[] args) {

        TitanicDataAnalysisTablesaw tda = new TitanicDataAnalysisTablesaw();
        String filePath = "src/main/resources/titanic.csv";
        Table titanicData = null;

        //loading data into a table
        titanicData = tda.loadFromCsvTablesaw(filePath);

        //printing the table structure
        String tableStructure = tda.getTableStructure (titanicData);
        System.out.println (tableStructure);

        //printing table summary
        System.out.println(tda.getTableSummary(titanicData));

        //printing the number of missing values in all and a selected column
        System.out.println(titanicData.missingValueCounts());
        System.out.println(titanicData.select("age").missingValueCounts());

        //drop rows using Selections
        //dropping rows where age is empty
        Table dataNoEmptyAge = titanicData.dropWhere(titanicData.numberColumn(4).isMissing());
        System.out.println("\nsummary after dropping\n" +tda.getTableSummary(dataNoEmptyAge));

        //sorting new data with no null ages
        System.out.println(dataNoEmptyAge.sortDescendingOn("age"));

        //exchanging the sex column with gender column of numeric data
        Table mappedData = tda.mapTextColumnToNumber (titanicData);

        Table firstFiveRows = mappedData.first (5);
        System.out.println ("Titanic with numeric gender column\n"+firstFiveRows);

    }

    public Table loadFromCsvTablesaw(String path) {
        /**
         * Load data from CSV file into a table using tablesaw
         */

        Table table = null;
        try {
            table = Table.read().csv(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    public String getTableStructure(Table table){
        /**
         * Get the structure of the table i.e. columns of the table
         */

        Table struct = table.structure();
        return struct.toString();
    }

    public String getTableSummary(Table table){
        /**
         * calculate min, max, mean, ...
         */

        Table summary = table.summary();
        return summary.toString();
    }

    // mapping text data to numeric values on the sex field female=0,male=1 and adding a column named gender
    public Table mapTextColumnToNumber(Table data) {
        NumberColumn mappedGenderColumn = null;
        StringColumn gender = (StringColumn) data.column ("Sex");
        List<Number> mappedGenderValues = new ArrayList<Number>();
        for (String v : gender) {
            if ((v != null) && (v.equals ("male"))) {
                mappedGenderValues.add (1);
            } else {
                mappedGenderValues.add (0);
            }
        }
        mappedGenderColumn = DoubleColumn.create ("gender", mappedGenderValues);
        data.addColumns (mappedGenderColumn);
//        data.write ().csv ("test.csv");
        data.removeColumns ("Sex");
        return data;
    }

}

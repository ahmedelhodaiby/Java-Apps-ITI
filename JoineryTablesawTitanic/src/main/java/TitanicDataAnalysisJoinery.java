import joinery.DataFrame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TitanicDataAnalysisJoinery {

    public static void main(String[] args) {

        TitanicDataAnalysisJoinery tda = new TitanicDataAnalysisJoinery();
        //load data from CSV
//        DataFrame<Object> df = tda.readFromCsvJoinery("src/main/resources/titanic.csv");

//        System.out.println(df.toString());

        DataFrame<Object> df1 = null;
        try {
            //only loading specific columns into the dataframe
            df1 = DataFrame.readCsv("src/main/resources/titanic.csv")
                    .retain("pclass", "survived", "sex", "age", "sibsp", "embarked");

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(df1);

        //printing the count of survivors of each gender
        System.out.println(df1.retain("sex","survived")
                .groupBy("sex")
                .count()
                );

    }

    public DataFrame readFromCsvJoinery(String path) {
        DataFrame<Object> df = null;
        try {
            /**
             * load data from CSV
             */
            df = DataFrame.readCsv(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return df;
    }
}

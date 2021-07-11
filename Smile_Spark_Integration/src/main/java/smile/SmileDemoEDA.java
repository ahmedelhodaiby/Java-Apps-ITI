package smile;

import org.apache.commons.csv.CSVFormat;
import smile.classification.RandomForest;
import smile.data.DataFrame;
import smile.data.formula.Formula;
import smile.data.measure.NominalScale;
import smile.data.vector.IntVector;
import smile.io.Read;
import smile.plot.swing.Histogram;
import smile.validation.metric.Accuracy;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class SmileDemoEDA {
    String trainPath = "src/main/resources/data/titanic_train.csv";
    String testPath = "src/main/resources/data/titanic_test.csv";

    public static void main(String[] args) throws IOException {

        SmileDemoEDA sd = new SmileDemoEDA ();
        PassengerProvider pProvider = new PassengerProvider ();

        DataFrame trainData = pProvider.readCSV (sd.trainPath);
        System.out.println ("Training data structure\n"+trainData.structure ());
        System.out.println ("Training data summary\n"+trainData.summary ());

        //Map categorical columns to numerical columns
        trainData = trainData.merge (IntVector.of ("Gender",
                encodeCategory (trainData, "Sex")));
        trainData = trainData.merge (IntVector.of ("PClassValues",
                encodeCategory (trainData, "Pclass")));

        trainData = trainData.drop ("Pclass", "Sex");
        trainData = trainData.omitNullRows ();
        System.out.println (trainData.summary ());
        System.out.println ("==============Start of Exploratory Data Analysis==============");
        try {
            eda (trainData);
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace ();
        }

        RandomForest model = RandomForest.fit(Formula.lhs("Survived"), trainData);
        System.out.println("feature importance:");
        System.out.println(Arrays.toString(model.importance()));
        System.out.println(model.metrics ());

        //TODO load test data to validate model

        System.out.println ("============== test Data ==============");
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader ();
        DataFrame testData = null;
        try {
            testData = Read.csv (sd.testPath, format);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        testData = testData.select("Pclass", "Age", "Sex", "Survived");
        testData = testData.merge (IntVector.of ("Gender",
                encodeCategory (testData, "Sex")));
       testData = testData.merge(IntVector.of("PClassValues",
                encodeCategory(testData, "Pclass")));
        testData = testData.drop("Sex");
        testData = testData.drop("Pclass");
        testData = testData.omitNullRows();

        System.out.println (testData.structure ());
        System.out.println (testData.summary ());
        System.out.println ("-------------------------------------------------------------------");
        int[][] results=model.test (testData);
        testData = testData.merge(IntVector.of("predictions", results[0]));
        System.out.println(testData);
//        trainData = trainData.merge("output" , IntVector.of(results));
//        RandomForest model1= model.prune (testData);
//        model1.importance ();
//        model1.metrics ();


    }

/**********************************************************************************************************************/

    public static int[] encodeCategory(DataFrame df, String columnName) {
        /**
         * returns an intArray mapped from a categorical column from the dataframe
         */
        String[] values = df.stringVector (columnName).distinct ().toArray (new String[]{});
        int[] pclassValues = df.stringVector (columnName).factorize (new NominalScale (values)).toIntArray ();
        return pclassValues;
    }

    private static void eda(DataFrame titanic) throws InterruptedException, InvocationTargetException {
        /**
         * replacing null values with zeros in age
         */
        titanic.summary ();
        DataFrame titanicSurvived = DataFrame.of (titanic.stream ().filter (t -> t.get ("Survived").equals (1)));
        DataFrame titanicNotSurvived = DataFrame.of (titanic.stream ().filter (t -> t.get ("Survived").equals (0)));
        titanicNotSurvived.omitNullRows ().summary ();
        titanicSurvived = titanicSurvived.omitNullRows ();
        titanicSurvived.summary ();
        int size = titanicSurvived.size ();
        System.out.println ("number of survived passengers "+size);
        Double averageAge = titanicSurvived.stream ()
                .mapToDouble (t -> t.isNullAt ("Age") ? 0.0 : t.getDouble ("Age"))
                .average ()
                .orElse (0);
        System.out.println ("average Age "+averageAge.intValue ());
// counting ages in a map
        Map map = titanicSurvived.stream ()
                .collect (Collectors.groupingBy (t -> Double.valueOf (t.getDouble ("Age")).intValue (), Collectors.counting ()));

        double[] breaks = ((Collection<Integer>) map.keySet ())
                .stream ()
                .mapToDouble (l -> Double.valueOf (l))
                .toArray ();

        int[] valuesInt = ((Collection<Long>) map.values ())
                .stream ().mapToInt (i -> i.intValue ())
                .toArray ();

        Histogram.of (titanicSurvived.doubleVector ("Age").toDoubleArray (), 15, false)
                .canvas ().setAxisLabels ("Age", "Count")
                .setTitle ("Age frequencies among surviving passengers")
                .window ();
        Histogram.of (titanicSurvived.intVector ("PClassValues").toIntArray (), 4, true)
                .canvas ().setAxisLabels ("Classes", "Count")
                .setTitle ("Pclass values frequencies among surviving passengers")
                .window ();
//        Histogram.of(values, map.size(), false).canvas().window();
        System.out.println ("titanic survived schema "+titanicSurvived.schema ());
        //////////////////////////////////////////////////////////////////////////

    }

    public static void testModel(DataFrame testData,RandomForest model){
        int[][] results=model.test (testData);
        RandomForest model1= model.prune (testData);
        System.out.println("feature importance:");
        System.out.println(Arrays.toString(model1.importance()));
        System.out.println(model1.metrics ());
        int[] pred = model.predict(testData);
        System.out.println("Accuracy= "+ Accuracy.of(testData.column("survived").toIntArray(), pred));
    }

}

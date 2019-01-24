package com.java.zonov.cft.sorting;

import com.java.zonov.cft.sorting.read.ReadFromSource;
import com.java.zonov.cft.sorting.sort.SortingT;
import com.java.zonov.cft.sorting.verify.Verification;
import com.java.zonov.cft.sorting.write.WriteToSource;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.ArrayList;
import java.util.List;

public class Run {
    /**
     * Command line parameter. Data type - Integer
     */
    @Option(name = "-i", usage = "integer data type", forbids = {"-s"})
    private boolean intType;

    /**
     * Command line parameter. Data type - String
     */
    @Option(name = "-s", usage = "string data type")
    private boolean strType;

    /**
     * Command line parameter. Sorting type - ASC
     */
    @Option(name = "-a", usage = "asc sorting")
    private boolean asc;

    /**
     * Command line parameter. Sorting type - DESC
     */
    @Option(name = "-d", usage = "desc sorting", forbids = {"-a"})
    private boolean desc;

    /**
     * Command line arguments. output and input file names
     */
    @Argument(required = true, metaVar = "IO files")
    private List<String> arguments = new ArrayList<String>();


    public static void main(String[] args) {
        new Run().start(args);
    }


    private <T> void start(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);

            if (!(strType ^ intType)) {
                throw new CmdLineException(parser, "Data type option is not set", new Throwable());
            }
            if (arguments.size() < 2) {
                throw new CmdLineException(parser, "Not enough arguments [IO files] is given", new Throwable());
            }
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("Program [-options...] arguments...");

            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();

            // print option sample
            System.err.println("Example: sort.exe -i -d out.txt in1.txt in2.txt");
            System.exit(2);
        }

        String outputFileName = arguments.get(0); // имя файла для сохранение
        List<String> inputFileNames = arguments.subList(1, arguments.size()); // коллекция имен файлов для считывания данных

        List<List<Integer>> intData = new ArrayList();
        List<List<String>> strData = new ArrayList();

        for (String file : inputFileNames) {
            List<String> rawList = ReadFromSource.read(file);

            if (intType) {
                List<Integer> list = Verification.check(rawList, file);

                if (!list.isEmpty()) {
                    intData.add(list);
                }
            } else if (!rawList.isEmpty()) {
                strData.add(rawList);
            }
        }

        if (intType && ! (intData.size() < 2)) {
            WriteToSource.write(outputFileName, SortingT.sort(intData), desc);
        } else if (strType && !(strData.size() < 2)) {
            WriteToSource.write(outputFileName, SortingT.sort(strData), desc);
        } else {
            System.err.println("Not enough file's to sort");
            System.exit(2);
        }


    }
}

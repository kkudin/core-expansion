package com.omvoid.community;

import com.omvoid.community.corexp.CoreExpansionAlgorithmImpl;
import com.omvoid.community.exception.GraphReaderException;
import com.omvoid.community.exception.JsonWriterException;
import com.omvoid.community.models.CmdArguments;
import com.omvoid.community.utils.CommandLineUtil;
import com.omvoid.community.utils.GraphReader;
import com.omvoid.community.utils.JsonResultWriter;
import org.apache.commons.cli.ParseException;

import java.util.concurrent.TimeUnit;

public class Application {

    public static void main(String[] args) {
        CommandLineUtil commandLineUtil = new CommandLineUtil();
        try {
            commandLineUtil = new CommandLineUtil();
            CmdArguments cmdArguments = commandLineUtil.extractArguments(args);

            GraphReader reader = new GraphReader();
            var graph = reader.readGraph(cmdArguments);

            System.out.println("Graph read success");

            System.out.println("Computing communities... This may take several minutes");

            long time = System.nanoTime();

            CoreExpansionAlgorithmImpl coreExpansionAlgorithm = new CoreExpansionAlgorithmImpl(
                    Runtime.getRuntime().availableProcessors(),
                    cmdArguments.getIsWeighted()
            );
            var results = coreExpansionAlgorithm.computeCommunities(graph);

            time = System.nanoTime() - time;

            System.out.printf("Computing communities success. It took %d seconds\n",TimeUnit.SECONDS.convert(time, TimeUnit.NANOSECONDS));

            JsonResultWriter jsonResultWriter = new JsonResultWriter();
            jsonResultWriter.writeResult(results, cmdArguments.getOutputDirectory());

            System.out.printf("Result was written to '%s' directory", cmdArguments.getOutputDirectory());
        } catch (JsonWriterException e) {
            System.err.println("Error while write result to file. Aborting...");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("Error while computing communities. Aborting...");
            e.printStackTrace();
        } catch (ParseException e) {
            commandLineUtil.printHelp();
        } catch (GraphReaderException e) {
            System.err.println("Error while read graph. Aborting...");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Got unexpected exception. Aborting...");
            e.printStackTrace();
        }
    }
}

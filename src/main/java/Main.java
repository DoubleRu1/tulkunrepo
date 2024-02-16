import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.helper.HelpScreenException;
import net.sourceforge.argparse4j.inf.*;
import org.sngroup.Configuration;
import org.sngroup.test.evaluator.Evaluator;
import org.sngroup.test.evaluator.BurstEvaluator;
import org.sngroup.test.evaluator.IncrementalEvaluator;
import org.sngroup.test.runner.SimulationRunner;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getGlobal();
        logger.setLevel(Level.INFO);

        ArgumentParser parser = ArgumentParsers
                .newFor("Tulkun").build()
                .defaultHelp(true)
                .description("分布式数据平面验证");

//        parser.addArgument("module").help("module name");
        Subparsers subparser = parser.addSubparsers().title("subcommands").help("sub-command help").dest("prog").metavar("prog");

        Subparser burst = subparser.addParser("burst").help("Burst update simulation evaluator. All FIBs are read at once and then verified.");
        Subparser incre = subparser.addParser("incre").help("Incremental update simulation Evaluator");
        Subparser list = subparser.addParser("list").help("Print network list");

        burst.addArgument("network").type(String.class).help("Network name. All configurations will be set automatically.");
        burst.addArgument("-t", "--times").type(Integer.class).setDefault(1).help("The times of burst update");
        Evaluator.setParser(burst);

        incre.addArgument("network").type(String.class).help("Network name. All configurations will be set automatically.");
        incre.addArgument("-t", "--times").type(Integer.class).setDefault(-1).help("The times of rule change, -1 means read all changes");
        Evaluator.setParser(incre);

        Namespace ns;
        try {
            ns = parser.parseArgs(args);
        }catch (HelpScreenException e){
            return;
        } catch (ArgumentParserException e) {
            e.printStackTrace();
            return;
        }

        String prog = ns.getString("prog");
        Evaluator evaluator;
        switch (prog) {
//            case "bss": {
//                evaluator = new BurstEvaluator(ns);
//                evaluator.start(new SocketSimulationRunner());
//                return;
//            }
            case "burst": {
                evaluator = new BurstEvaluator(ns);
                evaluator.start(new SimulationRunner());
                return;
            }
            case "incre": {
                evaluator = new IncrementalEvaluator(ns);
                evaluator.start(new SimulationRunner());
                return;
            }
            case "list": {
                System.out.println("Network list:");
                for (String n : Configuration.getNetworkList()) {
                    System.out.println("\t"+n);
                }
            }
        }


    }
}
